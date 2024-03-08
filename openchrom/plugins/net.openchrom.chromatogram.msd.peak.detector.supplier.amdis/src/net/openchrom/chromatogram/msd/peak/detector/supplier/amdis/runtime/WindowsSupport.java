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
import java.io.IOException;
import java.util.List;

import org.eclipse.chemclipse.support.runtime.AbstractWindowsSupport;

public class WindowsSupport extends AbstractWindowsSupport implements IExtendedRuntimeSupport {

	private IAmdisSupport amdisSupport;

	public WindowsSupport(String application, List<String> parameters) throws FileNotFoundException {

		super(application, parameters);
		amdisSupport = new AmdisSupport(this);
	}

	@Override
	public boolean isValidApplicationExecutable() {

		return amdisSupport.validateExecutable();
	}

	@Override
	public IAmdisSupport getAmdisSupport() {

		return amdisSupport;
	}

	@Override
	public Process executeOpenCommand() throws IOException {

		return getOpenCommand().start();
	}

	@Override
	public Process executeKillCommand() throws IOException {

		return getKillCommand().start();
	}

	private ProcessBuilder getOpenCommand() {

		/*
		 * Returns e.g.: "C:\Programs\NIST\AMDIS32\AMDIS32$.exe
		 */
		String amdis = getApplication().replace("AMDIS32$.exe", "AMDIS_32.exe"); // run the GUI version
		return new ProcessBuilder(amdis);
	}

	private ProcessBuilder getKillCommand() {

		if(isValidApplicationExecutable()) {
			/*
			 * taskkill kills the e.g. AMDIS application.
			 */
			return new ProcessBuilder("taskkill", "/f", "/IM", "AMDIS_32.exe");
		}
		return new ProcessBuilder();
	}
}
