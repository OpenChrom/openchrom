/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.util.Comparator;

public class BarSeriesIonComparator implements Comparator<BarSeriesIon> {

	@Override
	public int compare(BarSeriesIon barSeriesIon1, BarSeriesIon barSeriesIon2) {

		return Double.compare(barSeriesIon2.getIntensity(), barSeriesIon1.getIntensity());
	}
}
