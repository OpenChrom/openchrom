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
package net.openchrom.xxd.process.supplier.templates.io;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public abstract class AbstractTemplateExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	protected void setPosition(IChromatogram<? extends IPeak> chromatogram, IPeak peak, AbstractSetting setting, PositionDirective positionDirective, double deltaLeft, double deltaRight) {

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
				setting.setPositionStart((startRetentionTime - deltaLeft) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				setting.setPositionStop((stopRetentionTime + deltaRight) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				break;
			case RETENTION_INDEX:
				int retentionIndexStart = 0;
				int retentionIndexStop = 0;
				int startScan = chromatogram.getScanNumber(startRetentionTime);
				int stopScan = chromatogram.getScanNumber(stopRetentionTime);
				if(startScan > 0 && stopScan > 0) {
					retentionIndexStart = (int)Math.round(chromatogram.getScan(startScan).getRetentionIndex() - deltaLeft);
					retentionIndexStop = (int)Math.round(chromatogram.getScan(stopScan).getRetentionIndex() + deltaRight);
				}
				setting.setPositionStart(retentionIndexStart);
				setting.setPositionStop(retentionIndexStop);
				break;
			default:
				break;
		}
	}
}