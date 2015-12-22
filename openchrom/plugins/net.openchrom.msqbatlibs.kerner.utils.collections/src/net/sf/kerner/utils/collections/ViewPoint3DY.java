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
package net.sf.kerner.utils.collections;

import java.util.Collection;

import net.sf.kerner.utils.math.point.Point3D;
import net.sf.kerner.utils.transformer.Transformer;

public class ViewPoint3DY implements Transformer<Point3D, Double>, TransformerCollection<Point3D, Double> {

	public Double transform(final Point3D element) {

		if(element == null) {
			return null;
		}
		return element.getY();
	}

	public Collection<Double> transformCollection(final Collection<? extends Point3D> element) {

		final Collection<Double> result = UtilCollection.newCollection();
		for(final Point3D p : element) {
			result.add(transform(p));
		}
		return result;
	}
}
