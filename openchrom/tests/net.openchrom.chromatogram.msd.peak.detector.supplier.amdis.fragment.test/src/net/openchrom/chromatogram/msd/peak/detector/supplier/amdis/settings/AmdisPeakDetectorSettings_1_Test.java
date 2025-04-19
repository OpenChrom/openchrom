/*******************************************************************************
 * Copyright (c) 2008, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings;

import junit.framework.TestCase;

public class AmdisPeakDetectorSettings_1_Test extends TestCase {

	private SettingsAMDIS settings;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		settings = new SettingsAMDIS();
	}

	@Override
	protected void tearDown() throws Exception {

		settings = null;
		super.tearDown();
	}

	public void test_1() {

		assertNotNull(settings.getOnsiteSettings());
	}
}
