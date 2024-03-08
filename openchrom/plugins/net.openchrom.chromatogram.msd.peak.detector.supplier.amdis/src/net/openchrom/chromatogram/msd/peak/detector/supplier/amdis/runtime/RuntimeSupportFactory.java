/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.runtime;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.support.settings.OperatingSystemUtils;

public class RuntimeSupportFactory {

	public static IExtendedRuntimeSupport getRuntimeSupport(String application, String chromatogram) throws FileNotFoundException {

		IExtendedRuntimeSupport runtimeSupport;
		List<String> parameters = new ArrayList<>();
		parameters.add(chromatogram);
		parameters.add(IAmdisSupport.PARAMETER);
		if(OperatingSystemUtils.isWindows()) {
			runtimeSupport = new WindowsSupport(application, parameters);
		} else if(OperatingSystemUtils.isMac()) {
			runtimeSupport = new MacWineSupport(application, parameters);
		} else {
			/*
			 * wine AMDIS32\$.exe C:\\tmp\\C2.CDF /S
			 */
			runtimeSupport = new LinuxWineSupport(application, parameters);
		}
		return runtimeSupport;
	}
}
