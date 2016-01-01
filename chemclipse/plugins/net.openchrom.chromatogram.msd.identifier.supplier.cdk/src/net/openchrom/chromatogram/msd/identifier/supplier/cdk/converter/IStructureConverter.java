/*******************************************************************************
 * Copyright (c) 2013, 2016 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter;

import org.openscience.cdk.interfaces.IMolecule;

public interface IStructureConverter {

	/**
	 * Try to create a molecule by the given input.
	 * This method may return null.
	 * 
	 * @param input
	 * @return {@link IMolecule}
	 */
	IMolecule generate(String input);
}
