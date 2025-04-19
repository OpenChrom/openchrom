/*******************************************************************************
 * Copyright (c) 2013, 2025 Marwin Wollschläger.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - adjustments
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.openscience.cdk.interfaces.IAtomContainer;

public interface IStructureConverter {

	/**
	 * Try to create a molecule by the given input.
	 * This method may return null.
	 * 
	 * @param input
	 * @return {@link IMolecule}
	 */
	IAtomContainer generate(String input);
}
