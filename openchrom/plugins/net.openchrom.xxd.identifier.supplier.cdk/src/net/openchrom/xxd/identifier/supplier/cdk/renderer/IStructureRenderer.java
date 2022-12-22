/*******************************************************************************
 * Copyright (c) 2013, 2022 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - adjustments
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.renderer;

import java.awt.Graphics2D;

import org.openscience.cdk.interfaces.IAtomContainer;

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

	public void renderStructure(Graphics2D g2d, IAtomContainer moleculeToRender);

	public boolean checkForCoordinates(IAtomContainer moleculeToRender);
}
