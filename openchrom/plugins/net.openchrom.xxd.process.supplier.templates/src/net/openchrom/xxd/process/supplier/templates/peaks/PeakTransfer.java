/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.IPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.IPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetector;
import org.eclipse.chemclipse.chromatogram.peak.detector.settings.IPeakDetectorSettings;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramPeakWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakTransferSettings;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;

@SuppressWarnings("rawtypes")
public class PeakTransfer extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakTransferSettings settings = getSettings();
		return detect(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakTransferSettings settings = getSettings();
		return detect(chromatogramSelection, settings, monitor);
	}

	private PeakTransferSettings getSettings() {

		PeakTransferSettings settings = new PeakTransferSettings();
		settings.setUseBestTargetOnly(PreferenceSupplier.isTransferUseBestTargetOnly());
		settings.setDeltaRetentionTimeLeft(PreferenceSupplier.getTransferRetentionTimeMinutesLeft());
		settings.setDeltaRetentionTimeRight(PreferenceSupplier.getTransferRetentionTimeMinutesRight());
		settings.setNumberTraces(PreferenceSupplier.getTransferNumberTraces());
		return settings;
	}

	@SuppressWarnings({"unchecked"})
	private IProcessingInfo applyDetector(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakTransferSettings) {
				PeakTransferSettings peakTransferSettings = (PeakTransferSettings)settings;
				transferPeaks(chromatogramSelection, peakTransferSettings);
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	@SuppressWarnings("unchecked")
	private void transferPeaks(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, PeakTransferSettings peakTransferSettings) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();
		List<IPeak> peaks = chromatogram.getPeaks(chromatogramSelection);
		List<IChromatogram> referencedChromatograms = chromatogram.getReferencedChromatograms();
		for(IChromatogram referencedChromatogram : referencedChromatograms) {
			transferPeaks(peaks, referencedChromatogram, peakTransferSettings);
		}
	}

	@SuppressWarnings("unchecked")
	private void transferPeaks(List<IPeak> peaks, IChromatogram chromatogram, PeakTransferSettings peakTransferSettings) {

		/*
		 * General Settings
		 */
		boolean useBestTargetOnly = peakTransferSettings.isUseBestTargetOnly();
		TargetExtendedComparator comparator = new TargetExtendedComparator(SortOrder.DESC);
		PeakSupport peakSupport = new PeakSupport();
		int deltaRetentionTimeLeft = (int)(peakTransferSettings.getDeltaRetentionTimeLeft() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int deltaRetentionTimeRight = (int)(peakTransferSettings.getDeltaRetentionTimeRight() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int numberTraces = peakTransferSettings.getNumberTraces();
		//
		for(IPeak peak : peaks) {
			/*
			 * Specific Settings
			 */
			IPeakModel peakModel = peak.getPeakModel();
			int startRetentionTime = peakModel.getStartRetentionTime() - deltaRetentionTimeLeft;
			int stopRetentionTime = peakModel.getStopRetentionTime() + deltaRetentionTimeRight;
			//
			Set<Integer> traces = new HashSet<>();
			float purity = 1.0f;
			//
			if(peak instanceof IChromatogramPeakMSD) {
				IChromatogramPeakMSD peakMSD = (IChromatogramPeakMSD)peak;
				purity = peakMSD.getPurity();
				if(purity < 1.0f) {
					IScanMSD scanMSD = peakMSD.getExtractedMassSpectrum();
					if(scanMSD.getIons().size() <= numberTraces) {
						IExtractedIonSignal extractedIonSignal = peakMSD.getExtractedMassSpectrum().getExtractedIonSignal();
						for(int ion = extractedIonSignal.getStartIon(); ion <= extractedIonSignal.getStopIon(); ion++) {
							if(extractedIonSignal.getAbundance(ion) > 0.0f) {
								traces.add(ion);
							}
						}
					}
				}
			}
			//
			IPeak peakReference = peakSupport.extractPeakByRetentionTime(chromatogram, startRetentionTime, stopRetentionTime, true, true, traces);
			if(peakReference != null) {
				/*
				 * Adjust the peak model.
				 * Skip it if it's of type MSD, cause the traces already led to an adjusted peak.
				 */
				if(purity > 0.0d && purity < 1.0f) {
					if(peakReference instanceof IChromatogramPeakCSD || peakReference instanceof IChromatogramPeakWSD) {
						IScan peakMaximum = peakReference.getPeakModel().getPeakMaximum();
						float totalSignal = peakMaximum.getTotalSignal();
						peakMaximum.adjustTotalSignal(totalSignal * purity);
					}
				}
				/*
				 * Transfer the targets.
				 */
				if(useBestTargetOnly) {
					IIdentificationTarget identificationTarget = IIdentificationTarget.getBestIdentificationTarget(peak.getTargets(), comparator);
					peakReference.getTargets().add(createIdentificationTarget(identificationTarget));
				} else {
					for(IIdentificationTarget identificationTarget : peak.getTargets()) {
						peakReference.getTargets().add(createIdentificationTarget(identificationTarget));
					}
				}
				chromatogram.addPeak(peakReference);
			}
		}
	}

	private IIdentificationTarget createIdentificationTarget(IIdentificationTarget identificationTarget) {

		ILibraryInformation libraryInformation = new LibraryInformation(identificationTarget.getLibraryInformation());
		IComparisonResult comparisonResult = new ComparisonResult(identificationTarget.getComparisonResult());
		IIdentificationTarget identificationTargetSink = new IdentificationTarget(libraryInformation, comparisonResult);
		identificationTargetSink.setIdentifier(PeakTransferSettings.DESCRIPTION);
		return identificationTargetSink;
	}
}