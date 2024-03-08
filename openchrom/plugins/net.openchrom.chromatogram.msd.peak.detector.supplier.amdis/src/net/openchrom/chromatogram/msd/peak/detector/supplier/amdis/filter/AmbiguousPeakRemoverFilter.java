/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.comparison.massspectrum.IMassSpectrumComparator;
import org.eclipse.chemclipse.chromatogram.msd.comparison.massspectrum.MassSpectrumComparator;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.filter.IPeakFilter;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.MatchConstraints;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.DataCategory;
import org.eclipse.chemclipse.processing.Processor;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.supplier.ProcessExecutionContext;
import org.eclipse.chemclipse.xxd.filter.peaks.AbstractPeakFilter;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.filter.AmbiguousPeakRemoverFilterSettings.SelectionMethod;

@Component(service = {IPeakFilter.class, Filter.class, Processor.class})
public class AmbiguousPeakRemoverFilter extends AbstractPeakFilter<AmbiguousPeakRemoverFilterSettings> {

	private static final String NAME = "Ambiguous Peak Remover";

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	public String getDescription() {

		return "Filtering ambiguous peaks in a given retention window ";
	}

	@Override
	public Class<AmbiguousPeakRemoverFilterSettings> getConfigClass() {

		return AmbiguousPeakRemoverFilterSettings.class;
	}

	@Override
	public void filterPeaks(IChromatogramSelection<?, ?> chromatogramSelection, AmbiguousPeakRemoverFilterSettings configuration, ProcessExecutionContext context) throws IllegalArgumentException {

		List<IPeak> peaks = new ArrayList<>(getReadOnlyPeaks(chromatogramSelection));
		if(configuration == null) {
			configuration = createNewConfiguration();
		}
		Comparator<IPeak> compareFunction;
		if(configuration.getMethod() == SelectionMethod.AREA) {
			compareFunction = new AreaComparator<>();
			for(IPeak peak : peaks) {
				if(!(peak instanceof IPeakMSD)) {
					throw new IllegalArgumentException("invalid peak type");
				}
			}
		} else {
			compareFunction = new SNRComparator<>();
			for(IPeak peak : peaks) {
				if(!(peak instanceof IChromatogramPeakMSD)) {
					context.addWarnMessage(getName(), "SNR compare method is only avaiable for Chromatogram Peaks, skip processing");
					return;
				}
				if(!(peak instanceof IPeakMSD)) {
					throw new IllegalArgumentException("invalid peak type");
				}
			}
		}
		filterDuplicatePeaks(chromatogramSelection, peaks, configuration, compareFunction, context.getProgressMonitor());
	}

	@Override
	public AmbiguousPeakRemoverFilterSettings createConfiguration(Collection<IPeak> items) throws IllegalArgumentException {

		for(IPeak peak : items) {
			if(peak instanceof IChromatogramPeakMSD peakMSD) {
				return new AmbiguousPeakRemoverFilterSettings((peakMSD).getChromatogram());
			}
		}
		return super.createConfiguration(items);
	}

	private void filterDuplicatePeaks(IChromatogramSelection<?, ?> chromatogramSelection, List<IPeak> peaks, AmbiguousPeakRemoverFilterSettings settings, Comparator<IPeak> compareFunction, IProgressMonitor monitor) {

		SubMonitor subMonitor = SubMonitor.convert(monitor, NAME, peaks.size());
		// We first order all peaks by retention time
		Collections.sort(peaks, new Comparator<IPeak>() {

			@Override
			public int compare(IPeak p1, IPeak p2) {

				return getRTDelta(Adapters.adapt(p1, IPeakMSD.class), Adapters.adapt(p2, IPeakMSD.class));
			}
		});
		IPeak lastPeak = null;
		List<IPeak> candidatePeakSet = new ArrayList<>();
		IMassSpectrumComparator comparator = MassSpectrumComparator.getMassSpectrumComparator(settings.getComparatorID());
		for(IPeak peak : peaks) {
			if(lastPeak != null) {
				double deltaSeconds = getRTDelta(peak, lastPeak) / 1000d;
				double deltaMinutes = deltaSeconds / 60d;
				if(deltaMinutes < settings.getRtMaxdistance()) {
					// add it to the set of candidates
					candidatePeakSet.add(peak);
					lastPeak = peak;
				} else {
					// extract peaks from the candidatesSet...
					// and delete extracted ones...
					deletePeaks(extractPeaks(candidatePeakSet, comparator, settings.getMinmatchfactor()), chromatogramSelection, compareFunction);
					lastPeak = null;
				}
			}
			if(lastPeak == null) {
				lastPeak = peak;
				candidatePeakSet.clear();
				candidatePeakSet.add(peak);
			}
			subMonitor.worked(1);
		}
		if(!candidatePeakSet.isEmpty()) {
			// extract peaks from the candidatesSet...
			// and delete extracted ones...
			deletePeaks(extractPeaks(candidatePeakSet, comparator, settings.getMinmatchfactor()), chromatogramSelection, compareFunction);
		}
	}

