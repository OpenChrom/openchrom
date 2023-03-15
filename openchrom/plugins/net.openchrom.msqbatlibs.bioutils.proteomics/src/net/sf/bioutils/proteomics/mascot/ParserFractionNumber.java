/*******************************************************************************
 * Copyright (c) 2015, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.sf.bioutils.proteomics.mascot;

import java.io.IOException;
import java.util.Arrays;

import net.sf.kerner.utils.exception.ExceptionFileFormat;

public class ParserFractionNumber {

	public int parseFractionNumber(String string) throws IOException {

		String[] values = string.split("\\.");
		if(values.length != 5) {
			throw new ExceptionFileFormat("Unexpected array length " + Arrays.asList(values));
		}
		int result = Integer.parseInt(values[3]);
		return result;
	}
}