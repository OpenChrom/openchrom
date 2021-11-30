/*******************************************************************************
 * Copyright (c) 2019, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Matthias Mailänder - adapt to new ILabel interface
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings.support;

import org.eclipse.chemclipse.support.text.ILabel;

public enum DigitalFilterTreatmentOptions implements ILabel {
	SHIFT_ONLY("Left shift group delay only"), //
	SUBSTITUTE_WITH_ZERO("Left shift group delay and overwrite with zeros"), //
	SUBSTITUTE_WITH_NOISE("Left shift group delay and overwrite with noise");

	private String label;

	private DigitalFilterTreatmentOptions(String label) {

		this.label = label;
	}

	@Override
	public String label() {

		return label;
	}
}
