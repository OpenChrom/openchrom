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
 * Philip Wenig - refactoring bundle name
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.renderer;

import java.awt.Color;

import org.openscience.cdk.interfaces.IAtom;

/**
 * Utility class for defining AtomColors
 * 
 * @author administrator_marwin
 * 
 */
public class AtomToColorMapping {

	Color color;
	IAtom atom;

	// Only allow to instantiate an AtomToColorMapping
	// with color and atom attributes!
	public AtomToColorMapping(IAtom atom, Color color) {

		this.color = color;
		this.atom = atom;
	}

	public void setAtom(IAtom atom) {

		this.atom = atom;
	}

	public void setColor(Color color) {

		this.color = color;
	}
}
