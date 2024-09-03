/*******************************************************************************
 * Copyright (c) 2022, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.compiler;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

import net.openchrom.xxd.process.supplier.templates.io.ITemplateExport;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSettings;
import net.openchrom.xxd.process.supplier.templates.system.ReviewExportProcessSettings;

public class ReviewTemplateCompiler implements ITemplateExport {

	public boolean compilePeaks(File file, List<? extends IPeak> peaks, ReviewExportProcessSettings reviewExportProcessSettings) {

		ReviewSettings reviewSettings = new ReviewSettings();
		//
		int deltaLeft = reviewExportProcessSettings.getRetentionTimeDeltaLeft();
		int deltaRight = reviewExportProcessSettings.getRetentionTimeDeltaRight();
		int numberTraces = reviewExportProcessSettings.getNumberTraces();
		boolean optimizeRange = reviewExportProcessSettings.isOptimizeRange();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(peak);
			if(libraryInformation != null) {
				ReviewSetting setting = new ReviewSetting();
				setting.setPositionDirective(PositionDirective.RETENTION_TIME_MIN);
				setting.setPositionStart((peakModel.getStartRetentionTime() - deltaLeft) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				setting.setPositionStop((peakModel.getStopRetentionTime() + deltaRight) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				setting.setName(libraryInformation.getName());
				setting.setCasNumber(libraryInformation.getCasNumber());
				setting.setTraces(extractTraces(peak, numberTraces));
				setting.setOptimizeRange(optimizeRange);
				reviewSettings.add(setting);
			}
		}
		//
		return reviewSettings.exportItems(file);
	}
}
