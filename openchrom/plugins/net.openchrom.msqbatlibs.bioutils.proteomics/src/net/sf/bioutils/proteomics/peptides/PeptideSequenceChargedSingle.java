/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

	@Override
	public synchronized void append(final AminoAcid peptide) {

		peptides.add(peptide);
	}

	@Override
	public synchronized int getChargeState() {

		return 1;
	}

	@Override
	public synchronized void insert(final AminoAcid peptide, final int index) {

		peptides.add(index, peptide);
	}
}
