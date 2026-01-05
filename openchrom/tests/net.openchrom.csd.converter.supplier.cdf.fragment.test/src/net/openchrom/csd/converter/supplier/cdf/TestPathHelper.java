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
package net.openchrom.csd.converter.supplier.cdf;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestPathHelper extends PathResolver {

	public static final String DIONEX = "testData/Dionex/DIONEX.CDF";
	public static final String DNX_7AN = "testData/Dionex/DNX-7AN.CDF";

	public static final String SOLV001 = "testData/PerkinElmer/SOLV001.CDF";

	public static final String CLASS10 = "testData/Shimadzu/CLASS10.CDF"; // actually LC
	public static final String CLASSVP = "testData/Shimadzu/CLASSVP.CDF"; // actually LC
	public static final String SHIMADZU = "testData/Shimadzu/SHIMADZU.CDF"; // optical detector
	public static final String SHMDZU1 = "testData/Shimadzu/SHMDZU1.CDF"; // optical detector

	public static final String TGNTHPGC = "testData/Thru-Put Systems/tgnthpgc.cdf";
	public static final String TGNTHPLAS = "testData/Thru-Put Systems/tgnthplas.cdf";
	public static final String TGNTPE41 = "testData/Thru-Put Systems/tgntpe41.cdf";

	public static final String WAT_MS2D = "testData/Waters/WAT_MS2D.CDF";
	public static final String WATERS4 = "testData/Waters/WATERS4.CDF";

	public static final String CAL_3 = "testData/CAL_3.CDF";
	public static final String EXAMPLE = "testData/EXAMPLE.CDF";
	public static final String EXAMPLE1 = "testData/EXAMPLE1.CDF";
	public static final String PCB = "testData/PCB.CDF";
	public static final String PK_SUM01N01 = "testData/PK-SUM01N01.CDF";
}
