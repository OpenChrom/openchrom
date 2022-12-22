/*******************************************************************************
 * Copyright (c) 2013, 2022 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - additional API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A Class that should later be able to convert IMolecule instances to
 * the Chemfig format. It is currently not working.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKMoleculeToChemFigConverter implements IStructureConverter {

	@Override
	public IAtomContainer generate(String input) {

		// TODO: to be implemented!
		return null;
	}
}