	private static List<PeakGroup<IPeak>> extractPeaks(List<IPeak> candidatePeakSet, IMassSpectrumComparator comparator, double minMatchFactor) {

		MatchConstraints matchConstraints = new MatchConstraints();
		//
		int size = candidatePeakSet.size();
		if(size < 2) {
			// nothing to do then...
			return Collections.emptyList();
		}
		// compare each other and form a group
		List<PeakGroup<IPeak>> peakGroups = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			IPeak candidate = candidatePeakSet.get(i);
			for(int j = 0; j < size; j++) {
				if(j == i) {
					// no need to compare with itself
					continue;
				}
				IPeak comparison = candidatePeakSet.get(j);
				IProcessingInfo<IComparisonResult> info = comparator.compare(Adapters.adapt(comparison, IPeakMSD.class).getExtractedMassSpectrum(), Adapters.adapt(candidate, IPeakMSD.class).getExtractedMassSpectrum(), matchConstraints);
				if(info != null) {
					IComparisonResult result = info.getProcessingResult();
					if(result != null) {
						float matchFactor = result.getMatchFactor();
						if(matchFactor / 100.0d > minMatchFactor) {
							PeakGroup<IPeak> group = new PeakGroup<>();
							group.addPeak(candidate, i);
							group.addPeak(comparison, j);
							peakGroups.add(group);
						}
					}
				}
			}
		}
		int groups = peakGroups.size();
		// now join groups that intersect each other
		for(int i = 0; i < groups; i++) {
			PeakGroup<IPeak> current = peakGroups.get(i);
			for(int j = 0; j < groups; j++) {
				if(i == j) {
					continue;
				}
				PeakGroup<IPeak> other = peakGroups.get(j);
				if(current.intersects(other)) {
					current.merge(other);
				}
			}
		}
		for(Iterator<PeakGroup<IPeak>> iterator = peakGroups.iterator(); iterator.hasNext();) {
			PeakGroup<IPeak> peakGroup = iterator.next();
			if(peakGroup.isEmpty()) {
				iterator.remove();
			}
		}
		return peakGroups;
	}

	private void deletePeaks(List<PeakGroup<IPeak>> list, IChromatogramSelection<?, ?> chromatogramSelection, Comparator<IPeak> compareFunction) {

		List<IPeak> peaksToDelete = new ArrayList<>();
		for(PeakGroup<IPeak> peakGroup : list) {
			IPeak maxPeak = peakGroup.getMaxPeak(compareFunction);
			if(maxPeak != null) {
				for(IPeak peak : peakGroup.values()) {
					if(peak == maxPeak) {
						continue;
					}
					peaksToDelete.add(peak);
				}
			}
		}
		deletePeaks(peaksToDelete, chromatogramSelection);
		resetPeakSelection(chromatogramSelection);
	}

	private static int getRTDelta(IPeak p1, IPeak p2) {

		int rt1 = Adapters.adapt(p1, IPeakMSD.class).getExtractedMassSpectrum().getRetentionTime();
		int rt2 = Adapters.adapt(p2, IPeakMSD.class).getExtractedMassSpectrum().getRetentionTime();
		return rt1 - rt2;
	}

	@Override
	public DataCategory[] getDataCategories() {

		return new DataCategory[]{DataCategory.MSD};
	}
}
