/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;

public class PeakIntegratorLabelProvider extends AbstractTemplateLabelProvider {

	public static final String[] TITLES = { //
			POSITION_START, //
			POSITION_STOP, //
			POSITION_DIRECTIVE, //
			IDENTIFIER, //
			INTEGRATOR //
	};
	public static final int[] BOUNDS = { //
			100, //
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
		if(element instanceof IntegratorSetting) {
			IntegratorSetting setting = (IntegratorSetting)element;
			switch(columnIndex) {
				case 0:
					text = getFormattedPosition(setting.getPositionStart(), PLACEHOLDER);
					break;
				case 1:
					text = getFormattedPosition(setting.getPositionStop(), PLACEHOLDER);
					break;
				case 2:
					text = setting.getPositionDirective().label();
					break;
				case 3:
					text = getFormattedIdentifier(setting.getIdentifier());
					break;
				case 4:
					text = setting.getIntegrator();
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_INTEGRATOR, IApplicationImage.SIZE_16x16);
	}
}