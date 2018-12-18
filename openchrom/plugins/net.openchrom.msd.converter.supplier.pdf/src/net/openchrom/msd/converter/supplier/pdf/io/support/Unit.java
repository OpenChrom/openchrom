/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

public enum Unit {
	PT(1.0f), //
	MM(2.83465f), //
	CM(28.3465f), //
	INCH(72.0f);

	private float factor;

	private Unit(float factor) {
		this.factor = factor;
	}

	public float getFactor() {

		return factor;
	}
}
