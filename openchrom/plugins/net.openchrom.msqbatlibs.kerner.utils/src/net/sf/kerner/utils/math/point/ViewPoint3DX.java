/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.math.point;

import net.sf.kerner.utils.transformer.Transformer;

public class ViewPoint3DX implements Transformer<Point3D, Double> {

	public Double transform(final Point3D element) {

		if(element == null) {
			return null;
		}
		return element.getX();
	}
}
