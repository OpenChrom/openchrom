/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
*******************************************************************************/
package net.openchrom.supplier.cdk.core.customizedRenderer;

import java.awt.Color;

import org.openscience.cdk.interfaces.IAtom;

/**
 *  Utility class for defining AtomColors 
 * @author administrator_marwin
 *
 */

public class AtomToColorMapping {
	Color color; IAtom atom;
	// Only allow to instantiate an AtomToColorMapping 
	// with color and atom attributes!
	public AtomToColorMapping(IAtom atom, Color color)
	{
		this.color = color;
		this.atom = atom;
	}
	
	
	public void setAtom(IAtom atom){ this.atom = atom; }
	public void setColor(Color color){ this.color = color; }
}
