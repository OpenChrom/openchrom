/*******************************************************************************
 * Copyright (c) 2013, 2015 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.renderer;

import java.awt.Graphics2D;

import org.openscience.cdk.interfaces.IMolecule;

/**
 * Interface that all Renderers for Molecules must implement.
 * Has a method checkForCoordinates(), that verifies validity and a method for
 * adding missing coordinates...
 * Contains a method renderStructure(Graphics2D,IMolecule) that does the job.
 * 
 * @author administrator_marwin
 * 
 */
public interface IStructureRenderer {

	public void renderStructure(Graphics2D g2d, IMolecule moleculeToRender);

	public boolean checkForCoordinates(IMolecule moleculeToRender);
}
