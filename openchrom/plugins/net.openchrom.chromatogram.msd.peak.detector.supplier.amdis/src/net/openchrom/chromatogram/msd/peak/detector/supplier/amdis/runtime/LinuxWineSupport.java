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
import java.util.Map;

import org.eclipse.chemclipse.support.runtime.AbstractLinuxWineSupport;

public class LinuxWineSupport extends AbstractLinuxWineSupport implements IExtendedRuntimeSupport {

	private IAmdisSupport amdisSupport;

	public LinuxWineSupport(String application, List<String> parameters) throws FileNotFoundException {

		super(application, parameters);
		amdisSupport = new AmdisSupport(this);
	}

	@Override
	public int getSleepMillisecondsBeforeExecuteRunCommand() {

		/*
		 * Sometimes NIST-DB doesn't start.
		 * Does a sleep time preventing this?
		 */
		return 4000;
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
	public Process executeKillCommand() throws IOException {

		return getKillCommand().start();
	}

	@Override
	public Process executeOpenCommand() throws IOException {

		return getOpenCommand().start();
	}

	private ProcessBuilder getKillCommand() {

		return new ProcessBuilder("pkill", "-f", "AMDIS");
	}

	private ProcessBuilder getOpenCommand() {

		/*
		 * "env WINEPREFIX=/home/openchrom/.wine wine start C:\\programme\\nist\\AMDIS32-271\\AMDIS32$.exe"
		 */
		String amdis = getWineApplication().replace("AMDIS32$.exe", "AMDIS_32.exe"); // run the GUI version
		ProcessBuilder processBuilder = new ProcessBuilder("wine", "start", amdis);
		Map<String, String> environment = processBuilder.environment();
		environment.put("WINEPREFIX", getWineEnvironment());
		return processBuilder;
	}
}
