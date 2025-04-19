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
 * Matthias Mailänder - backfall: calculate from molecular formula
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.support;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;

public class SmilesSupport {

	private static SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());

	/**
	 * May return null.
	 * 
	 * @param smiles
	 * @param strict
	 * @return {@link IAtomContainer}
	 */
	public static IAtomContainer generate(String smiles, boolean strict) {

		IAtomContainer molecule = null;
		if(smiles != null) {
			try {
				smilesParser.setStrict(strict);
				molecule = smilesParser.parseSmiles(smiles);
			} catch(InvalidSmilesException e) {
			}
		}
		//
		return molecule;
	}
}