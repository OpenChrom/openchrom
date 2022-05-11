/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;

public class PeakReviewLabelProvider extends AbstractTemplateLabelProvider {

	public static final int INDEX_OPTIMIZE_RANGE = 7;
	//
	public static final String[] TITLES = { //
			NAME, //
			POSITION_START, //
			POSITION_STOP, //
			POSITION_DIRECTIVE, //
			CAS_NUMBER, //
			TRACES, //
			PEAK_TYPE, //
			OPTIMIZE_RANGE //
	};
	public static final int[] BOUNDS = { //
			200, //
			100, //
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
					text = getFormattedPosition(setting.getPositionStart());
					break;
				case 2:
					text = getFormattedPosition(setting.getPositionStop());
					break;
				case 3:
					text = setting.getPositionDirective().label();
					break;
				case 4:
					text = setting.getCasNumber();
					break;
				case 5:
					text = setting.getTraces();
					break;
				case 6:
					text = getFormattedPeakType(setting.getPeakType());
					break;
				case 7:
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