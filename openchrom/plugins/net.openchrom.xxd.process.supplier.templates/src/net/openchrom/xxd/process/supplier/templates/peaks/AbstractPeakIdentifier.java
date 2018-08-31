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
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakIdentifierSettings;

public abstract class AbstractPeakIdentifier {

	private static final Logger logger = Logger.getLogger(AbstractPeakIdentifier.class);

	protected <T> List<T> extractPeaks(T peak) {

		List<T> peaks = new ArrayList<>();
		peaks.add(peak);
		return peaks;
	}

	protected List<? extends IPeak> extractPeaks(IChromatogramSelection<? extends IPeak> chromatogramSelection) {

		IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
		List<IPeak> peaks = new ArrayList<>();
		for(IPeak peak : chromatogram.getPeaks(chromatogramSelection)) {
			peaks.add(peak);
		}
		return peaks;
	}

	protected PeakIdentifierSettings getSettings(String preferenceKey) {

		PeakIdentifierSettings settings = new PeakIdentifierSettings();
		settings.setIdentifierSettings(PreferenceSupplier.getSettings(preferenceKey, ""));
		return settings;
	}

	protected IProcessingInfo applyIdentifier(List<? extends IPeak> peaks, IIdentifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakIdentifierSettings) {
				PeakIdentifierSettings peakIdentifierSettings = (PeakIdentifierSettings)settings;
				for(IdentifierSettings identifierSettings : peakIdentifierSettings.getIdentifierSettings()) {
					identifyPeak(peaks, identifierSettings);
				}
			} else {
				processingInfo.addErrorMessage(PeakIdentifierSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void identifyPeak(List<? extends IPeak> peaks, IdentifierSettings identifierSettings) {

		int startRetentionTime = (int)(identifierSettings.getStartRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		int stopRetentionTime = (int)(identifierSettings.getStopRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		//
		try {
			if(startRetentionTime > 0 && startRetentionTime < stopRetentionTime) {
				for(IPeak peak : peaks) {
					if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
						/*
						 * Target
						 */
						ILibraryInformation libraryInformation = new LibraryInformation();
						libraryInformation.setName(identifierSettings.getName());
						libraryInformation.setCasNumber(identifierSettings.getCasNumber());
						libraryInformation.setComments(identifierSettings.getComments());
						libraryInformation.setContributor(identifierSettings.getContributor());
						libraryInformation.setReferenceIdentifier(identifierSettings.getReferenceId());
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
