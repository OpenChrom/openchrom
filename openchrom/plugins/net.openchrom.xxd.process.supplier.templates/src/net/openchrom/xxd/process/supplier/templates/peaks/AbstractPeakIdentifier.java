/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 * Christoph Läubrich - adjust naming of methods
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.RetentionTimeRange;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakIdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;

public abstract class AbstractPeakIdentifier {

	private static final Logger logger = Logger.getLogger(AbstractPeakIdentifier.class);
	//
	private PeakIdentifierListUtil listUtil = new PeakIdentifierListUtil();

	protected <T> List<T> extractPeaks(T peak) {

		List<T> peaks = new ArrayList<>();
		peaks.add(peak);
		return peaks;
	}

	protected <T extends IPeak> List<T> extractPeaks(IChromatogramSelection<T, ?> chromatogramSelection) {

		IChromatogram<T> chromatogram = chromatogramSelection.getChromatogram();
		List<T> peaks = new ArrayList<>();
		for(T peak : chromatogram.getPeaks(chromatogramSelection)) {
			peaks.add(peak);
		}
		return peaks;
	}

	protected PeakIdentifierSettings getSettings(String preferenceKey) {

		PeakIdentifierSettings settings = new PeakIdentifierSettings();
		settings.setIdentifierSettings(PreferenceSupplier.getSettings(preferenceKey, ""));
		return settings;
	}

	protected <T> IProcessingInfo<T> applyIdentifier(List<? extends IPeak> peaks, IIdentifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<T> processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakIdentifierSettings) {
				PeakIdentifierSettings peakIdentifierSettings = (PeakIdentifierSettings)settings;
				for(IdentifierSetting identifierSetting : peakIdentifierSettings.getIdentifierSettingsList()) {
					identifyPeak(peaks, identifierSetting);
				}
			} else {
				processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void identifyPeak(List<? extends IPeak> peaks, IdentifierSetting identifierSetting) {

		PeakSupport retentionTimeSupport = new PeakSupport();
		RetentionTimeRange retentionTimeRange = retentionTimeSupport.getRetentionTimeRange(peaks, identifierSetting, identifierSetting.getReferenceIdentifier());
		int startRetentionTime = retentionTimeRange.getStartRetentionTime();
		int stopRetentionTime = retentionTimeRange.getStopRetentionTime();
		//
		try {
			if(startRetentionTime > 0 && startRetentionTime < stopRetentionTime) {
				for(IPeak peak : peaks) {
					if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
						if(isTraceMatch(peak, identifierSetting)) {
							/*
							 * Target
							 */
							ILibraryInformation libraryInformation = new LibraryInformation();
							libraryInformation.setName(identifierSetting.getName());
							libraryInformation.setCasNumber(identifierSetting.getCasNumber());
							libraryInformation.setComments(identifierSetting.getComments());
							libraryInformation.setContributor(identifierSetting.getContributor());
							libraryInformation.setReferenceIdentifier(identifierSetting.getReference());
							IComparisonResult comparisonResult = ComparisonResult.createBestMatchComparisonResult();
							IIdentificationTarget identificationTarget = new IdentificationTarget(libraryInformation, comparisonResult);
							identificationTarget.setIdentifier(PeakIdentifierSettings.DESCRIPTION);
							peak.getTargets().add(identificationTarget);
						}
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

	/**
	 * MSD, WSD is checked
	 * CSD always true
	 *
	 * @return
	 */
	private boolean isTraceMatch(IPeak peak, IdentifierSetting identifierSetting) {

		boolean traceMatch = false;
		//
		if(peak instanceof IPeakCSD) {
			traceMatch = true;
		} else {
			/*
			 * MSD, WSD
			 */
			IScan scan = peak.getPeakModel().getPeakMaximum();
			Set<Integer> traces = listUtil.extractTraces(identifierSetting.getTraces());
			int detected = 0;
			for(int trace : traces) {
				if(isTraceContained(scan, trace)) {
					detected++;
				}
			}
			//
			if(detected == traces.size()) {
				traceMatch = true;
			}
		}
		//
		return traceMatch;
	}

	private boolean isTraceContained(IScan scan, int trace) {

		boolean isTraceContained = false;
		//
		if(scan instanceof IScanMSD) {
			try {
				IScanMSD scanMSD = (IScanMSD)scan;
				IIon ion = scanMSD.getIon(trace);
				if(ion != null) {
					isTraceContained = true;
				}
			} catch(Exception e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		} else if(scan instanceof IScanWSD) {
			IScanWSD scanWSD = (IScanWSD)scan;
			Optional<IScanSignalWSD> optional = scanWSD.getScanSignal((double)trace);
			isTraceContained = optional.isPresent();
		}
		//
		return isTraceContained;
	}

	private <T> IProcessingInfo<T> validate(List<? extends IPeak> peaks, IIdentifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<T> processingInfo = new ProcessingInfo<>();
		if(peaks == null) {
			processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The peaks selection must not be null.");
		}
		if(settings == null) {
			processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The peak identifier settings must not be null.");
		}
		return processingInfo;
	}
}
