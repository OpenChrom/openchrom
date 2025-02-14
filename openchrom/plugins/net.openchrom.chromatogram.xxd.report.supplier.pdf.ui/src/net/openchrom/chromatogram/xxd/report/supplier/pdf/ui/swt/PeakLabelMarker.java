/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.swt;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.swt.SWT;
import org.eclipse.swtchart.extensions.core.BaseChart;

public class PeakLabelMarker extends AbstractLabelMarker {

	public PeakLabelMarker(BaseChart baseChart, int indexSeries, List<? extends IPeak> peaks) {

		super(baseChart);
		List<String> labels = getPeakLabels(peaks);
		setLabels(labels, indexSeries, SWT.VERTICAL);
	}

	private List<String> getPeakLabels(List<? extends IPeak> peaks) {

		List<String> labels = new ArrayList<>();
		if(peaks != null) {
			for(int i = 0; i < peaks.size(); i++) {
				IPeak peak = peaks.get(i);
				labels.add(getBestIdentification(peak.getTargets(), peak.getPeakModel().getPeakMaximum().getRetentionIndex()));
			}
		}

		return labels;
	}

	private String getBestIdentification(Set<IIdentificationTarget> targets, float retentionIndex) {

		ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(targets, retentionIndex);
		if(libraryInformation != null) {
			return normalizeText(libraryInformation.getName());
		} else {
			return "";
		}
	}

	private String normalizeText(String text) {

		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\P{InBasic_Latin}", "?");
	}
}
