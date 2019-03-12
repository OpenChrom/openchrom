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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.DisplayOption;

public class TimeRatioContentProvider implements IStructuredContentProvider {

	private DisplayOption displayOption = DisplayOption.RESULTS;

	public TimeRatioContentProvider() {
		this(DisplayOption.RESULTS);
	}

	public TimeRatioContentProvider(DisplayOption displayOption) {
		this.displayOption = displayOption;
	}

	@Override
	public Object[] getElements(Object inputElement) {

		TimeRatios timeRatios = null;
		//
		if(inputElement instanceof IMeasurementResult) {
			IMeasurementResult measurementResult = (IMeasurementResult)inputElement;
			Object object = measurementResult.getResult();
			if(object instanceof TimeRatios) {
				timeRatios = (TimeRatios)object;
			}
		} else if(inputElement instanceof TimeRatios) {
			timeRatios = (TimeRatios)inputElement;
		}
		//
		if(timeRatios != null) {
			if(DisplayOption.RESULTS.equals(displayOption)) {
				List<TimeRatio> ratios = new ArrayList<>();
				for(TimeRatio ratio : timeRatios) {
					if(ratio.getPeak() != null) {
						ratios.add(ratio);
					}
				}
				return ratios.toArray();
			} else if(DisplayOption.SETTINGS.equals(displayOption)) {
				return timeRatios.toArray();
			}
		}
		//
		return null;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
}
