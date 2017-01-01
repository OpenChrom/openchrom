/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

public class YIonChargedSingle extends PeptideSequenceChargedSingle {

	public YIonChargedSingle(final List<AminoAcid> peptides) {
		super(peptides);
	}

	public YIonChargedSingle(final String string) {
		super(string);
	}

	public YIonChargedSingle(final String name, final List<AminoAcid> peptides) {
		super(name, peptides);
	}
}
