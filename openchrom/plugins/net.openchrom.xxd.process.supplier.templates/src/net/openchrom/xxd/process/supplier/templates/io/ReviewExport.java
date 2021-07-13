/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
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
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSettings;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class ReviewExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Review Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		ReviewSettings reviewSettings = new ReviewSettings();
		//
		int deltaLeft = PreferenceSupplier.getExportDeltaLeftMillisecondsReview();
		int deltaRight = PreferenceSupplier.getExportDeltaRightMillisecondsReview();
		int numberTraces = PreferenceSupplier.getExportNumberTracesReview();
		boolean optimizeRange = PreferenceSupplier.isExportOptimizeRangeReview();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			float retentionIndex = peakModel.getPeakMaximum().getRetentionIndex();
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
			ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(peak.getTargets(), identificationTargetComparator);
			if(libraryInformation != null) {
				ReviewSetting reviewSetting = new ReviewSetting();
				reviewSetting.setStartRetentionTime(peakModel.getStartRetentionTime() - deltaLeft);
				reviewSetting.setStopRetentionTime(peakModel.getStopRetentionTime() + deltaRight);
				reviewSetting.setName(libraryInformation.getName());
				reviewSetting.setCasNumber(libraryInformation.getCasNumber());
				reviewSetting.setTraces(extractTraces(peak, numberTraces));
				reviewSetting.setOptimizeRange(optimizeRange);
				reviewSettings.add(reviewSetting);
			}
		}
		//
		reviewSettings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The review template has been exported successfully.");
		return processingInfo;
	}
}
