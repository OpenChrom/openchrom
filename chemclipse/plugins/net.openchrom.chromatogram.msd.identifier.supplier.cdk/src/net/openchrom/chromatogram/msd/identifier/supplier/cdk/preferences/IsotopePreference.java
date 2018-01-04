/*******************************************************************************
 * Copyright (c) 2013, 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences;

public enum IsotopePreference {
	BASIC(1), ORGANIC(2), USER_DEFINED(3);

	private int isotopePreference;

	private IsotopePreference(int isotopePreference) {
		this.isotopePreference = isotopePreference;
	}

	public int getIsotopePreference() {

		return isotopePreference;
	}
}
