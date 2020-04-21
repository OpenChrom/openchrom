/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.msd.identifier.peak.IPeakIdentifierMSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationResults;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.peaks.AbstractPeakIdentifier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class PeakReviewMSD<T> extends AbstractPeakIdentifier implements IPeakIdentifierMSD<IIdentificationResults> {

	@Override
	public IProcessingInfo<IIdentificationResults> identify(List<? extends IPeakMSD> peaks, IPeakIdentifierSettingsMSD peakIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<IIdentificationResults> processingInfo = new ProcessingInfo<IIdentificationResults>();
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
				processingInfo.addErrorMessage("PeakReviewMSD", "Execution failed", e);
			}
		}
		return processingInfo;
	}

	private IChromatogram<?> getChromatogram(List<? extends IPeakMSD> peaks) {

		for(IPeak peak : peaks) {
			if(peak instanceof IChromatogramPeak) {
				IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
				return chromatogramPeak.getChromatogram();
			}
		}
		return null;
	}
}
