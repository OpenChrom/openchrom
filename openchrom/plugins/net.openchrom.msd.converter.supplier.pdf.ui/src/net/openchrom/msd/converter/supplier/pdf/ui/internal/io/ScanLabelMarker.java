/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.internal.io;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.eavp.service.swtchart.core.BaseChart;
import org.eclipse.eavp.service.swtchart.marker.LabelMarker;
import org.eclipse.swt.SWT;

public class ScanLabelMarker extends LabelMarker {

	public ScanLabelMarker(BaseChart baseChart, int indexSeries, List<IScan> scans) {
		super(baseChart);
		List<String> labels = getScanLabels(scans);
		setLabels(labels, indexSeries, SWT.HORIZONTAL);
	}

	private List<String> getScanLabels(List<IScan> scans) {

		List<String> labels = new ArrayList<String>();
		//
		if(scans != null) {
			for(int i = 1; i <= scans.size(); i++) {
				labels.add("S" + i);
			}
		}
		//
		return labels;
	}
}
