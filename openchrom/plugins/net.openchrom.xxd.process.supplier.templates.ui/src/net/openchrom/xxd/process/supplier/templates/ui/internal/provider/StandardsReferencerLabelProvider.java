/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;

public class StandardsReferencerLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String NAME = "Target ISTD (Internal Standard)";
	public static final String START_RETENTION_TIME = "Source Start RT (Retention Time - Minutes)";
	public static final String STOP_RETENTION_TIME = "Source Stop RT (Retention Time - Minutes)";
	public static final String IDENTIFIER = "Source Identifier";
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");
	//
	public static final String[] TITLES = { //
			NAME, //
			START_RETENTION_TIME, //
			STOP_RETENTION_TIME, //
			IDENTIFIER //
	};
	public static final int[] BOUNDS = { //
			200, //
			100, //
			100, //
			200 //
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
		if(element instanceof AssignerReference) {
			AssignerReference setting = (AssignerReference)element;
			switch(columnIndex) {
				case 0:
					text = setting.getName();
					break;
				case 1:
					double startRT = setting.getStartRetentionTime();
					if(startRT == 0.0d) {
						text = "--";
					} else {
						text = decimalFormat.format(startRT);
					}
					break;
				case 2:
					double stopRT = setting.getStopRetentionTime();
					if(stopRT == 0.0d) {
						text = "--";
					} else {
						text = decimalFormat.format(stopRT);
					}
					break;
				case 3:
					text = setting.getIdentifier();
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
