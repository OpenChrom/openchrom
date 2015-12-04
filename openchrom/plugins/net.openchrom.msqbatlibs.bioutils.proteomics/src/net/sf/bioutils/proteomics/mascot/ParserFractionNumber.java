package net.sf.bioutils.proteomics.mascot;

import java.io.IOException;
import java.util.Arrays;

import net.sf.kerner.utils.exception.ExceptionFileFormat;

public class ParserFractionNumber {

	public int parseFractionNumber(String string) throws IOException {

		String[] s = string.split("\\.");
		if(s.length != 5) {
			throw new ExceptionFileFormat("Unexpected array length " + Arrays.asList(s));
		}
		int result = Integer.parseInt(s[3]);
		return result;
	}
}
