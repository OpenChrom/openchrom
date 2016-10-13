/*******************************************************************************
 * Copyright (c) 2016 Lablicate GmbH.
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

import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.IMassSpectrumTarget;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class SmilesCalculator {

	private NameToStructure nameStructure;
	private NameToStructureConfig nameStructureConfig;
	private TargetExtendedComparator targetExtendedComparator;

	public SmilesCalculator() {
		nameStructure = NameToStructure.getInstance();
		nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true);
		targetExtendedComparator = new TargetExtendedComparator(SortOrder.DESC);
	}

	public void calculate(IMassSpectra massSpectra, IProgressMonitor monitor) {

		int i = 1;
		exitloop:
		for(IScanMSD scanMSD : massSpectra.getList()) {
			/*
			 * Get library information.
			 */
			monitor.subTask("Calculate SMILES Scan#" + i++);
			if(monitor.isCanceled()) {
				break exitloop;
			}
			//
			ILibraryInformation libraryInformation = null;
			if(scanMSD instanceof IRegularLibraryMassSpectrum) {
				IRegularLibraryMassSpectrum libraryMassSpectrum = (IRegularLibraryMassSpectrum)scanMSD;
				libraryInformation = libraryMassSpectrum.getLibraryInformation();
			} else {
				libraryInformation = getLibraryInformation(scanMSD.getTargets());
			}
			//
			if(libraryInformation != null) {
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
					}
				}
			}
		}
	}

	private ILibraryInformation getLibraryInformation(List<IMassSpectrumTarget> targets) {

		ILibraryInformation libraryInformation = null;
		Collections.sort(targets, targetExtendedComparator);
		if(targets.size() >= 1) {
			libraryInformation = targets.get(0).getLibraryInformation();
		}
		return libraryInformation;
	}
}
