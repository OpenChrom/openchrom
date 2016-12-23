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

import java.util.Collection;
import java.util.List;

public interface Peptide extends Iterable<AminoAcid> {

	public static double MOL_WEIGHT_OXYGEN = 15.9994;
	public static double MOL_WEIGHT_HYDROGEN = 1.0078;

	List<AminoAcid> asAminoAcidList();

	List<Character> asCharacterList();

	String asString();

	List<String> asStringList();

	boolean contains(AminoAcid p);

	int getChargeState();

	Collection<Modification> getModifications();

	double getMolWeight();

	double getMolWeightCTerminal();

	double getMolWeightNTerminal();

	void setModifications(Collection<Modification> modifications);
}
