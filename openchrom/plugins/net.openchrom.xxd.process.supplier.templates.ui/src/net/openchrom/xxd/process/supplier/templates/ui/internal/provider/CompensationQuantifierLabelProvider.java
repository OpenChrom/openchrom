/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

import java.text.DecimalFormat;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;

public class CompensationQuantifierLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String NAME = "Quantifier";
	public static final String INTERNAL_STANDARD = "Internal Standard";
	public static final String EXPECTED_CONCENTRATION = "Expected Concentration";
	public static final String CONCENTRATION_UNIT = "Concentration Unit";
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");
	//
	public static final String[] TITLES = { //
			NAME, //
			INTERNAL_STANDARD, //
			EXPECTED_CONCENTRATION, //
			CONCENTRATION_UNIT //
	};
	//
	public static final int[] BOUNDS = { //
			150, //
			150, //
			50, //
			50 //
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
		if(element instanceof CompensationSetting) {
			CompensationSetting setting = (CompensationSetting)element;
			switch(columnIndex) {
				case 0:
					text = setting.getName();
					break;
				case 1:
					text = setting.getInternalStandard();
					break;
				case 2:
					text = decimalFormat.format(setting.getExpectedConcentration());
					break;
				case 3:
					text = setting.getConcentrationUnit();
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_QUANTITATION_DEFAULT, IApplicationImage.SIZE_16x16);
	}
}
