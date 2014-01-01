/*******************************************************************************
 * Copyright (c) 2013, 2014 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - additional API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.converter;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.iupac.parser.NomParser;
import org.openscience.cdk.iupac.parser.ParseException;

import net.openchrom.logging.core.Logger;

/**
 * Wrapper class for using the iupac functionality of the CDK. It should be noted though, that
 * the OPSIN package provides a more convenient way of parsing iupac names.
 * => use OPSIN when possible.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKIupacToMoleculeConverter implements IStructureConverter {

	private static final Logger logger = Logger.getLogger(CDKIupacToMoleculeConverter.class);

	@Override
	public IMolecule generate(String input) {

		IMolecule result = null;
		if(input != null) {
			try {
				result = NomParser.generate(input);
			} catch(ParseException e) {
				logger.warn("NomParser.generate() method encountered a ParseException.\n" + "While this is not a fatal error, results of the class CDKIupacToIMoleculeConverter " + " \nand thus from the MoleculePanel could differ from your expectation. " + e);
				return null;
			} catch(CDKException e) {
				logger.warn("NomParser.generate() method encountered a CDKException.\n" + "While this is not a fatal error, results of the class CDKIupacToIMoleculeConverter " + " \nand thus from the MoleculePanel could differ from your expectation. " + e);
				return null;
			}
		}
		return result;
	}
}
