package net.openchrom.supplier.cdk.core;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * A simple yet useful class for parsing smiles to IMolecules
 * that can then be rendered.
 * 
 * @author marwin
 * 
 */
public class ChromSmilesParser {

	// The actual SmilesParser:
	private SmilesParser _sp;

	//
	private ChromSmilesParser() {

	};

	//
	public static ChromSmilesParser getInstance() {

		ChromSmilesParser parser = new ChromSmilesParser();
		parser._sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		return parser;
	}

	// Class to generate IMolecule out of a smiles String
	public IMolecule generate(String smilesString) {

		IMolecule molecule = null;
		try {
			molecule = _sp.parseSmiles(smilesString);
		} catch(InvalidSmilesException ise) {
			System.err.println(//
			"Cannot parse input as Smiles String, \n" + "because the following error occured:\n" + ise);
		}
		return molecule;
	}
}
