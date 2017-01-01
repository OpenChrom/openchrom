/*******************************************************************************
 * Copyright (c) 2013, 2017 Marwin Wollschläger, Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - improvements
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openscience.cdk.interfaces.IIsotope;

import org.eclipse.chemclipse.logging.core.Logger;

/**
 * Uses the regex [0-9]*[A-Z]+ to extract the relevant information from the input.
 * This isotope information will either be of the form 12C, 1H, e.g. <mass number> <element symbol>
 * or it will be of the form <element symbol>, for example C, H, O, N etc.
 * To get the correct Isotope out of this information, the class uses the regex
 * [0-9]+ for the mass number and [A-Z][a-z]* for the element symbol.
 * It should be noted, that <em> Element Symbols should be given with capital letters in the beginning. </em>
 * 
 * @author administrator_marwin
 * 
 */
public class IsotopeParser {

	private static final Logger logger = Logger.getLogger(IsotopeParser.class);
	private Pattern isotopePattern;

	public IsotopeParser() {
		isotopePattern = Pattern.compile("([0-9]*)([A-Z][a-z]*)");
	}

	public Set<IIsotope> extract(String input) {

		Set<IIsotope> isotopes = new HashSet<IIsotope>();
		IsotopeDeciderFactory isotopeDeciderFactory = IsotopeDeciderFactory.getInstance();
		/*
		 * Extract the isotope names.
		 */
		Matcher matcher = isotopePattern.matcher(input);
		while(matcher.find()) {
			/*
			 * Try to parse the isotope.
			 */
			IIsotope isotope = null;
			String identifier = matcher.group(1);
			String elementSymbol = matcher.group(2);
			if(identifier == null || identifier.equals("")) {
				/*
				 * C, H, N ....
				 */
				isotope = isotopeDeciderFactory.getIsotope(elementSymbol);
			} else {
				/*
				 * 1H, 12C, 13C, ...
				 */
				try {
					int massNumber = Integer.parseInt(identifier);
					isotope = isotopeDeciderFactory.getIsotope(elementSymbol, massNumber);
				} catch(NumberFormatException e) {
					logger.warn(e);
				}
			}
			/*
			 * Add the isotope.
			 */
			if(isotope != null) {
				isotopes.add(isotope);
			}
		}
		return isotopes;
	}
}
