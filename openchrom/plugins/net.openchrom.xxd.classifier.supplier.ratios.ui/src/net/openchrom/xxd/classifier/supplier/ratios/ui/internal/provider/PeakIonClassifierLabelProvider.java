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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;

public class PeakIonClassifierLabelProvider extends AbstractChemClipseLabelProvider {

	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();

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
		if(element instanceof TraceRatio) {
			TraceRatio traceRatio = (TraceRatio)element;
			switch(columnIndex) {
				case 0:
					IPeakMSD peakMSD = traceRatio.getPeakMSD();
					if(peakMSD != null) {
						text = decimalFormat.format(peakMSD.getPeakModel().getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
					} else {
						text = "--";
					}
					break;
				case 1:
					text = traceRatio.getName();
					break;
				case 2:
					text = traceRatio.getTest();
					break;
				case 3:
					text = decimalFormat.format(traceRatio.getExpected());
					break;
				case 4:
					text = decimalFormat.format(traceRatio.getActual());
					break;
			}
		}
		return text;
	}

	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CLASSIFIER, IApplicationImage.SIZE_16x16);
	}
}
