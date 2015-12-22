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
package net.sf.bioutils.proteomics.peptidesearcher;

import java.util.Collection;
import java.util.List;

import net.sf.jfasta.impl.HeaderDialectUniprot;
import net.sf.kerner.utils.collections.UtilCollection;

class FactoryResult {

	static boolean areSameByID(final Collection<String> headers, final DatabaseID databaseID) {

		boolean result = true;
		String idOld = null;
		for(final String header : headers) {
			final String id = getIDStringFromHeader(header, databaseID);
			if(idOld == null || idOld.equals(id)) {
				idOld = id;
			} else if(idOld != null && !idOld.equals(id)) {
				result = false;
				break;
			}
		}
		return result;
	}

	static String getIDStringFromHeader(final String fastaHeader, final DatabaseID databaseId) {

		final HeaderDialectUniprot headerDialect = new HeaderDialectUniprot();
		headerDialect.setHeaderString(fastaHeader);
		switch(databaseId) {
			case ACCESSION:
				return headerDialect.getAccessionNumber();
			case GENE:
				return headerDialect.getGeneName();
			case PROTEIN:
				return headerDialect.getProteinName();
			case SPECIES:
				return headerDialect.getSpeciesName();
			default:
				throw new RuntimeException("Unkown ID " + databaseId);
		}
	}

	public Result create(final List<String> headers, final DatabaseID type) {

		if(type == null) {
			throw new IllegalArgumentException("No type defined");
		}
		if(UtilCollection.nullOrEmpty(headers)) {
			return Result.buildNotFound();
		}
		if(headers.size() == 1) {
			return Result.buildProteotypic(headers);
		} else {
			switch(type) {
				case ACCESSION:
					if(areSameByID(headers, type)) {
						return Result.buildProteotypic(headers);
					}
					break;
				case GENE:
					if(areSameByID(headers, type)) {
						return Result.buildProteotypic(headers);
					}
					break;
				case PROTEIN:
					if(areSameByID(headers, type)) {
						return Result.buildProteotypic(headers);
					}
					break;
				case SPECIES:
					if(areSameByID(headers, type)) {
						return Result.buildProteotypic(headers);
					}
					break;
				case PROTEIN_GENE:
					if(areSameByID(headers, DatabaseID.SPECIES) && (areSameByID(headers, DatabaseID.PROTEIN) || areSameByID(headers, DatabaseID.GENE))) {
						return Result.buildProteotypic(headers);
					}
					break;
				default:
					throw new RuntimeException("Unkown type " + type);
			}
			return Result.buildDegen(headers);
		}
	}
}
