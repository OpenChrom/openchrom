/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.identifier.supplier.cdk.renderer;

import java.awt.Graphics2D;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * Interface that all Renderers for Molecules must implement.
 * Has a method checkForCoordinates(), that verifies validity and a method for
 * adding missing coordinates...
 * Contains a method renderStructure(Graphics2D,IMolecule) that does the job.
 */
public interface IStructureRenderer {

	public void renderStructure(Graphics2D g2d, IAtomContainer moleculeToRender);

	public boolean checkForCoordinates(IAtomContainer moleculeToRender);
}
