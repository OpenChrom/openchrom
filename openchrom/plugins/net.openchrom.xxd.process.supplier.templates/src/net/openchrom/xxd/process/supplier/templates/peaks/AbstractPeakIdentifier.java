/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.IRetentionTimeRange;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakIdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.util.TracesUtil;

public abstract class AbstractPeakIdentifier {

	private static final Logger logger = Logger.getLogger(AbstractPeakIdentifier.class);

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
				processingInfo.addErrorMessage(PeakIdentifierSettings.IDENTIFIER_DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void identifyPeak(List<? extends IPeak> peaks, IdentifierSetting identifierSetting) {

		PeakSupport retentionTimeSupport = new PeakSupport();
		IRetentionTimeRange retentionTimeRange = retentionTimeSupport.getRetentionTimeRange(peaks, identifierSetting, identifierSetting.getReferenceIdentifier());
		int startRetentionTime = retentionTimeRange.getStartRetentionTime();
		int stopRetentionTime = retentionTimeRange.getStopRetentionTime();
		TracesUtil tracesUtil = new TracesUtil();
		//
		try {
			if(startRetentionTime > 0 && startRetentionTime < stopRetentionTime) {
				for(IPeak peak : peaks) {
					if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
						if(tracesUtil.isTraceMatch(peak, identifierSetting.getTraces())) {
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
							identificationTarget.setIdentifier(PeakIdentifierSettings.IDENTIFIER_DESCRIPTION);
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

	private <T> IProcessingInfo<T> validate(List<? extends IPeak> peaks, IIdentifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<T> processingInfo = new ProcessingInfo<>();
		if(peaks == null) {
			processingInfo.addErrorMessage(PeakIdentifierSettings.IDENTIFIER_DESCRIPTION, "The peaks selection must not be null.");
		}
		if(settings == null) {
			processingInfo.addErrorMessage(PeakIdentifierSettings.IDENTIFIER_DESCRIPTION, "The peak identifier settings must not be null.");
		}
		return processingInfo;
	}
}
