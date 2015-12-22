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
package net.sf.bioutils.proteomics;

import java.util.HashMap;
import java.util.Map;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakFractionated;
import net.sf.bioutils.proteomics.peptides.AminoAcid;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.UtilString;

import org.apache.log4j.Logger;

public class UtilProteomics {

	private final static Logger logger = Logger.getLogger(UtilProteomics.class);
	private final static Map<Character, AminoAcid> molMassesChar = new HashMap<Character, AminoAcid>();
	private final static Map<String, AminoAcid> molMassesString = new HashMap<String, AminoAcid>();
	private final static double terminiWeight = 18.0152;
	static {
		for(final AminoAcid p : AminoAcid.values()) {
			molMassesChar.put(p.getSingleCharIdent(), p);
			molMassesString.put(p.getTrippeCharIdent(), p);
		}
	}

	public static double getPeptideMolWeight(final String sequence, final boolean tripplets) {

		if(tripplets) {
			return getPeptideMolWeightTripplets(sequence);
		} else {
			return getPeptideMolWeightSinglets(sequence);
		}
	}

	public static double getPeptideMolWeightSinglets(final String sequence) {

		if(UtilString.emptyString(sequence)) {
			throw new IllegalArgumentException("empty sequence " + sequence);
		}
		double result = 0;
		for(final char c : sequence.toCharArray()) {
			final AminoAcid p = molMassesChar.get(c);
			if(p == null) {
				logger.warn("ignoring AS " + c);
			} else {
				result += p.getMolWeight();
			}
		}
		return result + terminiWeight;
	}

	public static double getPeptideMolWeightTripplets(final String sequence) {

		double result = 0;
		StringBuilder current = new StringBuilder();
		for(final char c : sequence.toCharArray()) {
			if(current.length() < 3) {
				current.append(c);
			} else {
				result += molMassesString.get(current).getMolWeight();
				current = new StringBuilder();
			}
		}
		result += molMassesString.get(current).getMolWeight();
		return result + terminiWeight;
	}

	public static boolean isAnnotatedMSMS(final Sample sample) {

		for(final Peak p : sample.getPeaks()) {
			if(p instanceof PeakFractionated) {
				final PeakFractionated pf = (PeakFractionated)p;
				if(pf.getSpectrum() != null && !pf.getSpectrum().getPeaks().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	private UtilProteomics() {

	}
}
