/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;

public class StandardsAssignerLabelProvider extends AbstractTemplateLabelProvider {

	public static final String[] TITLES = { //
			NAME_ISTD, //
			POSITION_START, //
			POSITION_STOP, //
			POSITION_DIRECTIVE, //
			CONCENTRATION, //
			CONCENTRATION_UNIT, //
			COMPENSATION_FACTOR, //
			TRACES //
	};
	//
	public static final int[] BOUNDS = { //
			200, //
			100, //
			100, //
			100, //
			50, //
			50, //
			50, //
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
		if(element instanceof AssignerStandard setting) {
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
					text = decimalFormat.format(setting.getConcentration());
					break;
				case 5:
					text = setting.getConcentrationUnit();
					break;
				case 6:
					text = decimalFormat.format(setting.getCompensationFactor());
					break;
				case 7:
					text = setting.getTracesIdentification();
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_QUANTITATION_DEFAULT, IApplicationImageProvider.SIZE_16x16);
	}
}