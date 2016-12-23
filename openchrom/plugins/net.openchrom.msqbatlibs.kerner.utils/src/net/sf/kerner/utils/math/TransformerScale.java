/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import net.sf.kerner.utils.transformer.Transformer;

public interface TransformerScale extends Transformer<Double, Double> {

	double getRatio(double n1, double n2);

	double invert(double number);

	boolean isLog();
}
