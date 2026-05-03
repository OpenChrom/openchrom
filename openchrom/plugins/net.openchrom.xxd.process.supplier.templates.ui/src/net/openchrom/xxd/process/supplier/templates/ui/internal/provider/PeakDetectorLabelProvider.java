/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments, use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class PeakDetectorLabelProvider extends AbstractTemplateLabelProvider {

	public static final int INDEX_OPTIMIZE_RANGE = 5;
	public static final int INDEX_AUTO_ADJUST_SCAN_RANGE = 9;
	public static final int INDEX_AUTO_ADJUST_DETECTOR_RANGE = 10;

	public static final String[] TITLES = { //
			POSITION_START, //
			POSITION_STOP, //
			POSITION_DIRECTIVE, //
			PEAK_TYPE, //
			TRACES, //
			OPTIMIZE_RANGE, //
			POSITION_RELATIVE_PEAK_NAME, //
			NAME, //
			CLASSIFIER, //
			AUTO_ADJUST_SCAN_RANGE, //
			AUTO_ADJUST_DETECTOR_RANGE //
	};
	public static final int[] BOUNDS = { //
			100, //
			100, //
			100, //
			50, //
			100, //
			30, //
			150, //
			150, //
			150, //
			30, //
			30 //
	};

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else if(columnIndex == INDEX_OPTIMIZE_RANGE) {
			if(element instanceof DetectorSetting setting) {
				String fileName = (setting.isOptimizeRange()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
				return ApplicationImageFactory.getInstance().getImage(fileName, IApplicationImageProvider.SIZE_16x16);
			}
		} else if(columnIndex == INDEX_AUTO_ADJUST_SCAN_RANGE) {
			if(element instanceof DetectorSetting setting) {
				String fileName = (setting.isAutoAdjustScanRange()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
				return ApplicationImageFactory.getInstance().getImage(fileName, IApplicationImageProvider.SIZE_16x16);
			}
		} else if(columnIndex == INDEX_AUTO_ADJUST_DETECTOR_RANGE) {
			if(element instanceof DetectorSetting setting) {
				String fileName = (setting.isAutoAdjustDetectorRange()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
				return ApplicationImageFactory.getInstance().getImage(fileName, IApplicationImageProvider.SIZE_16x16);
			}
		}

		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof DetectorSetting setting) {
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
					break;
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
				case 8:
					text = setting.getClassifier();
					break;
				case 9:
					text = "";
					break;
				case 10:
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

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_DETECTOR, IApplicationImageProvider.SIZE_16x16);
	}
}