/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peptides;

public enum Modification {
	CARBAMIDOMETHYL(AminoAcid.C, 160.030654);

	private final AminoAcid parent;
	private final double molWeight;

	private Modification(final AminoAcid parent, final double molWeight) {
		this.molWeight = molWeight;
		this.parent = parent;
	}

	public double getMolWeight() {

		return molWeight;
	}

	public AminoAcid getParent() {

		return parent;
	}
}
