/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.ui.support.ReviewSupport;

public class PeakLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String START_RETENTION_TIME = "Start [min]";
	public static final String STOP_RETENTION_TIME = "Stop [min]";
	public static final String NAME = "Name";
	public static final String AREA = "Area";
	//
	public static final int INDEX_NAME = 2;
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");
	//
	public static final String[] TITLES = { //
			START_RETENTION_TIME, //
			STOP_RETENTION_TIME, //
			NAME, //
			AREA //
	};
	public static final int[] BOUNDS = { //
			100, //
			100, //
			200, //
			100 //
	};

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof IPeak peak) {
			IPeakModel peakModel = peak.getPeakModel();
			//
			switch(columnIndex) {
				case 0:
					text = decimalFormat.format(peakModel.getStartRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
					break;
				case 1:
					text = decimalFormat.format(peakModel.getStopRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
					break;
				case 2:
					text = ReviewSupport.getName(peak);
					break;
				case 3:
					text = decimalFormat.format(peak.getIntegratedArea());
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK, IApplicationImageProvider.SIZE_16x16);
	}
}
