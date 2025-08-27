/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.settings.OperatingSystemUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private static BundleContext context;

	public static BundleContext getContext() {

		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		Activator.context = bundleContext;

		if(OperatingSystemUtils.isWindows()) {
			refreshWindowsCertificateStore();
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

		Activator.context = null;
	}

	// Workaround for https://github.com/eclipse-platform/eclipse.platform/issues/1690
	private void refreshWindowsCertificateStore() throws MalformedURLException, URISyntaxException {

		refreshWindowsCertificateStore(new URI("https://openchrom.net").toURL());
		refreshWindowsCertificateStore(new URI("https://lablicate.com").toURL());
	}

	private void refreshWindowsCertificateStore(URL url) {

		ProcessBuilder powerShell = new ProcessBuilder("powershell.exe", "-Command", "Invoke-WebRequest '" + url + "'");
		powerShell.redirectErrorStream(true);
		try {
			Process process = powerShell.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				output.append(line).append(System.lineSeparator());
			}

			int exitCode = process.waitFor();
			if(exitCode != 0) {
				logger.error("Failed to refresh Windows certificate store for " + url + System.lineSeparator() + output.toString());
			} else {
				logger.info("Refreshed Windows certificate store for " + url);
			}
		} catch(IOException e) {
			logger.warn(e);
		} catch(InterruptedException e) {
			logger.warn(e);
		}
	}
}
