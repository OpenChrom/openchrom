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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatios;

public class TraceRatioContentProvider implements IStructuredContentProvider {

	private String displayOption = "";

	public TraceRatioContentProvider() {
		this(TraceRatioResultTitles.OPTION_RESULTS);
	}

	public TraceRatioContentProvider(String displayOption) {
		this.displayOption = (displayOption.equals(TraceRatioResultTitles.OPTION_SETTINGS)) ? TraceRatioResultTitles.OPTION_SETTINGS : TraceRatioResultTitles.OPTION_RESULTS;
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
			if(TraceRatioResultTitles.OPTION_RESULTS.equals(displayOption)) {
				List<TraceRatio> ratios = new ArrayList<>();
				for(TraceRatio ratio : traceRatios) {
					if(ratio.getPeak() != null) {
						ratios.add(ratio);
					}
				}
				return ratios.toArray();
			} else if(TraceRatioResultTitles.OPTION_SETTINGS.equals(displayOption)) {
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
