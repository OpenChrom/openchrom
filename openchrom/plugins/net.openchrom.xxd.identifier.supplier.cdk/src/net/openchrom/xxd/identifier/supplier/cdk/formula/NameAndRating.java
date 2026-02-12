/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - refactoring bundle name
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.formula;

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
