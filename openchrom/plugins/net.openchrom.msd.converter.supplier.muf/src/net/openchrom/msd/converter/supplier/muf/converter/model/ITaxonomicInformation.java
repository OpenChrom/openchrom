/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.muf.converter.model;

public interface ITaxonomicInformation {

	String getGenus();

	void setGenus(String genus);

	String getSpecies();

	void setSpecies(String species);

	String getStrain();

	void setStrain(String strain);

	int getTaxonmicIdentifierNCBI();

	void setTaxonmicIdentifierNCBI(int id);

	int getUnmodifiedTaxonmicIdentifierNCBI();

	void setUnmodifiedTaxonmicIdentifierNCBI(int id);
}
