/*******************************************************************************
 * Copyright (c) 2013, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.preferences;

public enum IsotopePreference {

	BASIC(1), //
	ORGANIC(2), //
	USER_DEFINED(3);

	private int isotopePreference;

	private IsotopePreference(int isotopePreference) {

		this.isotopePreference = isotopePreference;
	}

	public int getIsotopePreference() {

		return isotopePreference;
	}
}
