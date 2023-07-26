/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.compiler;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.comparator.IonAbundanceComparator;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.xxd.classifier.supplier.ratios.core.ITemplateExport;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioExportSettings;

public class TraceRatioCompiler implements ITemplateExport {

	private IonAbundanceComparator ionComparator = new IonAbundanceComparator(SortOrder.DESC);
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");

	public boolean compilePeaks(File file, List<? extends IPeak> peaks, TraceRatioExportSettings traceRatioExportSettings) {

		TraceRatios settings = new TraceRatios();
		//
		int numberTraces = traceRatioExportSettings.getNumberTraces();
		float deviationWarn = traceRatioExportSettings.getAllowedDeviationOk();
		float deviationError = traceRatioExportSettings.getAllowedDeviationWarn();
		//
		for(IPeak peak : peaks) {
			if(peak instanceof IPeakMSD peakMSD) {
				String name = getName(peak);
				if(!name.isEmpty()) {
					IScanMSD scanMSD = peakMSD.getPeakModel().getPeakMassSpectrum();
					settings.addAll(extract(scanMSD, name, numberTraces, deviationWarn, deviationError));
				}
			}
		}
		//
		return settings.exportItems(file);
	}

	public boolean compileScans(File file, List<? extends IScanMSD> scansMSD, TraceRatioExportSettings traceRatioExportSettings) {

		TraceRatios settings = new TraceRatios();
		//
		int numberTraces = traceRatioExportSettings.getNumberTraces();
		float deviationWarn = traceRatioExportSettings.getAllowedDeviationOk();
		float deviationError = traceRatioExportSettings.getAllowedDeviationWarn();
		//
		for(IScanMSD scanMSD : scansMSD) {
			String name = getName(scanMSD);
			if(!name.isEmpty()) {
				settings.addAll(extract(scanMSD, name, numberTraces, deviationWarn, deviationError));
			}
		}
		//
		return settings.exportItems(file);
	}

	private List<TraceRatio> extract(IScanMSD scanMSD, String name, int numberTraces, float deviationWarn, float deviationError) {

		List<TraceRatio> traceRatios = new ArrayList<>();
		//
		List<IIon> ions = new ArrayList<>(scanMSD.getIons());
		Collections.sort(ions, ionComparator);
		if(ions.size() >= 2) {
			IIon master = ions.get(0);
			exitloop:
			for(int i = 1; i <= numberTraces; i++) {
				if(i < ions.size()) {
					if(master.getAbundance() > 0) {
						IIon reference = ions.get(i);
						String testCase = AbstractIon.getIon(master.getIon()) + TraceRatio.TEST_CASE_SEPARATOR + AbstractIon.getIon(reference.getIon());
						double ratio = Double.valueOf(decimalFormat.format(100.0d / master.getAbundance() * reference.getAbundance()));
						//
						TraceRatio traceRatio = new TraceRatio();
						traceRatio.setName(name);
						traceRatio.setTestCase(testCase);
						traceRatio.setExpectedRatio(ratio);
						traceRatio.setDeviationWarn(deviationWarn);
						traceRatio.setDeviationError(deviationError);
						traceRatios.add(traceRatio);
					}
				} else {
					break exitloop;
				}
			}
		}
		//
		return traceRatios;
	}
}