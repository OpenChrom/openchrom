/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
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
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.IdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class IdentifierExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "Identifier Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		TargetExtendedComparator comparator = new TargetExtendedComparator(SortOrder.DESC);
		//
		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		IdentifierSettings identifierSettings = new IdentifierSettings();
		//
		int deltaLeft = (int)(PreferenceSupplier.getExportDeltaLeftMinutesIdentifier() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int deltaRight = (int)(PreferenceSupplier.getExportDeltaRightMinutesIdentifier() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int numberTraces = PreferenceSupplier.getExportNumberTracesIdentifier();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(peak.getTargets(), comparator);
			if(libraryInformation != null) {
				IdentifierSetting identifierSetting = new IdentifierSetting();
				identifierSetting.setStartRetentionTime(peakModel.getStartRetentionTime() - deltaLeft);
				identifierSetting.setStopRetentionTime(peakModel.getStopRetentionTime() + deltaRight);
				identifierSetting.setName(libraryInformation.getName());
				identifierSetting.setCasNumber(libraryInformation.getCasNumber());
				identifierSetting.setComments(libraryInformation.getComments());
				identifierSetting.setContributor(libraryInformation.getContributor());
				identifierSetting.setReference(libraryInformation.getReferenceIdentifier());
				identifierSetting.setTraces(extractTraces(peak, numberTraces));
				identifierSetting.setReferenceIdentifier("");
				identifierSettings.add(identifierSetting);
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
