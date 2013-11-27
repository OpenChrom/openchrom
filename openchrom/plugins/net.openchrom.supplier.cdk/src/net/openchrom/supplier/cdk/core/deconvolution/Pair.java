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
/**
 * Generic container class for holding pairs of any type(2-tupels).
 * @author administrator_marwin
 *
 * @param <T>
 */

public class Pair<T> {
	public T x;// so that x can easily be accessed
	public T y;// so that y can easily be accessed
	
	// allow instantiation only when x,y are given!
	public Pair(T x, T y)
	{
		this.x = x; this.y = y;
	}
}
