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
package net.openchrom.xxd.process.supplier.templates.io;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.IdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class IdentifierExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Identifier Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		IdentifierSettings identifierSettings = new IdentifierSettings();
		//
		int deltaLeft = PreferenceSupplier.getExportDeltaLeftMillisecondsIdentifier();
		int deltaRight = PreferenceSupplier.getExportDeltaRightMillisecondsIdentifier();
		int numberTraces = PreferenceSupplier.getExportNumberTracesIdentifier();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(peak);
			if(libraryInformation != null) {
				IdentifierSetting setting = new IdentifierSetting();
				setting.setPositionDirective(PositionDirective.RETENTION_TIME_MIN);
				setting.setPositionStart((peakModel.getStartRetentionTime() - deltaLeft) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				setting.setPositionStop((peakModel.getStopRetentionTime() + deltaRight) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				setting.setName(libraryInformation.getName());
				setting.setCasNumber(libraryInformation.getCasNumber());
				setting.setComments(libraryInformation.getComments());
				setting.setContributor(libraryInformation.getContributor());
				setting.setReference(libraryInformation.getReferenceIdentifier());
				setting.setTraces(extractTraces(peak, numberTraces));
				setting.setReferenceIdentifier("");
				identifierSettings.add(setting);
			}
		}
		//
		identifierSettings.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The identifier template has been exported successfully.");
		return processingInfo;
	}
}