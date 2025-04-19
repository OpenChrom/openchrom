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
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.filter;

import java.util.Comparator;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.core.runtime.Adapters;

public class SNRComparator<T extends IPeak> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {

		IChromatogramPeakMSD c1 = Adapters.adapt(o1, IChromatogramPeakMSD.class);
		IChromatogramPeakMSD c2 = Adapters.adapt(o2, IChromatogramPeakMSD.class);
		return Float.compare(c1.getSignalToNoiseRatio(), c2.getSignalToNoiseRatio());
	}
}
