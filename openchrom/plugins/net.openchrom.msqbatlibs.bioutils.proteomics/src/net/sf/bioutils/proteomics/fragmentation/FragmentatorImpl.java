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
