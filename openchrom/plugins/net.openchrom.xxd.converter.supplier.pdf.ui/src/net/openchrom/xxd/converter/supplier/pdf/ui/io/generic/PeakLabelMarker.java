/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.pdf.ui.io.generic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.swt.SWT;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.marker.LabelMarker;

public class PeakLabelMarker extends LabelMarker {

	public PeakLabelMarker(BaseChart baseChart, int indexSeries, List<? extends IPeak> peaks) {

		super(baseChart);
		List<String> labels = getPeakLabels(peaks);
		setLabels(labels, indexSeries, SWT.HORIZONTAL);
	}

	private List<String> getPeakLabels(List<? extends IPeak> peaks) {

		List<String> labels = new ArrayList<>();
		//
		if(peaks != null) {
			for(int i = 1; i <= peaks.size(); i++) {
				labels.add("P" + i);
			}
		}
		//
		return labels;
	}
}
