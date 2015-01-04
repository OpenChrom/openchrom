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
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.support;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fragment.IFragmenter;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * Base class for use in ms prediction (not implemented yet).
 * 
 * @author administrator_marwin
 * 
 */
public class FullFragmenter implements IFragmenter {

	@Override
	public void generateFragments(IAtomContainer arg0) throws CDKException {

		// ExhaustiveFragmenter frag = new ExhaustiveFragmenter();
	}

	@Override
	public String[] getFragments() {

		return null;
	}

	@Override
	public IAtomContainer[] getFragmentsAsContainers() {

		return null;
	}
}
