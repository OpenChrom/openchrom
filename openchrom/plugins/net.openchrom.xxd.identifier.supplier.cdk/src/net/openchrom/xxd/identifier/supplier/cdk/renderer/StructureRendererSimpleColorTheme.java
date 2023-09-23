/*******************************************************************************
 * Copyright (c) 2013, 2023 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - refactoring bundle name
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

/**
 * A simple color theme for displaying molecules as an alternative to the CDK representation.
 * => via adding IAtom instances to the symbolicAtoms list it is possible to choose, whether
 * atoms should be displayed via their respective Symbol or if they should be hide (e.g. shown
 * as shape etc.)
 * 
 * @author administrator_marwin
 * 
 */
public class StructureRendererSimpleColorTheme implements IStructureRendererColorScheme {

	List<AtomToColorMapping> atomToColorMap;
	List<IAtom> symbolicAtoms;

	public StructureRendererSimpleColorTheme() {

		atomToColorMap = new ArrayList<>();
		symbolicAtoms = new ArrayList<>();
		AtomToColorMapping cToBlue = new AtomToColorMapping(
			SilentChemObjectBuilder.getInstance().newInstance(IAtom.class,"C"),
			Color.BLUE
		);
		atomToColorMap.add(cToBlue);
		symbolicAtoms.add(SilentChemObjectBuilder.getInstance().newInstance(IAtom.class, "Cl"));
	}

	@Override
	public List<AtomToColorMapping> getAtomToColorMap() {

		return atomToColorMap;
	}

	@Override
	public Color getDefaultColor() {

		return Color.BLACK;
	}

	@Override
	public Color getSingleBondColor() {

		return Color.BLACK;
	}

	@Override
	public Color getDoubleBondColor() {

		return Color.RED;
	}

	@Override
	public Color getTripleBondColor() {

		return Color.ORANGE;
	}

	@Override
	public void setAtomToColorMap(List<AtomToColorMapping> atomToColorMap) {

	}

	@Override
	public void setDefaultColor(Color defaultColor) {

	}

	@Override
	public void setSingleBondColor(Color singleBondColor) {

	}

	@Override
	public void setDoubleBondColor(Color doubleBondColor) {

	}

	@Override
	public void setTripleBondColor(Color tripleBondColor) {

	}

	@Override
	public boolean isShowingSymbolsForAtomType(IAtom atom) {

		for(IAtom toCheck : symbolicAtoms) {
			if(toCheck.getSymbol().equalsIgnoreCase(atom.getSymbol())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setDefaultBondStroke(Stroke stroke) {

	}

	@Override
	public void setDoubleBondStroke(Stroke stroke) {

	}

	@Override
	public void setTripleBondStroke(Stroke stroke) {

	}

	@Override
	public Stroke getDefaultBondStroke() {

		return new BasicStroke(2.0f);
	}

	@Override
	public Stroke getDoubleBondStroke() {

		return null;
	}

	@Override
	public Stroke getTripleBondStroke() {

		return null;
	}
}