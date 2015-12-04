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
package net.sf.bioutils.proteomics.fragmentation;

import java.util.Collections;
import java.util.List;

import net.sf.bioutils.proteomics.peptides.AminoAcid;
import net.sf.bioutils.proteomics.peptides.BIonChargedSingle;
import net.sf.bioutils.proteomics.peptides.Peptide;
import net.sf.bioutils.proteomics.peptides.YIonChargedSingle;
import net.sf.kerner.utils.collections.list.UtilList;

public class FragmentatorImpl implements Fragmentator {

	public FragmentatorImpl() {

	}

	@Override
	public List<Peptide> fractionate(final Peptide peptide) {

		final List<Peptide> bIons = UtilList.newList();
		final List<Peptide> yIons = UtilList.newList();
		for(int i = 1; i <= peptide.asAminoAcidList().size(); i++) {
			bIons.add(new BIonChargedSingle("b" + i, peptide.asAminoAcidList().subList(0, i)));
		}
		final List<AminoAcid> copy = UtilList.newList(peptide.asAminoAcidList());
		Collections.reverse(copy);
		for(int i = 1; i <= copy.size(); i++) {
			yIons.add(new YIonChargedSingle("y" + i, copy.subList(0, i)));
		}
		return UtilList.append(bIons, yIons);
	}
}
