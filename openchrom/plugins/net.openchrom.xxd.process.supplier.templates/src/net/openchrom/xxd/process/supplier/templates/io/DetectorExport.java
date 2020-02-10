/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.io;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSettings;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class DetectorExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Detector Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		DetectorSettings detectorSettings = new DetectorSettings();
		//
		int deltaLeft = (int)(PreferenceSupplier.getExportDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int deltaRight = (int)(PreferenceSupplier.getExportDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		boolean optimizeRange = PreferenceSupplier.isExportOptimizeRange();
		boolean useTraces = PreferenceSupplier.isUseTraces();
		int numberTraces = PreferenceSupplier.getExportNumberTraces();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			DetectorSetting detectorSetting = new DetectorSetting();
			detectorSetting.setStartRetentionTime(peakModel.getStartRetentionTime() - deltaLeft);
			detectorSetting.setStopRetentionTime(peakModel.getStopRetentionTime() + deltaRight);
			detectorSetting.setDetectorType(PeakType.VV);
			detectorSetting.setTraces(extractTraces(peak, useTraces, numberTraces));
			detectorSetting.setOptimizeRange(optimizeRange);
			detectorSetting.setReferenceIdentifier("");
			detectorSettings.add(detectorSetting);
		}
		//
		detectorSettings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The detector template has been exported successfully.");
		return processingInfo;
	}
}
