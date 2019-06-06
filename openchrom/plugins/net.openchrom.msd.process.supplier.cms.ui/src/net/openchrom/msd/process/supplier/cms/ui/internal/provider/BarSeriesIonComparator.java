/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.util.Comparator;

public class BarSeriesIonComparator implements Comparator<BarSeriesIon> {

	@Override
	public int compare(BarSeriesIon barSeriesIon1, BarSeriesIon barSeriesIon2) {

		return Double.compare(barSeriesIon2.getIntensity(), barSeriesIon1.getIntensity());
	}
}
