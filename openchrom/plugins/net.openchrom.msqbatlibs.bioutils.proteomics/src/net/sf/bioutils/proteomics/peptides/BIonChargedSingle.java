/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import java.util.List;

public class BIonChargedSingle extends PeptideSequenceChargedSingle {

	public BIonChargedSingle(final List<AminoAcid> peptides) {
		super(peptides);
	}

	public BIonChargedSingle(final String string) {
		super(string);
	}

	public BIonChargedSingle(final String name, final List<AminoAcid> peptides) {
		super(name, peptides);
	}

	public synchronized double getMolWeightCTerminal() {

		return 0;
	}

	public synchronized double getMolWeightNTerminal() {

		return MOL_WEIGHT_HYDROGEN;
	}
}
