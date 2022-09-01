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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatios;
import net.openchrom.xxd.classifier.supplier.ratios.ui.swt.DisplayOption;

public class PeakRatioContentProvider implements IStructuredContentProvider {

	private DisplayOption displayOption = DisplayOption.RESULTS;

	public PeakRatioContentProvider() {

		this(DisplayOption.RESULTS);
	}

	public PeakRatioContentProvider(DisplayOption displayOption) {

		this.displayOption = displayOption;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Object[] getElements(Object inputElement) {

		IPeakRatios<IPeakRatio> peakRatios = null;
		//
		if(inputElement instanceof IMeasurementResult) {
			IMeasurementResult measurementResult = (IMeasurementResult)inputElement;
			Object object = measurementResult.getResult();
			if(object instanceof IPeakRatios) {
				peakRatios = (IPeakRatios)object;
			}
		} else if(inputElement instanceof IPeakRatios) {
			peakRatios = (IPeakRatios)inputElement;
		}
		//
		if(peakRatios != null) {
			if(DisplayOption.RESULTS.equals(displayOption)) {
				List<IPeakRatio> ratios = new ArrayList<>();
				for(IPeakRatio ratio : peakRatios) {
					if(ratio.getPeak() != null) {
						ratios.add(ratio);
					}
				}
				return ratios.toArray();
			} else if(DisplayOption.SETTINGS.equals(displayOption)) {
				return peakRatios.toArray();
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
