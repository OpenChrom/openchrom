/*******************************************************************************
 * Copyright (c) 2019, 2021 Lablicate GmbH.
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
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.model.AssignerReferences;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class ReferencerExport extends AbstractChromatogramExportConverter implements IChromatogramExportConverter, ITemplateExport {

	private static final String DESCRIPTION = "References Template Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		AssignerReferences assignerReferences = new AssignerReferences();
		//
		int deltaLeft = PreferenceSupplier.getExportDeltaLeftMillisecondsAssigner();
		int deltaRight = PreferenceSupplier.getExportDeltaRightMillisecondsAssigner();
		//
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			float retentionIndex = peak.getPeakModel().getPeakMaximum().getRetentionIndex();
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
			String identifier = getIdentifier(peak, identificationTargetComparator);
			List<String> references = peak.getQuantitationReferences();
			for(String reference : references) {
				if(!reference.equals(identifier)) {
					/*
					 * Create a reference
					 */
					AssignerReference assignerReference = new AssignerReference();
					assignerReference.setInternalStandard(reference);
					assignerReference.setIdentifier(identifier);
					/*
					 * If no identifier is available, use the retention time.
					 */
					if("".equals(identifier)) {
						assignerReference.setStartRetentionTime(peakModel.getStartRetentionTime() - deltaLeft);
						assignerReference.setStopRetentionTime(peakModel.getStopRetentionTime() + deltaRight);
					} else {
						assignerReference.setStartRetentionTime(0);
						assignerReference.setStopRetentionTime(0);
					}
					//
					assignerReferences.add(assignerReference);
				}
			}
		}
		//
		assignerReferences.exportItems(file);
		//
		processingInfo.setProcessingResult(file);
		processingInfo.addInfoMessage(DESCRIPTION, "The references template has been exported successfully.");
		return processingInfo;
	}

	private String getIdentifier(IPeak peak, IdentificationTargetComparator comparator) {

		ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(peak.getTargets(), comparator);
		return (libraryInformation != null) ? libraryInformation.getName() : "";
	}
}
