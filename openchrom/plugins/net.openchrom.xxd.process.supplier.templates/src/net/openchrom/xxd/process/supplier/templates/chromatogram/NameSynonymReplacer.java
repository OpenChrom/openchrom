/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.ITargetSupplier;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.supplier.IChromatogramSelectionProcessSupplier;
import org.eclipse.chemclipse.model.support.IRetentionTimeRange;
import org.eclipse.chemclipse.model.support.RetentionTimeRange;
import org.eclipse.chemclipse.processing.DataCategory;
import org.eclipse.chemclipse.processing.core.ICategories;
import org.eclipse.chemclipse.processing.supplier.AbstractProcessSupplier;
import org.eclipse.chemclipse.processing.supplier.IProcessSupplier;
import org.eclipse.chemclipse.processing.supplier.IProcessTypeSupplier;
import org.eclipse.chemclipse.processing.supplier.ProcessExecutionContext;
import org.osgi.service.component.annotations.Component;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacement;
import net.openchrom.xxd.process.supplier.templates.model.NameReplacements;
import net.openchrom.xxd.process.supplier.templates.settings.NameSynonymReplacerSettings;

@Component(service = {IProcessTypeSupplier.class})
public class NameSynonymReplacer implements IProcessTypeSupplier {

	private static final String ID = "net.openchrom.xxd.process.supplier.templates.processors.nameReplacer";
	private static final String NAME = "Name Replacer";
	private static final String DESCRIPTION = "Replace common IUPAC names by the given synonyms.";

	@Override
	public String getCategory() {

		return ICategories.CHROMATOGRAM_FILTER;
	}

	@Override
	public Collection<IProcessSupplier<?>> getProcessorSuppliers() {

		return Collections.singleton(new ProcessSupplier(this));
	}

	private static final class ProcessSupplier extends AbstractProcessSupplier<NameSynonymReplacerSettings> implements IChromatogramSelectionProcessSupplier<NameSynonymReplacerSettings> {

		public ProcessSupplier(IProcessTypeSupplier parent) {

			super(ID, NAME, DESCRIPTION, NameSynonymReplacerSettings.class, parent, DataCategory.CSD, DataCategory.MSD, DataCategory.WSD);
		}

		@Override
		public IChromatogramSelection apply(IChromatogramSelection chromatogramSelection, NameSynonymReplacerSettings processSettings, ProcessExecutionContext context) throws InterruptedException {

			if(processSettings != null) {
				NameReplacements nameReplacements = processSettings.getNameReplacements();
				if(nameReplacements != null) {
					if(!nameReplacements.isEmpty()) {
						/*
						 * Map Replacements
						 */
						Map<String, String> replacements = new HashMap<>();
						for(NameReplacement nameReplacement : nameReplacements) {
							replacements.put(nameReplacement.getName(), nameReplacement.getSynonym());
						}
						/*
						 * Replace
						 */
						if(!replacements.isEmpty()) {
							IChromatogram chromatogram = chromatogramSelection.getChromatogram();
							IRetentionTimeRange retentionTimeRange = getRetentionTimeRange(chromatogramSelection);
							applyReplacements(chromatogram, retentionTimeRange, replacements, processSettings);
							if(processSettings.isProcessReferenceChromatograms()) {
								for(IChromatogram chromatogramReference : chromatogram.getReferencedChromatograms()) {
									applyReplacements(chromatogramReference, retentionTimeRange, replacements, processSettings);
								}
							}
						}
					}
				}
			}

			return chromatogramSelection;
		}
	}

	private static void applyReplacements(IChromatogram chromatogram, IRetentionTimeRange retentionTimeRange, Map<String, String> replacements, NameSynonymReplacerSettings processSettings) {

		/*
		 * Scans
		 */
		if(processSettings.isScans()) {
			replaceNames(getScans(chromatogram, retentionTimeRange), replacements);
		}
		/*
		 * Peaks
		 */
		if(processSettings.isPeaks()) {
			replaceNames(getPeaks(chromatogram, retentionTimeRange), replacements);
		}
		/*
		 * Chromatogram
		 */
		if(processSettings.isChromatogram()) {
			replaceNames(Arrays.asList(chromatogram), replacements);
		}
	}

	private static List<ITargetSupplier> getPeaks(IChromatogram chromatogram, IRetentionTimeRange retentionTimeRange) {

		List<ITargetSupplier> targetSuppliers = new ArrayList<>();
		for(IPeak peak : chromatogram.getPeaks(retentionTimeRange)) {
			if(!peak.getTargets().isEmpty()) {
				targetSuppliers.add(peak);
			}
		}

		return targetSuppliers;
	}

	private static List<ITargetSupplier> getScans(IChromatogram chromatogram, IRetentionTimeRange retentionTimeRange) {

		List<ITargetSupplier> targetSuppliers = new ArrayList<>();
		for(IScan scan : chromatogram.getScans()) {
			int retentionTime = scan.getRetentionTime();
			if(retentionTime >= retentionTimeRange.getStartRetentionTime() && retentionTime <= retentionTimeRange.getStopRetentionTime()) {
				if(!scan.getTargets().isEmpty()) {
					targetSuppliers.add(scan);
				}
			}
		}

		return targetSuppliers;
	}

	private static IRetentionTimeRange getRetentionTimeRange(IChromatogramSelection chromatogramSelection) {

		return new RetentionTimeRange(chromatogramSelection.getStartRetentionTime(), chromatogramSelection.getStopRetentionTime());
	}

	private static void replaceNames(List<ITargetSupplier> targetSuppliers, Map<String, String> replacements) {

		for(ITargetSupplier targetSupplier : targetSuppliers) {
			for(IIdentificationTarget identificationTarget : targetSupplier.getTargets()) {
				ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
				String name = libraryInformation.getName();
				String synonym = replacements.get(name);
				if(synonym != null) {
					libraryInformation.getSynonyms().add(name);
					libraryInformation.setName(synonym);
				}
			}
		}
	}
}