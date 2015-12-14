/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
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
