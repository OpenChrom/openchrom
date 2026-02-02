/*******************************************************************************
 * Copyright (c) 2020, 2026 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.IPeakIdentificationResults;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.literature.LiteratureReference;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.io.ITemplateExport;
import net.openchrom.xxd.process.supplier.templates.model.DetectorType;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.peaks.AbstractPeakIdentifier;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewDirectSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.ChromatogramValidator;

public abstract class AbstractPeakReview extends AbstractPeakIdentifier implements ITemplateExport {

	private boolean cancelled = false;

	public boolean isCancelled() {

		return cancelled;
	}

	public List<LiteratureReference> getLiteratureReferences() {

		return null;
	}

	protected void applyPeakReviewDirectSettings(IIdentifierSettings identifierSettings) {

		if(identifierSettings instanceof PeakReviewDirectSettings settings) {
			/*
			 * Set the detector type.
			 */
			DetectorType detectorType = settings.getDetectorType();
			PeakType peakType;
			switch(detectorType) {
				case BB:
					peakType = PeakType.BB;
					break;
				case MM:
					peakType = PeakType.MM;
					break;
				case CB:
					peakType = PeakType.CB;
					break;
				case VV:
					peakType = PeakType.VV;
					break;
				default:
					peakType = PeakType.DEFAULT;
					break;
			}
			if(peakType != PeakType.DEFAULT) {
				PreferenceSupplier.setReviewPeakType(peakType);
			}
			/*
			 * Peak Review Delta
			 */
			if(settings.getReviewDeltaLeft() > 0) {
				PreferenceSupplier.setReviewDeltaLeftMilliseconds(settings.getReviewDeltaLeft());
			}
			if(settings.getReviewDeltaRight() > 0) {
				PreferenceSupplier.setReviewDeltaRightMilliseconds(settings.getReviewDeltaRight());
			}
		}
	}

	protected IProcessingInfo<IPeakIdentificationResults> runProcess(List<? extends IPeak> peaks, IIdentifierSettings identifierSettings, String description, IProgressMonitor monitor) {

		IProcessingInfo<IPeakIdentificationResults> processingInfo = new ProcessingInfo<>();
		if(peaks == null || peaks.isEmpty()) {
			processingInfo.addWarnMessage(description, "No peaks are available in the current selection.");
		} else {
			if(identifierSettings instanceof PeakReviewSettings peakReviewSettings) {
				executeReviewWizard(peaks, peakReviewSettings, description, processingInfo, monitor);
			} else {
				if(identifierSettings == null) {
					prepareDirectReview(peaks, description, processingInfo, monitor);
				} else {
					processingInfo.addWarnMessage(description, "The settings must be an instance of PeakReviewSettings.");
				}
			}
		}

		return processingInfo;
	}

	private void prepareDirectReview(List<? extends IPeak> peaks, String description, IProcessingInfo<IPeakIdentificationResults> processingInfo, IProgressMonitor monitor) {

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
			PeakReviewSettings peakReviewSettings = new PeakReviewSettings();
			peakReviewSettings.setReviewSettings(reviewSettings);
			executeReviewWizard(peaks, peakReviewSettings, description, processingInfo, monitor);
		} else {
			processingInfo.addWarnMessage(description, "No peak review settings are available.");
		}
	}

	private void executeReviewWizard(List<? extends IPeak> peaks, PeakReviewSettings peakReviewSettings, String description, IProcessingInfo<IPeakIdentificationResults> processingInfo, IProgressMonitor monitor) {

		/*
		 * Retention indices (RI) will be adjusted to retention time (minutes) dynamically.
		 */
		IChromatogram chromatogram = getChromatogram(peaks);
		List<ReviewSetting> settingsValidated = ChromatogramValidator.filterValidReviewSettings(chromatogram, peakReviewSettings);
		if(settingsValidated.isEmpty()) {
			processingInfo.addWarnMessage(description, "The chromatogram doesn't contain any of the given peak traces.");
		} else {
			/*
			 * Run the wizard
			 */
			try {
				DisplayUtils.executeInUserInterfaceThread(new Runnable() {

					@Override
					public void run() {

						Shell shell = DisplayUtils.getShell();
						PeakReviewSupport peakReviewSupport = new PeakReviewSupport();
						ProcessReviewSettings processReviewSettings = new ProcessReviewSettings(processingInfo, chromatogram, peakReviewSettings);
						peakReviewSupport.addSettings(shell, processReviewSettings);
						setCancelStatus(peakReviewSupport.isCancelled());
					}
				});
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch(ExecutionException e) {
				processingInfo.addErrorMessage(description, "The execution failed, see attached log file.", e);
			}
		}
	}

	private IChromatogram getChromatogram(List<? extends IPeak> peaks) {

		for(IPeak peak : peaks) {
			if(peak instanceof IChromatogramPeak chromatogramPeak) {
				return chromatogramPeak.getChromatogram();
			}
		}

		return null;
	}

	private void setCancelStatus(boolean cancelled) {

		this.cancelled = cancelled;
	}
}