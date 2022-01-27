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

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.chromatogram.wsd.identifier.peak.IPeakIdentifierWSD;
import org.eclipse.chemclipse.chromatogram.wsd.identifier.settings.IPeakIdentifierSettingsWSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationResults;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.peaks.AbstractPeakIdentifier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class PeakReviewWSD<T> extends AbstractPeakIdentifier implements IPeakIdentifierWSD<IIdentificationResults> {

	private static final String DESCRIPTION = "PeakReviewWSD";

	@Override
	public IProcessingInfo<IIdentificationResults> identify(List<? extends IPeakWSD> peaks, IPeakIdentifierSettingsWSD peakIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<IIdentificationResults> processingInfo = new ProcessingInfo<IIdentificationResults>();
		if(peaks == null || peaks.size() == 0) {
			processingInfo.addErrorMessage(DESCRIPTION, "No peak(s) have been found in the current selection.");
		} else {
			runProcess(peaks, peakIdentifierSettings, processingInfo, monitor);
		}
		return processingInfo;
	}

	private void runProcess(List<? extends IPeakWSD> peaks, IPeakIdentifierSettingsWSD peakIdentifierSettings, IProcessingInfo<IIdentificationResults> processingInfo, IProgressMonitor monitor) {

		if(peakIdentifierSettings instanceof PeakReviewSettings) {
			PeakReviewSettings settings = (PeakReviewSettings)peakIdentifierSettings;
			IChromatogram<?> chromatogram = getChromatogram(peaks);
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

		for(IPeak peak : peaks) {
			if(peak instanceof IChromatogramPeak) {
				IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
				return chromatogramPeak.getChromatogram();
			}
		}
		//
		return null;
	}
}