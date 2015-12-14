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
package net.sf.kerner.utils.collections.applier;

public abstract class ApplierAbstract implements Applier {

	public static TYPE DEFAULT_FILTER_TYPE = TYPE.AND;
	protected TYPE type;

	public ApplierAbstract() {

		super();
		type = DEFAULT_FILTER_TYPE;
	}

	public ApplierAbstract(final TYPE type) {

		super();
		this.type = type;
	}
}
