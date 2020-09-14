/*******************************************************************************
 * Copyright (c) 2015, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 * Philip Wenig - number format refactoring
 *******************************************************************************/
package net.sf.kerner.utils.collections.list;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.support.text.ValueFormat;

import net.sf.kerner.utils.transformer.TransformerToString;

public class TransformerNumberToString implements TransformerToString<Number> {

	private final DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();

	@Override
	public String transform(Number element) {

		return decimalFormat.format(element);
	}
}
