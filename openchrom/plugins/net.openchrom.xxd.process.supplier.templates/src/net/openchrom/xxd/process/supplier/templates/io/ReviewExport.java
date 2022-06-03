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
package net.openchrom.xxd.process.supplier.templates.io;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.compiler.ReviewTemplateCompiler;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.system.ReviewExportProcessSettings;

public class ReviewExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Review Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		//
		ReviewExportProcessSettings reviewExportProcessSettings = new ReviewExportProcessSettings();
		reviewExportProcessSettings.setRetentionTimeDeltaLeft(PreferenceSupplier.getExportDeltaLeftMillisecondsReview());
		reviewExportProcessSettings.setRetentionTimeDeltaRight(PreferenceSupplier.getExportDeltaRightMillisecondsReview());
		reviewExportProcessSettings.setNumberTraces(PreferenceSupplier.getExportNumberTracesReview());
		reviewExportProcessSettings.setOptimizeRange(PreferenceSupplier.isExportOptimizeRangeReview());
		ReviewTemplateCompiler reviewTemplateCompiler = new ReviewTemplateCompiler();
		//
		if(reviewTemplateCompiler.compilePeaks(file, peaks, reviewExportProcessSettings)) {
			processingInfo.setProcessingResult(file);
			processingInfo.addInfoMessage(DESCRIPTION, "The review template has been exported successfully.");
		} else {
			processingInfo.addWarnMessage(DESCRIPTION, "Something has gone wrong to export the review template.");
		}
		//
		return processingInfo;
	}
}