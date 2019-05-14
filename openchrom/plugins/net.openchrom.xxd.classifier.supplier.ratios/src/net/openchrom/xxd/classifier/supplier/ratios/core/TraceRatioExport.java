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
package net.openchrom.xxd.classifier.supplier.ratios.core;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.comparator.IonAbundanceComparator;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;

public class TraceRatioExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Trace Ratio Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		TargetExtendedComparator targetComparator = new TargetExtendedComparator(SortOrder.DESC);
		IonAbundanceComparator ionComparator = new IonAbundanceComparator(SortOrder.DESC);
		//
		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		TraceRatios settings = new TraceRatios();
		//
		float deviationWarn = PreferenceSupplier.getAllowedDeviation();
		float deviationError = PreferenceSupplier.getAllowedDeviationWarn();
		int numberTraces = PreferenceSupplier.getNumberTraces();
		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
		//
		for(IPeak peak : peaks) {
			if(peak instanceof IPeakMSD) {
				String name = getName(peak, targetComparator);
				if(!"".equals(name)) {
					IPeakMSD peakMSD = (IPeakMSD)peak;
					IScanMSD scanMSD = peakMSD.getPeakModel().getPeakMassSpectrum();
					List<IIon> ions = new ArrayList<>(scanMSD.getIons());
					Collections.sort(ions, ionComparator);
					if(ions.size() >= 2) {
						IIon master = ions.get(0);
						exitloop:
						for(int i = 1; i <= numberTraces; i++) {
							if(i < ions.size()) {
								if(master.getAbundance() > 0) {
									IIon reference = ions.get(i);
									String testCase = (int)master.getIon() + TraceRatio.TEST_CASE_SEPARATOR + (int)reference.getIon();
									double ratio = Double.valueOf(decimalFormat.format(100.0d / master.getAbundance() * reference.getAbundance()));
									//
									TraceRatio traceRatio = new TraceRatio();
									traceRatio.setName(name);
									traceRatio.setTestCase(testCase);
									traceRatio.setExpectedRatio(ratio);
									traceRatio.setDeviationWarn(deviationWarn);
									traceRatio.setDeviationError(deviationError);
									settings.add(traceRatio);
								}
							} else {
								break exitloop;
							}
						}
					}
				}
			}
		}
		//
		settings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The trace classifier settings have been exported successfully.");
		return processingInfo;
	}
}
