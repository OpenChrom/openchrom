/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSettings;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class DetectorExport extends AbstractTemplateExport {

	private static final String DESCRIPTION = "Detector Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		DetectorSettings detectorSettings = new DetectorSettings();
		//
		double deltaLeft = PreferenceSupplier.getExportDeltaLeftPositionDetector();
		double deltaRight = PreferenceSupplier.getExportDeltaRightPositionDetector();
		boolean optimizeRange = PreferenceSupplier.isExportOptimizeRangeDetector();
		int numberTraces = PreferenceSupplier.getExportNumberTracesDetector();
		PositionDirective positionDirective = PreferenceSupplier.getExportPositionDirectiveDetector();
		RetentionIndexMap retentionIndexMap = new RetentionIndexMap(chromatogram);
		PeakType peakType = PreferenceSupplier.getExportPeakTypeDetector();
		//
		for(IPeak peak : peaks) {
			DetectorSetting setting = new DetectorSetting();
			setPosition(peak, retentionIndexMap, setting, positionDirective, deltaLeft, deltaRight);
			setting.setPeakType(peakType);
			setting.setTraces(extractTraces(peak, numberTraces));
			setting.setOptimizeRange(optimizeRange);
			setting.setReferenceIdentifier(""); // The absolute time is used, hence not needed here.
			//
			ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(peak);
			setting.setName(libraryInformation != null ? libraryInformation.getName() : "");
			detectorSettings.add(setting);
		}
		//
		detectorSettings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The detector template has been exported successfully.");
		return processingInfo;
	}
}