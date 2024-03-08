/*******************************************************************************
 * Copyright (c) 2008, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class PathResolver {

	/**
	 * Returns a absolute path of the specified Folder. For example
	 * TESTDATA_IMPORT_EMPTY as an absolute Path:
	 * $PluginPath$/testData/files/EMPTY.D/DATA.MS
	 * 
	 * @param string
	 * @throws IOException
	 * @return String absolutePath
	 */
	public static String getAbsolutePath(final String string) throws IOException {

		if("".equals(string) && string == null) {
			throw new IOException();
		}
		Bundle bundle = Platform.getBundle(Activator.getContext().getBundle().getSymbolicName());
		IPath path = new Path(string);
		URL url = FileLocator.find(bundle, path, null);
		return FileLocator.resolve(url).getPath();
	}
}
