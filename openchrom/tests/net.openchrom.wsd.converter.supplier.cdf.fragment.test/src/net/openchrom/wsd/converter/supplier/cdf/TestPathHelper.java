/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.cdf;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestPathHelper extends PathResolver {

	public static final String HP_CH = "testData/Hewlett Packard/HP-CH.CDF";

	public static final String ACID = "testData/PerkinElmer/ACID.CDF";

	public static final String VARIAN1 = "testData/Varian/VARIAN1.CDF";
	public static final String VARIAN2 = "testData/Varian/VARIAN2.CDF";
	public static final String VARIAN3 = "testData/Varian/VARIAN3.CDF";
	public static final String VARIAN4 = "testData/Varian/VARIAN4.CDF";

	public static final String WAT_490 = "testData/Waters/WAT_490.CDF";
	public static final String WAT_9962 = "testData/Waters/WAT_9962.CDF";

	public static final String AAA250PM = "testData/AAA250PM.CDF";
	public static final String ICI_21_2 = "testData/ICI_21_2.CDF";
	public static final String SPA = "testData/SPA.CDF";
	public static final String STEROIDS = "testData/STEROIDS.CDF";
}
