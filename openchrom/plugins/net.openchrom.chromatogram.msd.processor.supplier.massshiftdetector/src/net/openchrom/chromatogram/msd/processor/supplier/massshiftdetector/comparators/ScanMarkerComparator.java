/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.comparators;

import java.util.Comparator;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;

public class ScanMarkerComparator implements Comparator<IScanMarker> {

	@Override
	public int compare(IScanMarker o1, IScanMarker o2) {

		if(o1 != null && o2 != null) {
			return Integer.compare(o2.getScanNumber(), o1.getScanNumber());
		}
		return 0;
	}
}
