/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.identifier.peak.IPeakIdentifierMSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.targets.IPeakTarget;
import org.eclipse.chemclipse.model.targets.PeakTarget;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakIdentifierSettings;

public class PeakIdentifier implements IPeakIdentifierMSD {

	private static final Logger logger = Logger.getLogger(PeakIdentifier.class);

	/*
	 * ------------------------------------------------------------------------------------------------ MSD
	 */
	@Override
	public IProcessingInfo identify(List<IPeakMSD> peaks, IPeakIdentifierSettingsMSD settings, IProgressMonitor monitor) {

		return applyIdentifier(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo identify(IPeakMSD peak, IPeakIdentifierSettingsMSD settings, IProgressMonitor monitor) {

		return identify(extractPeaks(peak), settings, monitor);
	}

	@Override
	public IProcessingInfo identify(IPeakMSD peak, IProgressMonitor monitor) {

		return identify(peak, getPeakIdentifierSettings(PreferenceSupplier.P_PEAK_IDENTIFIER_LIST_MSD), monitor);
	}

	@Override
	public IProcessingInfo identify(List<IPeakMSD> peaks, IProgressMonitor monitor) {

		return identify(peaks, getPeakIdentifierSettings(PreferenceSupplier.P_PEAK_IDENTIFIER_LIST_MSD), monitor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IProcessingInfo identify(IChromatogramSelectionMSD chromatogramSelectionMSD, IProgressMonitor monitor) {

		return identify(extractPeaks(chromatogramSelectionMSD), monitor);
	}

	/*
	 * ------------------------------------------------------------------------------------------------ Generic
	 */
	private <T> List<T> extractPeaks(T peak) {

		List<T> peaks = new ArrayList<>();
		peaks.add(peak);
		return peaks;
	}

	private List<? extends IPeak> extractPeaks(IChromatogramSelection<? extends IPeak> chromatogramSelection) {

		IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
		List<IPeak> peaks = new ArrayList<>();
		for(IPeak peak : chromatogram.getPeaks(chromatogramSelection)) {
			peaks.add(peak);
		}
		return peaks;
	}

	private PeakIdentifierSettings getPeakIdentifierSettings(String preferenceKey) {

		PeakIdentifierSettings settings = new PeakIdentifierSettings();
		settings.setIdentifierSettings(PreferenceSupplier.getSettings(preferenceKey, ""));
		return settings;
	}

	private IProcessingInfo applyIdentifier(List<? extends IPeak> peaks, IIdentifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakIdentifierSettings) {
				PeakIdentifierSettings peakIdentifierSettings = (PeakIdentifierSettings)settings;
				for(IdentifierSettings identifierSettings : peakIdentifierSettings.getIdentifierSettings()) {
					identifyPeakBySettings(peaks, identifierSettings);
				}
			} else {
				processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void identifyPeakBySettings(List<? extends IPeak> peaks, IdentifierSettings identifierSettings) {

		int start = (int)(identifierSettings.getStartRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		int stop = (int)(identifierSettings.getStopRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		identifyPeak(peaks, start, stop, identifierSettings.getName());
	}

	private void identifyPeak(List<? extends IPeak> peaks, int startRetentionTime, int stopRetentionTime, String name) {

		try {
			if(startRetentionTime > 0 && startRetentionTime < stopRetentionTime) {
				for(IPeak peak : peaks) {
					if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
						/*
						 * Target
						 */
						ILibraryInformation libraryInformation = new LibraryInformation();
						libraryInformation.setName(name);
						libraryInformation.setCasNumber("");
						libraryInformation.setComments("");
						libraryInformation.setContributor("");
						libraryInformation.setReferenceIdentifier("");
						IComparisonResult comparisonResult = ComparisonResult.createBestMatchComparisonResult();
						IPeakTarget identificationTarget = new PeakTarget(libraryInformation, comparisonResult);
						identificationTarget.setIdentifier(PeakIdentifierSettings.DESCRIPTION);
						peak.addTarget(identificationTarget);
					}
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}

	private boolean isPeakMatch(IPeak peak, int startRetentionTime, int stopRetentionTime) {

		IPeakModel peakModel = peak.getPeakModel();
		int retentionTime = peakModel.getRetentionTimeAtPeakMaximum();
		if(retentionTime >= startRetentionTime && retentionTime <= stopRetentionTime) {
			return true;
		} else {
			return false;
		}
	}

	private IProcessingInfo validate(List<? extends IPeak> peaks, IIdentifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		if(peaks == null) {
			processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The peaks selection must not be null.");
		}
		if(settings == null) {
			processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The peak identifier settings must not be null.");
		}
		return processingInfo;
	}
}
