/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
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
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;

public class QuantRatioExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Quant Ratio Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		QuantRatios settings = new QuantRatios();
		//
		float deviationWarn = PreferenceSupplier.getAllowedDeviationOk();
		float deviationError = PreferenceSupplier.getAllowedDeviationWarn();
		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
		//
		for(IPeak peak : peaks) {
			String name = getName(peak);
			if(!"".equals(name)) {
				List<IQuantitationEntry> quantitationEntries = peak.getQuantitationEntries();
				for(IQuantitationEntry quantitationEntry : quantitationEntries) {
					double expectedConcentration = Double.valueOf(decimalFormat.format(quantitationEntry.getConcentration()));
					QuantRatio quantRatio = new QuantRatio();
					quantRatio.setName(name);
					quantRatio.setQuantitationName(quantitationEntry.getName());
					quantRatio.setExpectedConcentration(expectedConcentration);
					quantRatio.setConcentrationUnit(quantitationEntry.getConcentrationUnit());
					quantRatio.setDeviationWarn(deviationWarn);
					quantRatio.setDeviationError(deviationError);
					settings.add(quantRatio);
				}
			}
		}
		//
		settings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The ratio classifier settings have been exported successfully.");
		return processingInfo;
	}
}