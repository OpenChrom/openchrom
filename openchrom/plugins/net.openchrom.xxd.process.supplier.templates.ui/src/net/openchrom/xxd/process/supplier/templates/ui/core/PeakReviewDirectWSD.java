/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.chromatogram.wsd.identifier.peak.IPeakIdentifierWSD;
import org.eclipse.chemclipse.chromatogram.wsd.identifier.settings.IPeakIdentifierSettingsWSD;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.IIdentificationResults;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.io.ITemplateExport;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.peaks.AbstractPeakIdentifier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class PeakReviewDirectWSD<T> extends AbstractPeakIdentifier implements IPeakIdentifierWSD<IIdentificationResults>, ITemplateExport {

	private static final String DESCRIPTION = "PeakReviewWSD";

	@Override
	public IProcessingInfo<IIdentificationResults> identify(List<? extends IPeakWSD> peaks, IPeakIdentifierSettingsWSD peakIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<IIdentificationResults> processingInfo = new ProcessingInfo<>();
		if(peaks == null || peaks.isEmpty()) {
			processingInfo.addErrorMessage(DESCRIPTION, "No peaks have been found in the current selection.");
		} else {
			runProcess(peaks, peakIdentifierSettings, processingInfo, monitor);
		}
		return processingInfo;
	}

	private void runProcess(List<? extends IPeakWSD> peaks, IPeakIdentifierSettingsWSD peakIdentifierSettings, IProcessingInfo<IIdentificationResults> processingInfo, IProgressMonitor monitor) {

		/*
		 * No settings: peakIdentifierSettings == null
		 */
		IChromatogram<?> chromatogram = getChromatogram(peaks);
		List<ReviewSetting> reviewSettings = new ArrayList<>();
		for(IPeak peak : peaks) {
			if(!peak.getTargets().isEmpty()) {
				float retentionIndex = peak.getPeakModel().getPeakMaximum().getRetentionIndex();
				IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(retentionIndex);
				IIdentificationTarget identificationTarget = IIdentificationTarget.getBestIdentificationTarget(peak.getTargets(), identificationTargetComparator);
				if(identificationTarget != null) {
					ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
					IPeakModel peakModel = peak.getPeakModel();
					ReviewSetting reviewSetting = new ReviewSetting();
					reviewSetting.setStartRetentionTime(peakModel.getStartRetentionTime());
					reviewSetting.setStopRetentionTime(peakModel.getStopRetentionTime());
					reviewSetting.setName(libraryInformation.getName());
					reviewSetting.setCasNumber(libraryInformation.getCasNumber());
					reviewSetting.setDetectorType(PeakType.VV);
					reviewSetting.setTraces(getTraces(peak));
					reviewSetting.setOptimizeRange(true);
					reviewSettings.add(reviewSetting);
				}
			}
		}
		/*
		 * Check, that at least one review setting is set.
		 */
		if(!reviewSettings.isEmpty()) {
			PeakReviewSettings settings = new PeakReviewSettings();
			settings.setReviewSettings(reviewSettings);
			ProcessReviewSettings processSettings = new ProcessReviewSettings(processingInfo, chromatogram, settings);
			try {
				DisplayUtils.executeInUserInterfaceThread(new Runnable() {

					@Override
					public void run() {

						Shell shell = DisplayUtils.getShell();
						PeakReviewSupport peakReviewSupport = new PeakReviewSupport();
						peakReviewSupport.addSettings(shell, processSettings);
					}
				});
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch(ExecutionException e) {
				processingInfo.addErrorMessage(DESCRIPTION, "The execution failed, see attached log file.", e);
			}
		}
	}

	private IChromatogram<?> getChromatogram(List<? extends IPeakWSD> peaks) {

		for(IPeakWSD peak : peaks) {
			if(peak instanceof IChromatogramPeak) {
				IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
				return chromatogramPeak.getChromatogram();
			}
		}
		//
		return null;
	}
}