/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.chromatogram.msd.identifier.peak.IPeakIdentifierMSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.IPeakIdentificationResults;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.io.ITemplateExport;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.peaks.AbstractPeakIdentifier;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.ChromatogramValidator;

public class PeakReviewDirectMSD extends AbstractPeakIdentifier implements IPeakIdentifierMSD, ITemplateExport {

	private static final String DESCRIPTION = "PeakReviewMSD";
	private boolean cancelled = false;

	@Override
	public IProcessingInfo<IPeakIdentificationResults> identify(List<? extends IPeakMSD> peaks, IPeakIdentifierSettingsMSD peakIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<IPeakIdentificationResults> processingInfo = new ProcessingInfo<>();
		if(peaks == null || peaks.isEmpty()) {
			processingInfo.addErrorMessage(DESCRIPTION, "No peaks have been found in the current selection.");
		} else {
			runProcess(peaks, processingInfo);
		}
		return processingInfo;
	}

	public boolean isCancelled() {

		return cancelled;
	}

	private void runProcess(List<? extends IPeakMSD> peaks, IProcessingInfo<IPeakIdentificationResults> processingInfo) {

		/*
		 * No settings: peakIdentifierSettings == null
		 */
		IChromatogram chromatogram = getChromatogram(peaks);
		List<ReviewSetting> reviewSettings = new ArrayList<>();
		for(IPeak peak : peaks) {
			if(!peak.getTargets().isEmpty()) {
				IIdentificationTarget identificationTarget = IIdentificationTarget.getIdentificationTarget(peak);
				if(identificationTarget != null) {
					ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
					IPeakModel peakModel = peak.getPeakModel();
					ReviewSetting reviewSetting = new ReviewSetting();
					reviewSetting.setStartRetentionTime(peakModel.getStartRetentionTime());
					reviewSetting.setStopRetentionTime(peakModel.getStopRetentionTime());
					reviewSetting.setName(libraryInformation.getName());
					reviewSetting.setCasNumber(libraryInformation.getCasNumber());
					reviewSetting.setPeakType(PreferenceSupplier.getReviewPeakType());
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
			List<ReviewSetting> filteredReviewSettings = ChromatogramValidator.filterValidReviewSettings(chromatogram, settings);
			if(filteredReviewSettings.isEmpty()) {
				processingInfo.addWarnMessage(DESCRIPTION, "The chromatogram doesn't contain any of the given peak traces.");
			} else {
				try {
					DisplayUtils.executeInUserInterfaceThread(new Runnable() {

						@Override
						public void run() {

							ProcessReviewSettings processSettings = new ProcessReviewSettings(processingInfo, chromatogram, settings);
							Shell shell = DisplayUtils.getShell();
							PeakReviewSupport peakReviewSupport = new PeakReviewSupport();
							peakReviewSupport.addSettings(shell, processSettings);
							cancelled = peakReviewSupport.isCancelled();
						}
					});
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch(ExecutionException e) {
					processingInfo.addErrorMessage(DESCRIPTION, "The execution failed, see attached log file.", e);
				}
			}
		}
	}

	private IChromatogram getChromatogram(List<? extends IPeakMSD> peaks) {

		for(IPeakMSD peak : peaks) {
			if(peak instanceof IChromatogramPeak chromatogramPeak) {
				return chromatogramPeak.getChromatogram();
			}
		}

		return null;
	}
}