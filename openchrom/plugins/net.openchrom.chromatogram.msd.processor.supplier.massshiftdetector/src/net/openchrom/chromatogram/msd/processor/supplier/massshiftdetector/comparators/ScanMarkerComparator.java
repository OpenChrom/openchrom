/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.comparators;

import java.util.Comparator;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ScanMarker;

public class ScanMarkerComparator implements Comparator<ScanMarker> {

	@Override
	public int compare(ScanMarker o1, ScanMarker o2) {

		if(o1 != null && o2 != null) {
			return Integer.compare(o2.getScan(), o1.getScan());
		}
		return 0;
	}
}
