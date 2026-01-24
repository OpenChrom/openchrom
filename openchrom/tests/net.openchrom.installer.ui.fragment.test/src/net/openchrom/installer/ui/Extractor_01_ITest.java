/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.installer.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@TestInstance(Lifecycle.PER_CLASS)
public class Extractor_01_ITest {

	@BeforeAll
	public void setUp() throws IOException {

	}

	@Test
	public void testExtract() {

		try {
			Bundle bundle = FrameworkUtil.getBundle(getClass());
			IPath path = new Path(File.separator + "plugin.xml");
			URL url = FileLocator.find(bundle, path, null);
			File file = new File(FileLocator.resolve(url).getPath());
			PluginFileExtractor pluginFileExtractor = new PluginFileExtractor();
			String content = pluginFileExtractor.extract(file);
			assertTrue(content.length() > 0);
		} catch(IOException e) {
			assertTrue(false);
		}
	}

}