/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.core;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.compiler.TimeRatioCompiler;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TimeRatioExportSettings;

public class TimeRatioExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Time Ratio Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		//
		TimeRatioExportSettings timeRatioExportSettings = new TimeRatioExportSettings();
		timeRatioExportSettings.setAllowedDeviationOk(PreferenceSupplier.getAllowedDeviationOk());
		timeRatioExportSettings.setAllowedDeviationWarn(PreferenceSupplier.getAllowedDeviationWarn());
		//
		TimeRatioCompiler timeRatioCompiler = new TimeRatioCompiler();
		if(timeRatioCompiler.compilePeaks(file, peaks, timeRatioExportSettings)) {
			processingInfo.setProcessingResult(file);
			processingInfo.addInfoMessage(DESCRIPTION, "The time classifier settings have been exported successfully.");
		} else {
			processingInfo.addWarnMessage(DESCRIPTION, "Something went wrong to compile the time ratios.");
		}
		//
		return processingInfo;
	}
}