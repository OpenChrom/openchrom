/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.ms.converter.supplier.cdf;

import java.io.IOException;
import java.net.URL;

import net.openchrom.chromatogram.ms.converter.supplier.cdf.Activator;

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
