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

import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.msd.converter.supplier.cms.model.IonMeasurement;

public class IonMeasurementListLabelProvider extends AbstractChemClipseLabelProvider {

	private DecimalFormat decimalFormatMZ = ValueFormat.getDecimalFormatEnglish("0.0000");
	private DecimalFormat decimalFormatSignal = ValueFormat.getDecimalFormatEnglish("0.0000E00");

	public IonMeasurementListLabelProvider() {
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

		String text = "";
		if(element instanceof IonMeasurement) {
			IonMeasurement ionMeasurement = (IonMeasurement)element;
			// String.format("ETime,s\tPtotal,%s",ppUnits)
			switch(columnIndex) {
				case 0:
					text = decimalFormatMZ.format(ionMeasurement.getMZ());
					// text = String.format("%.4f", ionMeasurement.getMZ());
					break;
				case 1:
					text = decimalFormatSignal.format(ionMeasurement.getSignal());
					// text = String.format("% .4g", ionMeasurement.getSignal());
					break;
			}
		} else if(element instanceof Ion) {
			Ion ion = (Ion)element;
			// String.format("ETime,s\tPtotal,%s",ppUnits)
			switch(columnIndex) {
				case 0:
					text = decimalFormatMZ.format(ion.getIon());
					// text = String.format("%.4f", ion.getIon());
					break;
				case 1:
					text = decimalFormatSignal.format(ion.getAbundance());
					// text = String.format("% .4g", ion.getAbundance());
					break;
			}
		}
		return text;
	}

	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ION, IApplicationImage.SIZE_16x16);
	}
}
