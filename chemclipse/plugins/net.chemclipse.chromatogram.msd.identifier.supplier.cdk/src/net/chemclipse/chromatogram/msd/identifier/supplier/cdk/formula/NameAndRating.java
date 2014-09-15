/*******************************************************************************
 * Copyright (c) 2013, 2014 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.formula;

public class NameAndRating {

	private String name;
	private double rating;

	public NameAndRating(String name, double rating) {

		this.name = name;
		this.rating = rating;
	}

	public String getName() {

		return name;
	}

	public double getRating() {

		return rating;
	}
}
