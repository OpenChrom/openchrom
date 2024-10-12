/*******************************************************************************
 * Copyright (c) 2009, 2024 Tasktop Technologies, Polarion Software and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

/**
 * @author David Green
 * @author Igor Burilo
 */
public enum PluginDescriptorKind {

	CONVERTER("converter"), //$NON-NLS-1$
	EXTENSION("extension"); //$NON-NLS-1$

	private final String value;

	private PluginDescriptorKind(String value) {

		this.value = value;
	}

	public String getValue() {

		return value;
	}

	/**
	 * return the enum constant whose {@link #getValue() value} is the same as the given value.
	 * 
	 * @param value
	 *            the string value, or null
	 * 
	 * @return the corresponding enum constant or null if the given value was null
	 * 
	 * @throws IllegalArgumentException
	 *             if the given value does not correspond to any enum constant
	 */
	public static PluginDescriptorKind fromValue(String value) throws IllegalArgumentException {

		if(value == null) {
			return null;
		}
		for(PluginDescriptorKind e : values()) {
			if(e.getValue().equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException(value);
	}
}
