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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.DisplayOption;

public class TraceRatioContentProvider implements IStructuredContentProvider {

	private DisplayOption displayOption = DisplayOption.RESULTS;

	public TraceRatioContentProvider() {
		this(DisplayOption.RESULTS);
	}

	public TraceRatioContentProvider(DisplayOption displayOption) {
		this.displayOption = displayOption;
	}

	@Override
	public Object[] getElements(Object inputElement) {

		TraceRatios traceRatios = null;
		//
		if(inputElement instanceof IMeasurementResult) {
			IMeasurementResult measurementResult = (IMeasurementResult)inputElement;
			Object object = measurementResult.getResult();
			if(object instanceof TraceRatios) {
				traceRatios = (TraceRatios)object;
			}
		} else if(inputElement instanceof TraceRatios) {
			traceRatios = (TraceRatios)inputElement;
		}
		//
		if(traceRatios != null) {
			if(DisplayOption.RESULTS.equals(displayOption)) {
				List<TraceRatio> ratios = new ArrayList<>();
				for(TraceRatio ratio : traceRatios) {
					if(ratio.getPeak() != null) {
						ratios.add(ratio);
					}
				}
				return ratios.toArray();
			} else if(DisplayOption.SETTINGS.equals(displayOption)) {
				return traceRatios.toArray();
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
