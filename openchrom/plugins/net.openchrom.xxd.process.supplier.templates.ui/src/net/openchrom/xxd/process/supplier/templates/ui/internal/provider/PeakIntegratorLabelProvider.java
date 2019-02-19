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

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;

public class PeakIntegratorLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String START_RETENTION_TIME = "Start Retention Time";
	public static final String STOP_RETENTION_TIME = "Stop Retention Time";
	public static final String IDENTIFIER = "Identifier";
	public static final String INTEGRATOR = "Integrator";
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");
	//
	public static final String[] TITLES = { //
			START_RETENTION_TIME, //
			STOP_RETENTION_TIME, //
			IDENTIFIER, //
			INTEGRATOR //
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
		if(element instanceof IntegratorSetting) {
			IntegratorSetting setting = (IntegratorSetting)element;
			switch(columnIndex) {
				case 0:
					double startRT = setting.getStartRetentionTime();
					text = (startRT == 0.0d) ? "--" : decimalFormat.format(startRT);
					break;
				case 1:
					double stopRT = setting.getStopRetentionTime();
					text = (stopRT == 0.0d) ? "--" : decimalFormat.format(stopRT);
					break;
				case 2:
					String identifier = setting.getIdentifier();
					text = ("".equals(identifier)) ? "--" : identifier;
					break;
				case 3:
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
