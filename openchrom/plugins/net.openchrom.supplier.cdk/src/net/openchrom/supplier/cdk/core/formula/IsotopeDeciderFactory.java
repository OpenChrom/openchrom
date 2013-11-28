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
package net.openchrom.supplier.cdk.core.formula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;

import net.openchrom.logging.core.Logger;

/**
 * A class for managing simple collections of isotopes that are often in use,
 * e.g., all isotopes of the PSE or only the most abundant isotopes etc ...
 * 
 * @author administrator_marwin
 * 
 */
public class IsotopeDeciderFactory {

	private static final Logger logger = Logger.getLogger(IsotopeDeciderFactory.class);

	private IsotopeDeciderFactory() {

	};

	private static IsotopeDeciderFactory singleton;

	public static IsotopeDeciderFactory getInstance() {

		if(singleton == null)
			singleton = new IsotopeDeciderFactory();
		return singleton;
	}

	public IsotopeDecider getBasicSet() {

		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IsotopeDecider isotopeDecider = new IsotopeDecider();
		IsotopeFactory ifac;
		try {
			ifac = IsotopeFactory.getInstance(builder);
			List<IIsotope> isotopeSet = new ArrayList<IIsotope>();
			IIsotope c12 = ifac.getMajorIsotope("C");
			IIsotope h1 = ifac.getMajorIsotope("H");
			IIsotope n14 = ifac.getMajorIsotope("N");
			IIsotope o16 = ifac.getMajorIsotope("O");
			isotopeSet.add(c12);
			isotopeSet.add(h1);
			isotopeSet.add(n14);
			isotopeSet.add(o16);
			isotopeDecider.setIsotopeSet(isotopeSet);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory. This is because of the following error:\n" + e);
		}
		return isotopeDecider;
	}

	public IsotopeDecider getIsotopeDeciderFromIsotopeList(List<IIsotope> isotopes) {

		// IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IsotopeDecider isotopeDecider = new IsotopeDecider();
		// IsotopeFactory ifac = IsotopeFactory.getInstance(builder);
		List<IIsotope> isotopeSet = new ArrayList<IIsotope>();
		for(IIsotope toAdd : isotopes) {
			isotopeSet.add(toAdd);
		}
		isotopeDecider.setIsotopeSet(isotopeSet);
		return isotopeDecider;
	}

	public IIsotope getMajorIsotopeFromString(String nameOfMajorIsotope) {

		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IsotopeFactory ifac;
		IIsotope result = null;
		try {
			ifac = IsotopeFactory.getInstance(builder);
			result = ifac.getMajorIsotope(nameOfMajorIsotope);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return result;
	}

	public IIsotope getIIsotopeFromStringAndInteger(String str, int num) {

		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IsotopeFactory ifac;
		IIsotope result = null;
		try {
			ifac = IsotopeFactory.getInstance(builder);
			result = ifac.getIsotope(str, num);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return result;
	}

	public IsotopeDecider getIsotopeDeciderFromStringArray(String[] isotopes) {

		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IsotopeDecider isotopeDecider = new IsotopeDecider();
		IsotopeFactory ifac;
		try {
			ifac = IsotopeFactory.getInstance(builder);
			List<IIsotope> isotopeSet = new ArrayList<IIsotope>();
			for(String isotope : isotopes) {
				IIsotope toAdd = ifac.getMajorIsotope(isotope);
				isotopeSet.add(toAdd);
			}
			isotopeDecider.setIsotopeSet(isotopeSet);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return isotopeDecider;
	}

	public IsotopeDecider getImportantOrganicIsotopes() {

		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IsotopeDecider isotopeDecider = new IsotopeDecider();
		IsotopeFactory ifac;
		try {
			ifac = IsotopeFactory.getInstance(builder);
			List<IIsotope> isotopeSet = new ArrayList<IIsotope>();
			IIsotope c12 = ifac.getMajorIsotope("C");
			IIsotope h1 = ifac.getMajorIsotope("H");
			IIsotope n14 = ifac.getMajorIsotope("N");
			IIsotope o16 = ifac.getMajorIsotope("O");
			IIsotope cl = ifac.getMajorIsotope("Cl");
			IIsotope br = ifac.getMajorIsotope("Br");
			IIsotope s = ifac.getMajorIsotope("S");
			IIsotope p = ifac.getMajorIsotope("P");
			IIsotope i = ifac.getMajorIsotope("I");
			IIsotope b = ifac.getMajorIsotope("B");
			isotopeSet.add(c12);
			isotopeSet.add(h1);
			isotopeSet.add(n14);
			isotopeSet.add(o16);
			isotopeSet.add(cl);
			isotopeSet.add(br);
			isotopeSet.add(s);
			isotopeSet.add(p);
			isotopeSet.add(i);
			isotopeSet.add(b);
			isotopeDecider.setIsotopeSet(isotopeSet);
		} catch(IOException e) {
			logger.warn("For some Reason i couldnt instantiate an instance of IsotopeFactory and this is because of the error:\n" + e);
		}
		return isotopeDecider;
	}
}
