/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments, use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class PeakDetectorLabelProvider extends AbstractTemplateLabelProvider {

	public static final int INDEX_OPTIMIZE_RANGE = 5;
	//
	public static final String[] TITLES = { //
			POSITION_START, //
			POSITION_STOP, //
			POSITION_DIRECTIVE, //
			PEAK_TYPE, //
			TRACES, //
			OPTIMIZE_RANGE, //
			REFERENCE_IDENTIFIER, //
			NAME //
	};
	public static final int[] BOUNDS = { //
			100, //
			100, //
			100, //
			50, //
			100, //
			30, //
			150, //
			150 //
	};

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else if(columnIndex == INDEX_OPTIMIZE_RANGE) {
			if(element instanceof DetectorSetting) {
				DetectorSetting setting = (DetectorSetting)element;
				String fileName = (setting.isOptimizeRange()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
				return ApplicationImageFactory.getInstance().getImage(fileName, IApplicationImage.SIZE_16x16);
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof DetectorSetting) {
			DetectorSetting setting = (DetectorSetting)element;
			switch(columnIndex) {
				case 0:
					text = getFormattedPosition(setting.getPositionStart());
					break;
				case 1:
					text = getFormattedPosition(setting.getPositionStop());
					break;
				case 2:
					text = setting.getPositionDirective().label();
					break;
				case 3:
					text = getFormattedPeakType(setting.getPeakType());
				case 4:
					text = setting.getTraces();
					break;
				case 5:
					text = "";
					break;
				case 6:
					text = setting.getReferenceIdentifier();
					break;
				case 7:
					text = setting.getName();
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_DETECTOR, IApplicationImage.SIZE_16x16);
	}
}