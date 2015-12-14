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
package net.sf.kerner.utils.math;

public class TransformerLogImpl extends TransformerLogAbstract {

	private final double base;

	public TransformerLogImpl(final double base) {

		this.base = base;
	}

	public double invert(final double number) {

		return Math.pow(base, number);
	}

	public Double transform(final Double number) {

		return UtilMath.log(number, base);
	}
}
