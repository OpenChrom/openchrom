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

	public void setDoubleBondColor(Color doubleBondColor);

	public void setTripleBondColor(Color tripleBondColor);

	public boolean isShowingSymbolsForAtomType(IAtom atom);

	public void setDefaultBondStroke(Stroke stroke);

	public void setDoubleBondStroke(Stroke stroke);// ?

	public void setTripleBondStroke(Stroke stroke);// ?

	public Stroke getDefaultBondStroke();

	public Stroke getDoubleBondStroke();// ?

	public Stroke getTripleBondStroke();// ?
}
