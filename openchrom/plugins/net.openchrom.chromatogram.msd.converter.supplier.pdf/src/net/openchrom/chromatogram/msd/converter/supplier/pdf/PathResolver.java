/*******************************************************************************
 * Copyright (c) 2012, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf;

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
	 * @return String absolutePath
	 */
	public static String getAbsolutePath(String string) {

		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		IPath path = new Path(string);
		URL url = FileLocator.find(bundle, path, null);
		try {
			return FileLocator.resolve(url).getPath().toString();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
