/*******************************************************************************
 * Copyright (c) 2016, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.identifier.chromatogram.AbstractChromatogramIdentifier;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IChromatogramIdentifierSettings;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.targets.IPeakTarget;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.IScanTargetMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings.IVendorIdentifierSettings;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class ChromatogramIdentifier extends AbstractChromatogramIdentifier {

	private NameToStructure nameStructure;
	private NameToStructureConfig nameStructureConfig;

	public ChromatogramIdentifier() {
		nameStructure = NameToStructure.getInstance();
		nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true); // TODO settings Preferences
	}

	@Override
	public IProcessingInfo identify(IChromatogramSelectionMSD chromatogramSelection, IChromatogramIdentifierSettings identifierSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		try {
			super.validateChromatogramSelection(chromatogramSelection);
			super.validateSettings(identifierSettings);
			//
			boolean deleteIdentificationsWithoutFormula = false;
			if(identifierSettings instanceof IVendorIdentifierSettings) {
				deleteIdentificationsWithoutFormula = ((IVendorIdentifierSettings)identifierSettings).isDeleteIdentificationsWithoutFormula();
			}
			/*
			 * Scans
			 */
			IChromatogramMSD chromatogram = chromatogramSelection.getChromatogramMSD();
			int startScan = chromatogram.getScanNumber(chromatogramSelection.getStartRetentionTime());
			int stopScan = chromatogram.getScanNumber(chromatogramSelection.getStopRetentionTime());
			/*
			 * Scans
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				IScan supplierScan = chromatogram.getScan(scan);
				if(supplierScan instanceof IScanMSD) {
					IScanMSD scanMSD = (IScanMSD)supplierScan;
					/*
					 * Scan
					 */
					if(scanMSD.getTargets().size() > 0) {
						calculateSmilesFormula(scanMSD, deleteIdentificationsWithoutFormula);
					}
					/*
					 * Optimized Scan.
					 */
					IScanMSD optimizedMassSpectrum = scanMSD.getOptimizedMassSpectrum();
					if(optimizedMassSpectrum != null) {
						if(optimizedMassSpectrum.getTargets().size() > 0) {
							calculateSmilesFormula(optimizedMassSpectrum, deleteIdentificationsWithoutFormula);
						}
					}
				}
			}
			/*
			 * Peaks
			 */
			List<IPeakMSD> peaks = new ArrayList<IPeakMSD>();
			for(IPeakMSD peakMSD : chromatogramSelection.getChromatogramMSD().getPeaks(chromatogramSelection)) {
				peaks.add(peakMSD);
			}
			calculateSmilesFormula(peaks, deleteIdentificationsWithoutFormula);
		} catch(Exception e) {
			processingInfo.addErrorMessage("SMILES", "Something gone wrong.");
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo identify(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		IVendorIdentifierSettings identifierSettings = PreferenceSupplier.getIdentifierSettings();
		return identify(chromatogramSelection, identifierSettings, monitor);
	}

	private void calculateSmilesFormula(IScanMSD scanMSD, boolean deleteIdentificationsWithoutFormula) {

		/*
		 * Get the targets for each peak.
		 */
		List<IScanTargetMSD> targets = scanMSD.getTargets();
		List<IScanTargetMSD> targetsToDelete = new ArrayList<IScanTargetMSD>();
		for(IScanTargetMSD target : targets) {
			/*
			 * Check if the peak is a peak identification entry.
			 */
			if(target instanceof IScanTargetMSD) {
				ILibraryInformation libraryInformation = ((IScanTargetMSD)target).getLibraryInformation();
				if(libraryInformation.getSmiles().equals("")) {
					/*
					 * Calculate SMILES
					 */
					String name = libraryInformation.getName();
					OpsinResult result = nameStructure.parseChemicalName(name, nameStructureConfig);
					String message = result.getMessage();
					if(message.equals("")) {
						/*
						 * Set the parsed name and smiles formula.
						 */
						libraryInformation.setName(result.getChemicalName());
						libraryInformation.setSmiles(result.getSmiles());
					} else {
						/*
						 * The name couldn't be parsed.
						 */
						libraryInformation.setComments(message);
						targetsToDelete.add(target);
					}
				}
			}
		}
		/*
		 * Delete each marked entry in the selected peak.
		 */
		if(deleteIdentificationsWithoutFormula) {
			scanMSD.removeTargets(targetsToDelete);
		}
	}

	private void calculateSmilesFormula(List<IPeakMSD> peaks, boolean deleteIdentificationsWithoutFormula) {

		/*
		 * Calculate formula for each peak.
		 */
		for(IPeakMSD peak : peaks) {
			/*
			 * Get the targets for each peak.
			 */
			List<IPeakTarget> targets = peak.getTargets();
			List<IPeakTarget> targetsToDelete = new ArrayList<IPeakTarget>();
			for(IPeakTarget target : targets) {
				/*
				 * Check if the peak is a peak identification entry.
				 */
				if(target instanceof IPeakTarget) {
					ILibraryInformation libraryInformation = ((IPeakTarget)target).getLibraryInformation();
					if(libraryInformation.getSmiles().equals("")) {
						/*
						 * Calculate SMILES
						 */
						String name = libraryInformation.getName();
						OpsinResult result = nameStructure.parseChemicalName(name, nameStructureConfig);
						String message = result.getMessage();
						if(message.equals("")) {
							/*
							 * Set the parsed name and smiles formula.
							 */
							libraryInformation.setName(result.getChemicalName());
							libraryInformation.setSmiles(result.getSmiles());
						} else {
							/*
							 * The name couldn't be parsed.
							 */
							libraryInformation.setComments(message);
							targetsToDelete.add(target);
						}
					}
				}
			}
			/*
			 * Delete each marked entry in the selected peak.
			 */
			if(deleteIdentificationsWithoutFormula) {
				peak.removeTargets(targetsToDelete);
			}
		}
	}
}
