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
package net.openchrom.xxd.process.supplier.templates.io;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.quantitation.IInternalStandard;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;
import net.openchrom.xxd.process.supplier.templates.model.AssignerStandards;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class StandardsExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Standards Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		AssignerStandards assignerStandards = new AssignerStandards();
		//
		int deltaLeft = (int)(PreferenceSupplier.getExportDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int deltaRight = (int)(PreferenceSupplier.getExportDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			List<IInternalStandard> internalStandards = peak.getInternalStandards();
			if(internalStandards.size() > 0) {
				for(IInternalStandard internalStandard : internalStandards) {
					AssignerStandard assignerStandard = new AssignerStandard();
					assignerStandard.setStartRetentionTime(peakModel.getStartRetentionTime() - deltaLeft);
					assignerStandard.setStopRetentionTime(peakModel.getStopRetentionTime() + deltaRight);
					assignerStandard.setName(internalStandard.getName());
					assignerStandard.setConcentration(internalStandard.getConcentration());
					assignerStandard.setConcentrationUnit(internalStandard.getConcentrationUnit());
					assignerStandard.setResponseFactor(internalStandard.getResponseFactor());
					assignerStandards.add(assignerStandard);
				}
			}
		}
		//
		assignerStandards.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The standards template has been exported successfully.");
		return processingInfo;
	}
}
