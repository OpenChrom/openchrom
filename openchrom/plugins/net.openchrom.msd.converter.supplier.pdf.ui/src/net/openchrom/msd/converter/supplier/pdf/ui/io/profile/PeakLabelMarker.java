/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.io.profile;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.swt.SWT;
import org.eclipse.swtchart.extensions.core.BaseChart;

public class PeakLabelMarker extends AbstractLabelMarker {

	private int numberLargestPeaks = 0;

	public PeakLabelMarker(BaseChart baseChart, int indexSeries, List<? extends IPeak> peaks, int numberLargestPeaks) {

		super(baseChart);
		this.numberLargestPeaks = numberLargestPeaks;
		List<String> labels = getPeakLabels(peaks);
		setLabels(labels, indexSeries, SWT.VERTICAL);
	}

	private List<String> getPeakLabels(List<? extends IPeak> peaks) {

		List<String> labels = new ArrayList<String>();
		double areaLimitToPrintLabels = getAreaLimitToPrintLabels(peaks);
		//
		if(peaks != null) {
			for(int i = 0; i < peaks.size(); i++) {
				IPeak peak = peaks.get(i);
				if(peak.getIntegratedArea() > areaLimitToPrintLabels) {
					labels.add(getBestIdentification(peak.getTargets(), peak.getPeakModel().getPeakMaximum().getRetentionIndex()));
				} else {
					labels.add("");
				}
			}
		}
		//
		return labels;
	}

	private double getAreaLimitToPrintLabels(List<? extends IPeak> peaks) {

		double areaLimit = Double.MAX_VALUE;
		List<IPeak> peakList = new ArrayList<>(peaks);
		Collections.sort(peakList, (p1, p2) -> Double.compare(p2.getIntegratedArea(), p1.getIntegratedArea()));
		//
		int size = peakList.size();
		if(numberLargestPeaks > 0 && size > 0) {
			int index = size > numberLargestPeaks ? numberLargestPeaks : size;
			areaLimit = peakList.get(index).getIntegratedArea();
		}
		//
		return areaLimit;
	}

	private String getBestIdentification(Set<IIdentificationTarget> targets, float retentionIndex) {

		IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
		ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(targets, identificationTargetComparator);
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
