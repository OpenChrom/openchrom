/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import java.io.File;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.ranges.TimeRange;
import org.eclipse.chemclipse.model.ranges.TimeRanges;
import org.eclipse.chemclipse.model.targets.TargetSupport;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class ChromatogramExportTimeRanges extends AbstractChromatogramExportConverter implements IChromatogramExportConverter {

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			/*
			 * Settings
			 */
			TimeRanges timeRanges = new TimeRanges();
			for(IPeak peak : chromatogram.getPeaks()) {
				String name = TargetSupport.getBestTargetLibraryField(peak);
				if(!name.isEmpty()) {
					IPeakModel peakModel = peak.getPeakModel();
					int start = peakModel.getStartRetentionTime();
					int center = peakModel.getRetentionTimeAtPeakMaximum();
					int stop = peakModel.getStopRetentionTime();
					timeRanges.add(new TimeRange(name, start, center, stop));
				}
			}
			//
			timeRanges.exportItems(file);
			processingInfo.setProcessingResult(file);
		}
		return processingInfo;
	}
}