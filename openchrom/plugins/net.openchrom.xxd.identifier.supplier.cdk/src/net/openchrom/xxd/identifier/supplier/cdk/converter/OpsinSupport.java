/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class OpsinSupport {

	public static void calculateSmilesIfAbsent(ILibraryInformation libraryInformation, NameToStructure nameStructure, NameToStructureConfig nameStructureConfig) {

		if(libraryInformation.getSmiles().equals("")) {
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

	private OpsinSupport() {

	}
}
