/*******************************************************************************
 * Copyright (c) 2019, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.io;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.IdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class IdentifierExport extends AbstractTemplateExport {

	private static final String DESCRIPTION = "Identifier Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		IdentifierSettings identifierSettings = new IdentifierSettings();
		PositionDirective positionDirective = PreferenceSupplier.getExportPositionDirectiveIdentifier();
		double deltaLeft = PreferenceSupplier.getExportDeltaLeftCoordinateIdentifier();
		double deltaRight = PreferenceSupplier.getExportDeltaRightCoordinateIdentifier();
		int numberTraces = PreferenceSupplier.getExportNumberTracesIdentifier();
		RetentionIndexMap retentionIndexMap = new RetentionIndexMap(chromatogram);

		for(IPeak peak : peaks) {
			ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(peak);
			if(libraryInformation != null) {
				IdentifierSetting setting = new IdentifierSetting();
				setPosition(peak, retentionIndexMap, setting, positionDirective, deltaLeft, deltaRight);
				setting.setName(libraryInformation.getName());
				setting.setCasNumber(libraryInformation.getCasNumber());
				setting.setComments(libraryInformation.getComments());
				setting.setContributor(libraryInformation.getContributor());
				setting.setReferenceIdentifier(libraryInformation.getReferenceIdentifier());
				setting.setTraces(extractTraces(peak, numberTraces));
				setting.setPositionRelativePeakName("");
				identifierSettings.add(setting);
			}
		}

		identifierSettings.exportItems(file);

		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The identifier template has been exported successfully.");
		return processingInfo;
	}
}
