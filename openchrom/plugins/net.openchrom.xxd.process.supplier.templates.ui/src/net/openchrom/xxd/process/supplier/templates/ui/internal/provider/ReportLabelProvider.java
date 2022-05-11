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

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;

public class ReportLabelProvider extends AbstractTemplateLabelProvider {

	public static final String[] TITLES = { //
			NAME, //
			POSITION_START, //
			POSITION_STOP, //
			POSITION_DIRECTIVE, //
			CAS_NUMBER, //
			REPORT_STRATEGY //
	};
	public static final int[] BOUNDS = { //
			200, //
			100, //
			100, //
			100, //
			100, //
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
		if(element instanceof ReportSetting) {
			ReportSetting setting = (ReportSetting)element;
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
					text = setting.getReportStrategy().label();
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