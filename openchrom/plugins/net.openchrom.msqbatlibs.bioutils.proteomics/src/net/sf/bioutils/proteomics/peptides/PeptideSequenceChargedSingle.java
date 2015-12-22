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

import java.util.List;

public class PeptideSequenceChargedSingle extends PeptideSequenceAbstract implements PeptideSequenceModifiable {

	public PeptideSequenceChargedSingle(final List<AminoAcid> peptides) {

		super(peptides);
	}

	public PeptideSequenceChargedSingle(final String string) {

		super(string);
	}

	public PeptideSequenceChargedSingle(final String name, final List<AminoAcid> peptides) {

		super(name, peptides);
	}

	public synchronized void append(final AminoAcid peptide) {

		peptides.add(peptide);
	}

	public synchronized int getChargeState() {

		return 1;
	}

	public synchronized void insert(final AminoAcid peptide, final int index) {

		peptides.add(index, peptide);
	}
}
