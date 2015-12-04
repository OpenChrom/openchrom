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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import net.sf.kerner.utils.collections.list.UtilList;

public abstract class PeptideSequenceAbstract implements Peptide {

	protected final List<AminoAcid> peptides;
	protected Collection<Modification> mods = null;
	protected String name;

	public PeptideSequenceAbstract(final List<AminoAcid> peptides) {

		this(null, peptides);
	}

	public PeptideSequenceAbstract(final String string) {

		peptides = new ArrayList<AminoAcid>(string.length());
		for(final char c : string.toCharArray()) {
			peptides.add(AminoAcid.parseSingleChar(c));
		}
	}

	public PeptideSequenceAbstract(final String name, final List<AminoAcid> peptides) {

		this.name = name;
		this.peptides = new ArrayList<AminoAcid>(peptides);
	}

	@Override
	public synchronized List<AminoAcid> asAminoAcidList() {

		return Collections.unmodifiableList(peptides);
	}

	@Override
	public synchronized List<Character> asCharacterList() {

		final List<Character> result = UtilList.newList();
		for(final AminoAcid p : peptides) {
			result.add(p.getSingleCharIdent());
		}
		return result;
	}

	@Override
	public synchronized String asString() {

		final StringBuilder sb = new StringBuilder();
		for(final AminoAcid p : peptides) {
			sb.append(p);
		}
		return sb.toString();
	}

	@Override
	public synchronized List<String> asStringList() {

		return UtilList.toStringList(asCharacterList());
	}

	@Override
	public synchronized boolean contains(final AminoAcid p) {

		return asAminoAcidList().contains(p);
	}

	@Override
	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof PeptideSequenceAbstract))
			return false;
		final PeptideSequenceAbstract other = (PeptideSequenceAbstract)obj;
		if(peptides == null) {
			if(other.peptides != null)
				return false;
		} else if(!peptides.equals(other.peptides))
			return false;
		return true;
	}

	@Override
	public synchronized Collection<Modification> getModifications() {

		if(mods == null) {
			mods = new LinkedHashSet<Modification>();
		}
		return Collections.unmodifiableCollection(mods);
	}

	@Override
	public synchronized double getMolWeight() {

		double result = 0;
		for(final AminoAcid p : peptides) {
			result += getMolWeight(p);
		}
		return result + getMolWeightCTerminal() + getMolWeightNTerminal();
	}

	protected synchronized double getMolWeight(final AminoAcid p) {

		for(final Modification m : getModifications()) {
			if(m.getParent().equals(p)) {
				return m.getMolWeight();
			}
		}
		return p.getMolWeight();
	}

	@Override
	public synchronized double getMolWeightCTerminal() {

		return MOL_WEIGHT_OXYGEN;
	}

	@Override
	public synchronized double getMolWeightNTerminal() {

		return (2 * MOL_WEIGHT_HYDROGEN) + (getChargeState() * MOL_WEIGHT_HYDROGEN);
	}

	public synchronized String getName() {

		return name;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((peptides == null) ? 0 : peptides.hashCode());
		return result;
	}

	@Override
	public synchronized Iterator<AminoAcid> iterator() {

		return asAminoAcidList().iterator();
	}

	@Override
	public synchronized void setModifications(final Collection<Modification> modifications) {

		mods = new HashSet<Modification>(modifications);
	}

	public synchronized void setName(final String name) {

		this.name = name;
	}

	@Override
	public synchronized String toString() {

		final StringBuilder sb = new StringBuilder();
		for(final AminoAcid p : peptides) {
			sb.append(p);
		}
		sb.append(":");
		sb.append(String.format("%10.4f", getMolWeight()));
		return sb.toString();
	}
}
