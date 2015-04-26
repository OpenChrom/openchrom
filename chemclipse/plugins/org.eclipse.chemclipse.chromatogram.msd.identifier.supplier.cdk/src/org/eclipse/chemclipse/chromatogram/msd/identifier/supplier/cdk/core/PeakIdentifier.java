/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.targets.IPeakTarget;
import org.eclipse.chemclipse.chromatogram.msd.identifier.peak.AbstractPeakIdentifier;
import org.eclipse.chemclipse.chromatogram.msd.identifier.processing.IPeakIdentifierProcessingInfo;
import org.eclipse.chemclipse.chromatogram.msd.identifier.processing.PeakIdentifierProcessingInfo;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettings;
import org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.settings.ICdkPeakIdentifierSettings;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.core.MessageType;
import org.eclipse.chemclipse.processing.core.ProcessingMessage;

public class PeakIdentifier extends AbstractPeakIdentifier {

	private NameToStructure nameStructure;
	private NameToStructureConfig nameStructureConfig;

	public PeakIdentifier() {

		nameStructure = NameToStructure.getInstance();
		nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true); // TODO settings Preferences
	}

	@Override
	public IPeakIdentifierProcessingInfo identify(IPeakMSD peak, IPeakIdentifierSettings peakIdentifierSettings, IProgressMonitor monitor) {

		List<IPeakMSD> peaks = new ArrayList<IPeakMSD>();
		peaks.add(peak);
		return identify(peaks, peakIdentifierSettings, monitor);
	}

	@Override
	public IPeakIdentifierProcessingInfo identify(List<IPeakMSD> peaks, IPeakIdentifierSettings peakIdentifierSettings, IProgressMonitor monitor) {

		IPeakIdentifierProcessingInfo processingInfo = new PeakIdentifierProcessingInfo();
		//
		boolean deleteIdentificationsWithoutFormula;
		if(peakIdentifierSettings instanceof ICdkPeakIdentifierSettings) {
			deleteIdentificationsWithoutFormula = ((ICdkPeakIdentifierSettings)peakIdentifierSettings).isDeleteIdentificationsWithoutFormula();
		} else {
			deleteIdentificationsWithoutFormula = PreferenceSupplier.isDeleteIdentificationsWithoutFormula();
		}
		//
		calculateSmilesFormula(peaks, deleteIdentificationsWithoutFormula);
		processingInfo.addMessage(new ProcessingMessage(MessageType.INFO, "SMILES Identifier", "The peak(s) have been identified successfully."));
		return processingInfo;
	}

	@Override
	public IPeakIdentifierProcessingInfo identify(List<IPeakMSD> peaks, IProgressMonitor monitor) {

		ICdkPeakIdentifierSettings peakIdentifierSettings = PreferenceSupplier.getPeakIdentifierSettings();
		return identify(peaks, peakIdentifierSettings, monitor);
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
					if(libraryInformation.getFormula().equals("")) {
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
							libraryInformation.setFormula(result.getSmiles());
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

	@Override
	public IPeakIdentifierProcessingInfo identify(IPeakMSD peak, IProgressMonitor monitor) {

		IPeakIdentifierSettings peakIdentifierSettings = PreferenceSupplier.getPeakIdentifierSettings();
		return identify(peak, peakIdentifierSettings, monitor);
	}
}
