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
package net.openchrom.supplier.cdk.core;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.layout.StructureDiagramGenerator;

import net.openchrom.supplier.cdk.core.customizedRenderer.CDKGraphView;

/**
 * Utility Class for the Test class CDKGraphViewTest that implements a subclass for JPanel for displaying purposes.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKGraphViewTestPanel extends JPanel {

	CDKGraphView graphView = new CDKGraphView();
	IMolecule example = ExampleMolecule.getMolecule();
	StructureDiagramGenerator structureDiagramGenerator = new StructureDiagramGenerator();

	@Override
	public void paintComponent(Graphics g) {

		structureDiagramGenerator.setMolecule(example);
		try {
			structureDiagramGenerator.generateCoordinates();
			example = structureDiagramGenerator.getMolecule();
		} catch(CDKException e) {
			e.printStackTrace();
		}
		super.paintComponent(g);
		graphView.renderStructure((Graphics2D)g, example);
	}
}
