/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - color support
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractTraceRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.swt.DisplayOption;

public class TimeRatioLabelProvider extends AbstractTraceRatioLabelProvider {

	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();
	private DisplayOption displayOption = DisplayOption.RESULTS;

	public TimeRatioLabelProvider() {

		this(DisplayOption.RESULTS);
	}

	public TimeRatioLabelProvider(DisplayOption displayOption) {

		super(3);
		this.displayOption = displayOption;
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

		String text;
		switch(displayOption) {
			case RESULTS:
				text = getColumnTextResults(element, columnIndex);
				break;
			case SETTINGS:
				text = getColumnTextSettings(element, columnIndex);
				break;
			default:
				text = "";
				break;
		}
		return text;
	}

	private String getColumnTextSettings(Object element, int columnIndex) {

		String text = "";
		if(element instanceof TimeRatio timeRatio) {
			switch(columnIndex) {
				case 0:
					text = timeRatio.getName();
					break;
				case 1:
					text = decimalFormat.format(timeRatio.getExpectedRetentionTimeMinutes());
					break;
				case 2:
					text = decimalFormat.format(timeRatio.getDeviationWarn());
					break;
				case 3:
					text = decimalFormat.format(timeRatio.getDeviationError());
					break;
			}
		}
		return text;
	}

	private String getColumnTextResults(Object element, int columnIndex) {

		String text = "";
		if(element instanceof TimeRatio timeRatio) {
			switch(columnIndex) {
				case 0:
					IPeak peak = timeRatio.getPeak();
					if(peak != null) {
						text = decimalFormat.format(peak.getPeakModel().getRetentionTimeAtPeakMaximum() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
					} else {
						text = "--";
					}
					break;
				case 1:
					text = timeRatio.getName();
					break;
				case 2:
					text = decimalFormat.format(timeRatio.getExpectedRetentionTimeMinutes());
					break;
				case 3:
					text = decimalFormat.format(timeRatio.getDeviation());
					break;
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CLASSIFIER, IApplicationImageProvider.SIZE_16x16);
	}
}
