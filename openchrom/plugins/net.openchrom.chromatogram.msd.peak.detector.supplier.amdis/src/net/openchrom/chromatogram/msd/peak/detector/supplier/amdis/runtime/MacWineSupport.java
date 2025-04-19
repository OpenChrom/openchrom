/*******************************************************************************
 * Copyright (c) 2014, 2025 Lablicate GmbH.
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

import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.chemclipse.support.runtime.AbstractMacWineSupport;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences.PreferenceSupplier;

public class MacWineSupport extends AbstractMacWineSupport implements IExtendedRuntimeSupport {

	private IAmdisSupport amdisSupport;

	public MacWineSupport(String application, List<String> parameters) throws FileNotFoundException {

		super(application, parameters, PreferenceSupplier.getMacWineBinary());
		amdisSupport = new AmdisSupport(this);
	}

	@Override
	public int getSleepMillisecondsBeforeExecuteRunCommand() {

		/*
		 * I've recognized that the e.g. NIST-DB sometimes don't start.
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
}
