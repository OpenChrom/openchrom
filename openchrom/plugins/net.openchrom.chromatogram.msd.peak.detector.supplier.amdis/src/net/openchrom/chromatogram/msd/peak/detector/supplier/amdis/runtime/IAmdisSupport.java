/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.runtime;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.IOnsiteSettings;

public interface IAmdisSupport {

	String ONSITE_INI_FILE = "ONSITE.INI";
	/*
	 * The parameter is used to run the AMDIS
	 * deconvolution in batch mode.
	 */
	String PARAMETER = "/S"; // Simple Analysis
	/*
	 * Identifier for the AMDIS executable. It will be used to avoid killing other processes than the AMDIS32$.exe.
	 */
	String AMDIS_EXE_IDENTIFIER_LC = "amdis";

	/**
	 * Modifies the onsite.ini
	 * and stores the needed settings.
	 */
	void modifySettings(IOnsiteSettings onsiteSettings);

	/**
	 * Validates the AMDIS executable;
	 * 
	 * @return boolean
	 */
	boolean validateExecutable();
}
