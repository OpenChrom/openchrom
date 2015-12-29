/*******************************************************************************
 * Copyright (c) 2013, 2015 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;

import org.eclipse.chemclipse.logging.core.Logger;

/**
 * A class for managing simple collections of isotopes that are often in use,
 * e.g., all isotopes of the PSE or only the most abundant isotopes etc ...
 * 
 * @author administrator_marwin
 * 
 */
public class IsotopeDeciderFactory {

	private static final Logger logger = Logger.getLogger(IsotopeDeciderFactory.class);
	private static IsotopeDeciderFactory singleton;

	private IsotopeDeciderFactory() {
	}

	public static IsotopeDeciderFactory getInstance() {

		if(singleton == null) {
			singleton = new IsotopeDeciderFactory();
		}
		return singleton;
	}

	/**
	 * Returns the isotope decider for: C H N O
	 * 
	 * @return {@link IsotopeDecider}
	 */
	public IsotopeDecider getBasicIsotopes() {

		Set<String> isotopes = new HashSet<String>();
		isotopes.add("C");
		isotopes.add("H");
		isotopes.add("N");
		isotopes.add("O");
		return getIsotopeDecider(isotopes);
	}

	/**
	 * Returns the isotope decider for: C H N O Cl Br S P I B
	 * 
	 * @return {@link IsotopeDecider}
	 */
	public IsotopeDecider getImportantOrganicIsotopes() {

		Set<String> isotopes = new HashSet<String>();
		isotopes.add("C");
		isotopes.add("H");
		isotopes.add("N");
		isotopes.add("O");
		isotopes.add("Cl");
		isotopes.add("Br");
		isotopes.add("S");
		isotopes.add("P");
		isotopes.add("I");
		isotopes.add("B");
		return getIsotopeDecider(isotopes);
	}

	public IsotopeDecider getIsotopeDecider(Set<String> isotopes) {

		IChemObjectBuilder chemObjectBuilder = DefaultChemObjectBuilder.getInstance();
		IsotopeDecider isotopeDecider = new IsotopeDecider();
		try {
			IsotopeFactory isotopeFactory = IsotopeFactory.getInstance(chemObjectBuilder);
			List<IIsotope> isotopeSet = new ArrayList<IIsotope>();
			for(String isotope : isotopes) {
				isotopeSet.add(isotopeFactory.getMajorIsotope(isotope));
			}
			isotopeDecider.setIsotopeSet(isotopeSet);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return isotopeDecider;
	}

	public IsotopeDecider getIsotopeDeciderFromIsotopeSet(Set<IIsotope> isotopes) {

		IsotopeDecider isotopeDecider = new IsotopeDecider();
		List<IIsotope> isotopeSet = new ArrayList<IIsotope>();
		for(IIsotope toAdd : isotopes) {
			isotopeSet.add(toAdd);
		}
		isotopeDecider.setIsotopeSet(isotopeSet);
		return isotopeDecider;
	}

	public IIsotope getIsotope(String elementSymbol) {

		IChemObjectBuilder chemObjectBuilder = DefaultChemObjectBuilder.getInstance();
		IsotopeFactory isotopeFactory;
		IIsotope result = null;
		try {
			isotopeFactory = IsotopeFactory.getInstance(chemObjectBuilder);
			result = isotopeFactory.getMajorIsotope(elementSymbol);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return result;
	}

	public IIsotope getIsotope(String elementSymbol, int massNumber) {

		IChemObjectBuilder chemObjectBuilder = DefaultChemObjectBuilder.getInstance();
		IsotopeFactory isotopeFactory;
		IIsotope result = null;
		try {
			isotopeFactory = IsotopeFactory.getInstance(chemObjectBuilder);
			result = isotopeFactory.getIsotope(elementSymbol, massNumber);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return result;
	}
}
