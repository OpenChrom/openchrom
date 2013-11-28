/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core.massToFormula;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Text;
import org.openscience.cdk.interfaces.IIsotope;

/**
 * Uses the regex [0-9]*[A-Z]+ to extract the relevant information out of
 * the Text-Widget of UIIsotopeDeciderDialog.
 * This isotope information will either be of the form 12C, 1H, e.g. <mass number> <element symbol>
 * or it will be of the form <element symbol>, for example C, H, O, N etc.
 * To get the correct Isotope out of this information, the class uses the regex
 * [0-9]+ for the mass number and [A-Z][a-z]* for the element symbol.
 * It should be noted, that <em> Element Symbols should be given with capital letters in the beginning. </em>
 * 
 * @author administrator_marwin
 * 
 */
public class IsotopeDeciderRegexHelper {

	Pattern regexForIsotopes;
	Pattern regexForIsotopesOrder;
	Pattern regexForIsotopesNames;
	List<IIsotope> isotopesToSet;

	public IsotopeDeciderRegexHelper() {

		regexForIsotopes = Pattern.compile("[0-9]*[A-Z][a-z]*");
		regexForIsotopesNames = Pattern.compile("[A-Z][a-z]*");
		regexForIsotopesOrder = Pattern.compile("[0-9]+");
		isotopesToSet = new ArrayList<IIsotope>();
	}

	public void generate(Text text) {

		String input = text.getText();
		Matcher matcher = regexForIsotopes.matcher(input);
		List<String> isotopesToSetNames = new ArrayList<String>();
		while(matcher.find()) {
			String isotopeText = input.substring(matcher.start(), matcher.end());
			isotopesToSetNames.add(isotopeText);
		}
		IsotopeDeciderFactory iDecFac = IsotopeDeciderFactory.getInstance();
		for(String nameAndNum : isotopesToSetNames) {
			Matcher matchName = regexForIsotopesNames.matcher(nameAndNum);
			matchName.find();
			String name = nameAndNum.substring(matchName.start(), matchName.end());
			Matcher matchOrder = regexForIsotopesNames.matcher(nameAndNum);
			if(matchOrder.find()) {// a mass number was given, e.g. input is of form <massNumber><Symbol>, for example 12C
				String order = nameAndNum.substring(matchOrder.start(), matchOrder.end());
				isotopesToSet.add(iDecFac.getIIsotopeFromStringAndInteger(name, Integer.parseInt(order)));
			}
			// isotopesToSet.add(iDecFac.)//TODO: Work from HERE!
			else {
				// no mass number was given, e.g. input is of form <element symbol>, for example C
				isotopesToSet.add(iDecFac.getMajorIsotopeFromString(name));
			}
		}
	}
}
