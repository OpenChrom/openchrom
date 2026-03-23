/*******************************************************************************
 * Copyright (c) 2019, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.core;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.compiler.TraceRatioCompiler;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioExportSettings;

public class TraceRatioExport extends AbstractChromatogramExportConverter implements ITemplateExport {

	private static final String DESCRIPTION = "Trace Ratio Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();

		TraceRatioExportSettings traceRatioExportSettings = new TraceRatioExportSettings();
		traceRatioExportSettings.setAllowedDeviationOk(PreferenceSupplier.getAllowedDeviationOk());
		traceRatioExportSettings.setAllowedDeviationWarn(PreferenceSupplier.getAllowedDeviationWarn());
		traceRatioExportSettings.setNumberTraces(PreferenceSupplier.getNumberTraces());

		TraceRatioCompiler traceRatioCompiler = new TraceRatioCompiler();
		if(traceRatioCompiler.compilePeaks(file, peaks, traceRatioExportSettings)) {
			processingInfo.setProcessingResult(file);
			processingInfo.addInfoMessage(DESCRIPTION, "The trace classifier settings have been exported successfully.");
		} else {
			processingInfo.addWarnMessage(DESCRIPTION, "Something went wrong to compile the trace ratios.");
		}

		return processingInfo;
	}
}
