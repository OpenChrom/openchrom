/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;

public class PeakReviewLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String NAME = "Name";
	public static final String START_RETENTION_TIME = "Start [min]";
	public static final String STOP_RETENTION_TIME = "Stop [min]";
	public static final String CAS_NUMBER = "CAS";
	public static final String TRACES = "Traces";
	public static final String DETECTOR_TYPE = "Detector Type";
	public static final String OPTIMIZE_RANGE = "Optimize Range (VV)";
	//
	public static final int INDEX_OPTIMIZE_RANGE = 6;
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");
	//
	public static final String[] TITLES = { //
			NAME, //
			START_RETENTION_TIME, //
			STOP_RETENTION_TIME, //
			CAS_NUMBER, //
			TRACES, //
			DETECTOR_TYPE, //
			OPTIMIZE_RANGE //
	};
	public static final int[] BOUNDS = { //
			200, //
			100, //
			100, //
			100, //
			100, //
			50, //
			30 //
	};

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else if(columnIndex == INDEX_OPTIMIZE_RANGE) {
			if(element instanceof ReviewSetting) {
				ReviewSetting setting = (ReviewSetting)element;
				String fileName = (setting.isOptimizeRange()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
				return ApplicationImageFactory.getInstance().getImage(fileName, IApplicationImage.SIZE_16x16);
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof ReviewSetting) {
			ReviewSetting setting = (ReviewSetting)element;
			switch(columnIndex) {
				case 0:
					text = setting.getName();
					break;
				case 1:
					text = decimalFormat.format(setting.getStartRetentionTimeMinutes());
					break;
				case 2:
					text = decimalFormat.format(setting.getStopRetentionTimeMinutes());
					break;
				case 3:
					text = setting.getCasNumber();
					break;
				case 4:
					text = setting.getTraces();
					break;
				case 5:
					PeakType detectorType = setting.getDetectorType();
					if(detectorType != null) {
						return detectorType.name();
					} else {
						return "";
					}
				case 6:
					text = "";
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_IDENTIFY_PEAKS, IApplicationImage.SIZE_16x16);
	}
}
