/*******************************************************************************
 * Copyright (c) 2022, 2023 Lablicate GmbH.
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

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public abstract class AbstractTemplateExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	protected void setPosition(IPeak peak, RetentionIndexMap retentionIndexMap, AbstractSetting setting, PositionDirective positionDirective, double deltaLeft, double deltaRight) {

		IPeakModel peakModel = peak.getPeakModel();
		setting.setPositionDirective(positionDirective);
		int startRetentionTime = peakModel.getStartRetentionTime();
		int stopRetentionTime = peakModel.getStopRetentionTime();
		//
		switch(positionDirective) {
			case RETENTION_TIME_MS:
				setting.setPositionStart(startRetentionTime - deltaLeft);
				setting.setPositionStop(stopRetentionTime + deltaRight);
				break;
			case RETENTION_TIME_MIN:
				setting.setPositionStart((startRetentionTime - deltaLeft) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				setting.setPositionStop((stopRetentionTime + deltaRight) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				break;
			case RETENTION_INDEX:
				setting.setPositionStart(Math.round(retentionIndexMap.getRetentionIndex(startRetentionTime)) - deltaLeft);
				setting.setPositionStop(Math.round(retentionIndexMap.getRetentionIndex(stopRetentionTime)) + deltaRight);
				break;
			default:
				break;
		}
	}
}
