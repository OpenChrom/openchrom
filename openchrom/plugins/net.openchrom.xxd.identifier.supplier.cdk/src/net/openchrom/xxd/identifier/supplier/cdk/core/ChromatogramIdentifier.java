/*******************************************************************************
 * Copyright (c) 2016, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.msd.identifier.chromatogram.AbstractChromatogramIdentifier;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IChromatogramIdentifierSettings;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.identifier.supplier.cdk.converter.OpsinSupport;
import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import net.openchrom.xxd.identifier.supplier.cdk.settings.IdentifierSettings;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;

public class ChromatogramIdentifier extends AbstractChromatogramIdentifier {

	@Override
	public IProcessingInfo<?> identify(IChromatogramSelectionMSD chromatogramSelection, IChromatogramIdentifierSettings chromatogramIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<?> processingInfo = validate(chromatogramSelection, chromatogramIdentifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramIdentifierSettings instanceof IdentifierSettings identifierSettings) {
				/*
				 * Settings
				 */
				NameToStructure nameStructure = NameToStructure.getInstance();
				NameToStructureConfig nameStructureConfig = new NameToStructureConfig();
				nameStructureConfig.setAllowRadicals(identifierSettings.isAllowRadicals());
				nameStructureConfig.setDetailedFailureAnalysis(identifierSettings.isDetailedFailureAnalysis());
				nameStructureConfig.setInterpretAcidsWithoutTheWordAcid(identifierSettings.isInterpretAcidsWithoutTheWordAcid());
				nameStructureConfig.setOutputRadicalsAsWildCardAtoms(identifierSettings.isOutputRadicalsAsWildCardAtoms());
				nameStructureConfig.setWarnRatherThanFailOnUninterpretableStereochemistry(identifierSettings.isWarnRatherThanFailOnUninterpretableStereochemistry());
				/*
				 * Scans
				 */
				IChromatogramMSD chromatogram = chromatogramSelection.getChromatogram();
				int startScan = chromatogram.getScanNumber(chromatogramSelection.getStartRetentionTime());
				int stopScan = chromatogram.getScanNumber(chromatogramSelection.getStopRetentionTime());
				/*
				 * Scans
				 */
				for(int scan = startScan; scan <= stopScan; scan++) {
					IScan supplierScan = chromatogram.getScan(scan);
					if(supplierScan instanceof IScanMSD scanMSD) {
						/*
						 * Scan
						 */
						if(!scanMSD.getTargets().isEmpty()) {
							calculateSmilesFormula(scanMSD, nameStructure, nameStructureConfig);
						}
						/*
						 * Optimized Scan.
						 */
						IScanMSD optimizedMassSpectrum = scanMSD.getOptimizedMassSpectrum();
						if(optimizedMassSpectrum != null) {
							if(!optimizedMassSpectrum.getTargets().isEmpty()) {
								calculateSmilesFormula(optimizedMassSpectrum, nameStructure, nameStructureConfig);
							}
						}
					}
				}
				/*
				 * Peaks
				 */
				List<IPeakMSD> peaks = new ArrayList<>();
				for(IPeakMSD peakMSD : chromatogramSelection.getChromatogram().getPeaks(chromatogramSelection)) {
					peaks.add(peakMSD);
				}
				calculateSmilesFormula(peaks, nameStructure, nameStructureConfig);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<?> identify(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		IdentifierSettings identifierSettings = PreferenceSupplier.getIdentifierSettings();
		return identify(chromatogramSelection, identifierSettings, monitor);
	}

	private void calculateSmilesFormula(IScanMSD scanMSD, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		calculateSmilesFormula(scanMSD.getTargets(), nameStructure, nameStructureConfig);
	}

	private void calculateSmilesFormula(List<IPeakMSD> peaks, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		for(IPeakMSD peak : peaks) {
			calculateSmilesFormula(peak.getTargets(), nameStructure, nameStructureConfig);
		}
	}

	private void calculateSmilesFormula(Set<IIdentificationTarget> targets, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		for(IIdentificationTarget target : targets) {
			ILibraryInformation libraryInformation = target.getLibraryInformation();
			OpsinSupport.calculateSmilesIfAbsent(libraryInformation, nameStructure, nameStructureConfig);
		}
	}
}
