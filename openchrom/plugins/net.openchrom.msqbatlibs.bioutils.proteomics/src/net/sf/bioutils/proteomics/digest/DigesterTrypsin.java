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
package net.sf.bioutils.proteomics.digest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.chemclipse.logging.core.Logger;

import net.sf.bioutils.proteomics.peptides.AminoAcid;
import net.sf.bioutils.proteomics.peptides.Peptide;
import net.sf.bioutils.proteomics.peptides.PeptideSequenceChargedSingle;
import net.sf.jranges.range.integerrange.impl.RangeIntegerDummy;

public class DigesterTrypsin extends DigesterAbstract {

	private final static Logger logger = Logger.getLogger(DigesterTrypsin.class);
	public final static double DEFAULT_MOL_WEIGHT_MIN = 750;
	public final static double DEFAULT_MOL_WEIGHT_MAX = 4000;
	private double molWeightMin = DEFAULT_MOL_WEIGHT_MIN;
	private double molWeightMax = DEFAULT_MOL_WEIGHT_MAX;

	private void addToResult(final Collection<Peptide> result, final Peptide peptides, final int indexLow, final int indexHigh) {

		// TODO: use factory
		if(indexLow <= indexHigh && indexHigh <= peptides.asAminoAcidList().size()) {
			final PeptideSequenceChargedSingle seq = new PeptideSequenceChargedSingle(peptides.asAminoAcidList().subList(indexLow, indexHigh));
			seq.setModifications(peptides.getModifications());
			result.add(seq);
		} else {
			logger.debug("skip invalid range " + new RangeIntegerDummy(indexLow, indexHigh));
		}
	}

	@Override
	public synchronized List<Peptide> digest(final Peptide peptides, final int numMissCleav) {

		final int len1 = peptides.asAminoAcidList().size();
		final List<Peptide> result = new ArrayList<Peptide>();
		final SortedSet<Integer> cleavSites = getCleavageSites(peptides);
		for(int i = 0; i <= numMissCleav; i++) {
			int lastOne = 0;
			final Iterator<Integer> it = cleavSites.iterator();
			while(it.hasNext()) {
				final Integer next = it.next();
				addToResult(result, peptides, lastOne, getMissCleavIndex(next, i, cleavSites) + 1);
				lastOne = next + 1;
			}
			addToResult(result, peptides, lastOne, peptides.asAminoAcidList().size());
		}
		int len2 = 0;
		for(final Peptide p : result) {
			len2 += p.asAminoAcidList().size();
		}
		if(len1 != len2) {
			// throw new RuntimeException("len1=" + len1 + ", len2=" + len2 +
			// ", pep=" + peptides);
		}
		for(final Iterator<Peptide> it = result.iterator(); it.hasNext();) {
			final Peptide peptide = it.next();
			if(peptide.getMolWeight() < getMolWeightMin() || peptide.getMolWeight() > getMolWeightMax()) {
				it.remove();
			} else {
				// nothing
			}
		}
		return new ArrayList<Peptide>(result);
	}

	public synchronized SortedSet<Integer> getCleavageSites(final Peptide peptides) {

		final TreeSet<Integer> result = new TreeSet<Integer>();
		// last index
		result.add(peptides.asAminoAcidList().size() - 1);
		for(int i = 0; i < peptides.asAminoAcidList().size(); i++) {
			final AminoAcid nextP = peptides.asAminoAcidList().get(i);
			AminoAcid next2P = null;
			if(peptides.asAminoAcidList().size() > i + 1) {
				next2P = peptides.asAminoAcidList().get(i + 1);
			}
			if((nextP.equals(AminoAcid.R) || nextP.equals(AminoAcid.K)) && ((next2P == null || !next2P.equals(AminoAcid.P)))) {
				result.add(i);
			}
		}
		return result;
	}

	private int getMissCleavIndex(final int currentIndex, final int numMissCLeav, final SortedSet<Integer> cleavSites) {

		final List<Integer> copy = new ArrayList<Integer>(cleavSites);
		final int indexOf = copy.indexOf(currentIndex);
		if(copy.size() > indexOf + numMissCLeav) {
			return copy.get(indexOf + numMissCLeav);
		} else {
			// if (log.isDebugEnabled()) {
			// log.debug("skip invalid miss cleav index " + indexOf +
			// numMissCLeav);
			// }
			return currentIndex;
		}
	}

	public synchronized double getMolWeightMax() {

		return molWeightMax;
	}

	public synchronized double getMolWeightMin() {

		return molWeightMin;
	}

	public synchronized void setMolWeightMax(final double molWeightMax) {

		this.molWeightMax = molWeightMax;
	}

	public synchronized void setMolWeightMin(final double molWeightMin) {

		this.molWeightMin = molWeightMin;
	}
}
