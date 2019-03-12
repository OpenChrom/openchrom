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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatios;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.DisplayOption;

public class QuantRatioContentProvider implements IStructuredContentProvider {

	private DisplayOption displayOption = DisplayOption.RESULTS;

	public QuantRatioContentProvider() {
		this(DisplayOption.RESULTS);
	}

	public QuantRatioContentProvider(DisplayOption displayOption) {
		this.displayOption = displayOption;
	}

	@Override
	public Object[] getElements(Object inputElement) {

		QuantRatios quantRatios = null;
		//
		if(inputElement instanceof IMeasurementResult) {
			IMeasurementResult measurementResult = (IMeasurementResult)inputElement;
			Object object = measurementResult.getResult();
			if(object instanceof QuantRatios) {
				quantRatios = (QuantRatios)object;
			}
		} else if(inputElement instanceof QuantRatios) {
			quantRatios = (QuantRatios)inputElement;
		}
		//
		if(quantRatios != null) {
			if(DisplayOption.RESULTS.equals(displayOption)) {
				List<QuantRatio> ratios = new ArrayList<>();
				for(QuantRatio ratio : quantRatios) {
					if(ratio.getPeak() != null) {
						ratios.add(ratio);
					}
				}
				return ratios.toArray();
			} else if(DisplayOption.SETTINGS.equals(displayOption)) {
				return quantRatios.toArray();
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
