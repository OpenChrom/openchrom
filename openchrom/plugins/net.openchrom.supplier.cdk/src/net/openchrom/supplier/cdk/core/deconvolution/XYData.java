/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core.deconvolution;

import java.util.List;

/**
 * A simple generic container class, that holds track of an X and Y List.
 * It can be used for handling data that comes in form of a list of pairs (2-tupels).
 * 
 * @author administrator_marwin
 * 
 * @param <T>
 */
public class XYData<T> {

	List<T> X;
	List<T> Y;

	public XYData(List<T> X, List<T> Y) {

		this.X = X;
		this.Y = Y;
	}

	public Pair<T> getPairAt(int index) {

		return new Pair<T>(X.get(index), Y.get(index));
	}
}
