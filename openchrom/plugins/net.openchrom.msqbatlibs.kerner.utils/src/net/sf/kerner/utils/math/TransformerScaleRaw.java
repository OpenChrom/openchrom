/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.math;

public class TransformerScaleRaw implements TransformerScale {

	public double getRatio(final double n1, final double n2) {

		return n1 / n2;
	}

	public double invert(final double number) {

		return number;
	}

	public boolean isLog() {

		return false;
	}

	public Double transform(final Double number) {

		return number;
	}
}
