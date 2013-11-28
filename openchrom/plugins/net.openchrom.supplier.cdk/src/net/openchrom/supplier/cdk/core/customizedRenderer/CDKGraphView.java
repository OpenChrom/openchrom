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

/**
 * Class that gives an alternative implementation for rendering molecular structures.
 * It would be quite a bit of work to get the rendering process to a quality similar
 * to the renderer of the CDK. Still, because the two dimensional coordinates are
 * easily calculated by the 2DStructureGenerator of the CDK, it would be interesting to
 * see whether a self-made rendering of the structures gives good, maybe even better
 * results as in the CDK ???
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;

import net.openchrom.supplier.cdk.core.CDKVanDerWaalsDescriptor;

public class CDKGraphView implements IStructureRenderer {

	Color BACKGROUND_COLOR = Color.WHITE;
	Font chemicalRenderingFont = new Font("Goudy Handtooled BT", Font.PLAIN, 15);
	// need to know width and height of the parent!
	int w = 500;
	int h = 500;
	// scaling makes the molecule system more appropriate
	int scale = 30;
	// van der waals radii need to be scaled to be visible
	int vanDerWaalsScale = 5;
	// molecule that is to be displayed!
	IMolecule molecule;
	// style to use for rendering molecules and bonds!
	IStructureRendererColorScheme colorScheme = new StructureRendererSimpleColorTheme();// default Theme

	public void setColorScheme(IStructureRendererColorScheme colorScheme) {

		this.colorScheme = colorScheme;
	}

	@Override
	public boolean checkForCoordinates(IMolecule moleculeToRender) {

		if(moleculeToRender.getAtom(0) != null)
			return true;
		return false;
	}

	@Override
	public void renderStructure(Graphics2D g2d, IMolecule moleculeToRender) {

		// Use RenderingHints to enhance the quality of the rendering process!
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// System.out.println("RenderStructure");
		if(!checkForCoordinates(moleculeToRender))
			return;
		molecule = moleculeToRender;
		for(IAtom atom : moleculeToRender.atoms()) {
			double correctedX = atom.getPoint2d().x * scale + w / 2;
			double correctedY = atom.getPoint2d().y * scale + h / 2;
			atom.setPoint2d(new Point2d(correctedX, correctedY));
		}
		for(IBond bond : moleculeToRender.bonds()) {
			bondLineDraw(g2d, bond);
		}
		for(IAtom atom : moleculeToRender.atoms()) {
			atomCircleDraw(g2d, atom);
		}
	}

	private void bondLineDraw(Graphics2D g2d, IBond bond) {

		double x1 = bond.getAtom(0).getPoint2d().x;
		double y1 = bond.getAtom(0).getPoint2d().y;
		double x2 = bond.getAtom(1).getPoint2d().x;
		double y2 = bond.getAtom(1).getPoint2d().y;
		Line2D.Double lineToDraw = new Line2D.Double(x1, y1, x2, y2);
		Stroke currentStroke = g2d.getStroke();
		g2d.setStroke(colorScheme.getDefaultBondStroke());
		g2d.draw(lineToDraw);
		g2d.setStroke(currentStroke);
	}

	private void atomCircleDraw(Graphics2D g2d, IAtom atom) {

		double x = atom.getPoint2d().x;
		double y = atom.getPoint2d().y;
		CDKVanDerWaalsDescriptor vanDerWaalsDesc = new CDKVanDerWaalsDescriptor();
		double vanDerWaalsRadius = vanDerWaalsDesc.describe(atom, molecule);
		int r = (int)(vanDerWaalsRadius * vanDerWaalsScale);
		Color atomColor = chooseDisplayColor(atom);
		g2d.setColor(atomColor);
		if(!colorScheme.isShowingSymbolsForAtomType(atom)) {
			g2d.fillOval((int)x - r / 2, (int)y - r / 2, r, r);
		} else {
			Rectangle2D.Double rect = new Rectangle2D.Double(x - 5, y - 5, 12, 12);
			Color curColor = g2d.getColor();
			g2d.setColor(BACKGROUND_COLOR);
			g2d.fill(rect);
			g2d.setColor(curColor);
			Font currentFont = g2d.getFont();
			g2d.setFont(chemicalRenderingFont);
			g2d.drawString(atom.getSymbol(), (float)(x - 4), (float)(y + 3));
			g2d.setFont(currentFont);
		}
	}

	private Color chooseDisplayColor(IAtom atom) {

		Color atomColor = colorScheme.getDefaultColor();
		List<AtomToColorMapping> atomToColorList = colorScheme.getAtomToColorMap();
		for(AtomToColorMapping atomToColorMap : atomToColorList) {
			if(atom.getSymbol().equalsIgnoreCase(atomToColorMap.atom.getSymbol())) {
				atomColor = atomToColorMap.color;
			}
		}
		return atomColor;
	}
}
