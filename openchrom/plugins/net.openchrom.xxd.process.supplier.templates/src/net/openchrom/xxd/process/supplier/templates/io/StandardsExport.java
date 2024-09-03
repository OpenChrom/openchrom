/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
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
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.quantitation.IInternalStandard;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;
import net.openchrom.xxd.process.supplier.templates.model.AssignerStandards;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class StandardsExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Standards Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		AssignerStandards assignerStandards = new AssignerStandards();
		//
		int numberTraces = PreferenceSupplier.getExportNumberTracesAssigner();
		int deltaLeft = PreferenceSupplier.getExportDeltaLeftMillisecondsStandards();
		int deltaRight = PreferenceSupplier.getExportDeltaRightMillisecondsStandards();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			List<IInternalStandard> internalStandards = peak.getInternalStandards();
			if(!internalStandards.isEmpty()) {
				for(IInternalStandard internalStandard : internalStandards) {
					AssignerStandard setting = new AssignerStandard();
					setting.setPositionDirective(PositionDirective.RETENTION_TIME_MIN);
					setting.setPositionStart((peakModel.getStartRetentionTime() - deltaLeft) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
					setting.setPositionStop((peakModel.getStopRetentionTime() + deltaRight) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
					setting.setName(internalStandard.getName());
					setting.setConcentration(internalStandard.getConcentration());
					setting.setConcentrationUnit(internalStandard.getConcentrationUnit());
					setting.setCompensationFactor(internalStandard.getCompensationFactor());
					setting.setTracesIdentification(extractTraces(peak, numberTraces));
					assignerStandards.add(setting);
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
