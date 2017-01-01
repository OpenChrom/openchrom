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

import net.sf.bioutils.proteomics.exception.ExceptionUnknownAminoAcid;

/**
 * 
 * </p>
 * <b>Note:</b> masses are monoisotopic.
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 0000-00-00
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 0000-00-00
 * 
 */
public enum AminoAcid {
	/**
	 * <a href="http://en.wikipedia.org/wiki/Alanine">en.wikipedia.org/wiki/
	 * Alanine</a>
	 */
	A("Ala", 71.03711),
	/**
	 * <a href="http://en.wikipedia.org/wiki/Cysteine">en.wikipedia.org/wiki/
	 * Cysteine</a>
	 */
	C("Cys", 103.00919),
	/**
	 * <a
	 * href="http://en.wikipedia.org/wiki/Aspartic_acid">en.wikipedia.org/wiki/
	 * Aspartic acid</a>
	 */
	D("Asp", 115.02694),
	/**
	 * <a href="http://en.wikipedia.org/wiki/Glutamic_acid">http://en.wikipedia.
	 * org/wiki/Glutamic_acid</a>
	 */
	E("Glu", 129.04259),
	/**
	 * <a href="http://en.wikipedia.org/wiki/Phenylalanine">http://en.wikipedia.
	 * org/wiki/Phenylalanine</a>
	 */
	F("Phe", 147.06841), G("Gly", 57.02146), H("His", 137.05891), I("Ile", 113.08406), K("Lys", 128.09496), L("Leu", 113.08406), M("Met", 131.04049), N("Asn", 114.04293), P("Pro", 97.05276), Q("Gln", 128.05858), R("Arg", 156.10111), S("Ser", 87.03203), T("Thr", 101.04768), V("Val", 99.06841), W("Trp", 186.07931), Y("Tyr", 163.06333), U("Sec", 150.953639), O("Pyl", 237.143012), B("Asx", N.getMolWeight()), Z("Glx", Q.getMolWeight()), J("Xle", L.getMolWeight()), X("Xaa", 0);

	public static AminoAcid parse(final String s) throws ExceptionUnknownAminoAcid {

		if(s.length() == 1 || s.length() == 3) {
			final String s2 = s.toUpperCase();
			if(s2.length() == 1) {
				return parseSingleChar(s2.charAt(0));
			} else if(s2.length() == 3) {
				return parseTrippleChar(s2);
			} else {
				throw new RuntimeException();
			}
		} else {
			throw new IllegalArgumentException("invalid length " + s.length());
		}
	}

	public static AminoAcid parseSingleChar(final char c) {

		for(final AminoAcid p : values()) {
			if(p.getSingleCharIdent() == c) {
				return p;
			}
		}
		throw new ExceptionUnknownAminoAcid("unknown peptide " + c);
	}

	public static AminoAcid parseTrippleChar(final String s) {

		for(final AminoAcid p : values()) {
			if(p.getTrippeCharIdent().equals(s)) {
				return p;
			}
		}
		throw new IllegalArgumentException("unknown peptide " + s);
	}

	private final double molWeight;
	private final String trippleCharIdent;

	private AminoAcid(final String trippleCharIdent, final double molWeight) {
		this.molWeight = molWeight;
		this.trippleCharIdent = trippleCharIdent;
	}

	public double getMolWeight() {

		return molWeight;
	}

	public char getSingleCharIdent() {

		return toString().charAt(0);
	}

	public String getTrippeCharIdent() {

		return trippleCharIdent;
	}
}
