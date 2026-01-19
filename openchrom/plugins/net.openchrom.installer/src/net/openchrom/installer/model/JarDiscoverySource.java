/*******************************************************************************
 * Copyright (c) 2009, 2026 Tasktop Technologies, Polarion Software and others.
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class JarDiscoverySource implements IDiscoverySource {

	private final String id;
	private final File jarFile;

	public JarDiscoverySource(String id, File jarFile) {

		this.id = id;
		this.jarFile = jarFile;
	}

	@Override
	public Object getId() {

		return id;
	}

	@Override
	public URL getResource(String resourceName) {

		try {
			String prefix = jarFile.toURI().toURL().toExternalForm();
			return new URI("jar:" + prefix + "!/" + URLEncoder.encode(resourceName, StandardCharsets.UTF_8)).toURL(); //$NON-NLS-1$ //$NON-NLS-2$
		} catch(MalformedURLException e) {
			throw new IllegalStateException(e);
		} catch(URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}
}
