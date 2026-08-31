/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.abif.metric;

/**
 * The metrics of a base caller. They are contributed via
 * the extension point "org.eclipse.chemclipse.model.comparisonMetrics", see
 * the plugin.xml of this plug-in.
 */
public class BaseCallerMetrics {

	public static final String ALGORITHM_ABIF = "abif";

	public static final String PHRED_QUALITY_SCORE = "abif.phred";

	private BaseCallerMetrics() {

	}
}
