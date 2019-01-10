/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

public abstract class AbstractElement<T> implements IElement<T> {

	private float x = 0.0f;
	private float y = 0.0f;
	private ReferenceX referenceX = ReferenceX.LEFT;
	private ReferenceY referenceY = ReferenceY.TOP;

	@Override
	public float getX() {

		return x;
	}

	public T setX(float x) {

		this.x = x;
		return reference();
	}

	@Override
	public float getY() {

		return y;
	}

	public T setY(float y) {

		this.y = y;
		return reference();
	}

	@Override
	public ReferenceX getReferenceX() {

		return referenceX;
	}

	@Override
	public T setReferenceX(ReferenceX referenceX) {

		this.referenceX = referenceX;
		return reference();
	}

	@Override
	public ReferenceY getReferenceY() {

		return referenceY;
	}

	@Override
	public T setReferenceY(ReferenceY referenceY) {

		this.referenceY = referenceY;
		return reference();
	}

	@SuppressWarnings("unchecked")
	private T reference() {

		return (T)this;
	}
}
