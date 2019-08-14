/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;

public class TimeRatioExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Time Ratio Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		TargetExtendedComparator comparator = new TargetExtendedComparator(SortOrder.DESC);
		//
		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		TimeRatios settings = new TimeRatios();
		//
		float deviationWarn = PreferenceSupplier.getAllowedDeviation();
		float deviationError = PreferenceSupplier.getAllowedDeviationWarn();
		//
		for(IPeak peak : peaks) {
			String name = getName(peak, comparator);
			if(!"".equals(name)) {
				IPeakModel peakModel = peak.getPeakModel();
				TimeRatio timeRatio = new TimeRatio();
				timeRatio.setName(name);
				timeRatio.setExpectedRetentionTime(peakModel.getRetentionTimeAtPeakMaximum());
				timeRatio.setDeviationWarn(deviationWarn);
				timeRatio.setDeviationError(deviationError);
				settings.add(timeRatio);
			}
		}
		//
		settings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The time classifier settings have been exported successfully.");
		return processingInfo;
	}
}
