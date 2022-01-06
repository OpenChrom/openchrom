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
 * Christoph Läubrich - Color-support
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.qual;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.targets.TargetSupport;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.classifier.supplier.ratios.model.qual.PeakQuality;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatio;

public class QualRatioLabelProvider extends AbstractChemClipseLabelProvider implements ITableColorProvider {

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
		if(element instanceof QualRatio) {
			QualRatio ratio = (QualRatio)element;
			IPeak peak = ratio.getPeak();
			switch(columnIndex) {
				case 0:
					text = (peak != null) ? decimalFormat.format(peak.getPeakModel().getRetentionTimeAtPeakMaximum() / IChromatogram.MINUTE_CORRELATION_FACTOR) : "--";
					break;
				case 1:
					text = TargetSupport.getBestTargetLibraryField(peak);
					break;
				case 2:
					text = ratio.getLeadingTailing().label();
					break;
				case 3:
					text = ratio.getSignalToNoise().label();
					break;
				case 4:
					text = ratio.getSymmetry().label();
					break;
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CLASSIFIER, IApplicationImage.SIZE_16x16);
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {

		if(element instanceof QualRatio) {
			QualRatio qualRatio = (QualRatio)element;
			if(columnIndex == 2) {
				return getColorPeakQuality(qualRatio.getLeadingTailing(), false);
			} else if(columnIndex == 3) {
				return getColorPeakQuality(qualRatio.getSignalToNoise(), false);
			} else if(columnIndex == 4) {
				return getColorPeakQuality(qualRatio.getSymmetry(), false);
			}
		}
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {

		if(element instanceof QualRatio) {
			QualRatio qualRatio = (QualRatio)element;
			if(columnIndex == 2) {
				return getColorPeakQuality(qualRatio.getLeadingTailing(), true);
			} else if(columnIndex == 3) {
				return getColorPeakQuality(qualRatio.getSignalToNoise(), true);
			} else if(columnIndex == 4) {
				return getColorPeakQuality(qualRatio.getSymmetry(), true);
			}
		}
		return null;
	}

	private Color getColorPeakQuality(PeakQuality peakQuality, boolean background) {

		Color color;
		switch(peakQuality) {
			case VERY_GOOD:
				color = (background) ? Colors.GREEN : Colors.BLACK;
				break;
			case GOOD:
				color = (background) ? Colors.GREEN : Colors.BLACK;
				break;
			case ACCEPTABLE:
				color = (background) ? Colors.YELLOW : Colors.BLACK;
				break;
			case BAD:
				color = (background) ? Colors.RED : Colors.WHITE;
				break;
			case VERY_BAD:
				color = (background) ? Colors.RED : Colors.WHITE;
				break;
			default:
				color = null;
				break;
		}
		return color;
	}
}