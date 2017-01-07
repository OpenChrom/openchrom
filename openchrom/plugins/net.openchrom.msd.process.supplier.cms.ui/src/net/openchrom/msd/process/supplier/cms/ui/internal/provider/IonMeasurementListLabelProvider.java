/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.msd.converter.supplier.cms.model.IonMeasurement;

public class IonMeasurementListLabelProvider extends AbstractChemClipseLabelProvider {

	public IonMeasurementListLabelProvider() {
		super("0.0###");
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		DecimalFormat decimalFormat = getDecimalFormat();
		String text = "";
		if(element instanceof IonMeasurement) {
			IonMeasurement ionMeasurement = (IonMeasurement)element;
			switch(columnIndex) {
				case 0:
					text = decimalFormat.format(ionMeasurement.getMZ());
					break;
				case 1:
					text = decimalFormat.format(ionMeasurement.getSignal());
					break;
			}
		}
		return text;
	}

	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ION, IApplicationImage.SIZE_16x16);
	}
}
