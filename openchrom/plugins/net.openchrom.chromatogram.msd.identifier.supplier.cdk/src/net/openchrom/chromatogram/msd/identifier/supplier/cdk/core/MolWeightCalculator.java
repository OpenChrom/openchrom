/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - backfall: calculate from molecular formula
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.core;

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
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.formula.MolecularFormula;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.OpsinSupport;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings.IdentifierSettings;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;

public class MolWeightCalculator extends AbstractChromatogramIdentifier {

	private SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());

	@Override
	public IProcessingInfo<?> identify(IChromatogramSelectionMSD chromatogramSelection, IChromatogramIdentifierSettings chromatogramIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<?> processingInfo = validate(chromatogramSelection, chromatogramIdentifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramIdentifierSettings instanceof IdentifierSettings) {
				/*
				 * Settings
				 */
				IdentifierSettings identifierSettings = (IdentifierSettings)chromatogramIdentifierSettings;
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
					if(supplierScan instanceof IScanMSD) {
						IScanMSD scanMSD = (IScanMSD)supplierScan;
						/*
						 * Scan
						 */
						if(scanMSD.getTargets().size() > 0) {
							calculateMolWeight(scanMSD, nameStructure, nameStructureConfig);
						}
						/*
						 * Optimized Scan.
						 */
						IScanMSD optimizedMassSpectrum = scanMSD.getOptimizedMassSpectrum();
						if(optimizedMassSpectrum != null) {
							if(optimizedMassSpectrum.getTargets().size() > 0) {
								calculateMolWeight(optimizedMassSpectrum, nameStructure, nameStructureConfig);
							}
						}
					}
				}
				/*
				 * Peaks
				 */
				List<IPeakMSD> peaks = new ArrayList<IPeakMSD>();
				for(IPeakMSD peakMSD : chromatogramSelection.getChromatogram().getPeaks(chromatogramSelection)) {
					peaks.add(peakMSD);
				}
				calculateMolWeight(peaks, nameStructure, nameStructureConfig);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<?> identify(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		IdentifierSettings identifierSettings = PreferenceSupplier.getIdentifierSettings();
		return identify(chromatogramSelection, identifierSettings, monitor);
	}

	private void calculateMolWeight(IScanMSD scanMSD, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		calculateMolWeight(scanMSD.getTargets(), nameStructure, nameStructureConfig);
	}

	private void calculateMolWeight(List<IPeakMSD> peaks, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		for(IPeakMSD peak : peaks) {
			calculateMolWeight(peak.getTargets(), nameStructure, nameStructureConfig);
		}
	}

	private void calculateMolWeight(Set<IIdentificationTarget> targets, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		for(IIdentificationTarget target : targets) {
			/*
			 * Check if the peak is a peak identification entry.
			 */
			if(target instanceof IIdentificationTarget) {
				ILibraryInformation libraryInformation = ((IIdentificationTarget)target).getLibraryInformation();
				OpsinSupport.calculateSmilesIfAbsent(libraryInformation, nameStructure, nameStructureConfig);
				String smiles = libraryInformation.getSmiles();
				if(!smiles.isEmpty()) {
					IAtomContainer molecule = generate(libraryInformation.getSmiles());
					double molWeight = AtomContainerManipulator.getMass(molecule);
					libraryInformation.setMolWeight(molWeight);
				} else {
					String formula = libraryInformation.getFormula();
					if(!formula.isEmpty()) {
						IMolecularFormula molecularFormula = new MolecularFormula();
						molecularFormula = MolecularFormulaManipulator.getMolecularFormula(formula, molecularFormula);
						IAtomContainer molecule = MolecularFormulaManipulator.getAtomContainer(molecularFormula);
						double molWeight = AtomContainerManipulator.getMass(molecule);
						libraryInformation.setMolWeight(molWeight);
					}
				}
			}
		}
	}

	private IAtomContainer generate(String smiles) {

		IAtomContainer molecule = null;
		if(smiles != null) {
			try {
				smilesParser.setStrict(PreferenceSupplier.isSmilesStrict());
				molecule = smilesParser.parseSmiles(smiles);
			} catch(InvalidSmilesException e) {
				//
			}
		}
		return molecule;
	}
}
