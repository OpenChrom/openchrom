/*******************************************************************************
 * Copyright (c) 2014, 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.runtime.IRuntimeSupport;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.internal.identifier.PathHelper;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.IOnsiteSettings;

public class AmdisSupport implements IAmdisSupport {

	private static final Logger logger = Logger.getLogger(AmdisSupport.class);
	private IRuntimeSupport runtimeSupport;

	public AmdisSupport(IRuntimeSupport runtimeSupport) {

		this.runtimeSupport = runtimeSupport;
	}

	@Override
	public void modifySettings(IOnsiteSettings onsiteSettings) {

		try {
			File nistSettings = new File(getOnsiteIniFile());
			File nistSettingsTmp = new File(PathHelper.getStoragePath() + File.separator + IAmdisSupport.ONSITE_INI_FILE);
			try (FileReader fileReader = new FileReader(nistSettings);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					PrintWriter printWriter = new PrintWriter(nistSettingsTmp)) {
				String line;
				while((line = bufferedReader.readLine()) != null) {
					/*
					 * Get the original or the modified line.
					 */
					printWriter.println(onsiteSettings.getLine(line));
				}
				printWriter.flush();
			}
			/*
			 * Copy the temporary file.
			 */
			try (FileReader in = new FileReader(nistSettingsTmp);
					FileWriter out = new FileWriter(nistSettings)) {
				int b;
				while((b = in.read()) != -1) {
					out.write(b);
				}
				out.flush();
			}
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	@Override
	public boolean validateExecutable() {

		return runtimeSupport.getApplicationExecutable().toLowerCase().startsWith(AMDIS_EXE_IDENTIFIER_LC);
	}

	private String getOnsiteIniFile() {

		return runtimeSupport.getApplicationPath() + File.separator + IAmdisSupport.ONSITE_INI_FILE;
	}
}
