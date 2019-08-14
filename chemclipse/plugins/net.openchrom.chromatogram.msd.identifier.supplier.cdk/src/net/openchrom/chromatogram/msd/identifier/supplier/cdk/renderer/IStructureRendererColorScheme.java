/*******************************************************************************
 * Copyright (c) 2013, 2018 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.renderer;

/**
 * Interface that makes use of AtomToColorMap but also defines a default color for rendering.
 * Also defines rules for the rendering of bonds ...
 * 
 * @author administrator_marwin
 * 
 */
import java.awt.Color;
import java.awt.Stroke;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;

public interface IStructureRendererColorScheme {

	List<AtomToColorMapping> getAtomToColorMap();

	Color getDefaultColor();

	Color getSingleBondColor();

	Color getDoubleBondColor();

	Color getTripleBondColor();

	public void setAtomToColorMap(List<AtomToColorMapping> atomToColorMap);

	public void setDefaultColor(Color defaultColor);

	public void setSingleBondColor(Color singleBondColor);

	public void setDoubleBondColor(Color DoubleBondColor);

	public void setTripleBondColor(Color tripleBondColor);

	public boolean isShowingSymbolsForAtomType(IAtom atom);

	public void setDefaultBondStroke(Stroke stroke);

	public void setDoubleBondStroke(Stroke stroke);// ?

	public void setTripleBondStroke(Stroke stroke);// ?

	public Stroke getDefaultBondStroke();

	public Stroke getDoubleBondStroke();// ?

	public Stroke getTripleBondStroke();// ?
}
