/*******************************************************************************
 * Copyright (c) 2009, 2025 Tasktop Technologies, Polarion Software and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

import java.net.URL;

/**
 * @author David Green
 * @author Igor Burilo
 */
public interface IDiscoverySource {

	/**
	 * an identifier that can be used to determine the origin of the source, typically for logging purposes.
	 */
	public Object getId();

	/**
	 * get a resource by an URL relative to the root of the source.
	 * 
	 * @param relativeUrl
	 *            the relative resource name
	 * @return an URL to the resource, or null if it is known that the resource does not exist.
	 */
	public URL getResource(String resourceName);
}
