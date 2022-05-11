/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.support;

import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;

public class RetentionIndexSupport {

	public static RetentionIndexMap getRetentionIndexMap(List<? extends IPeak> peaks) {

		RetentionIndexMap retentionIndexMap = new RetentionIndexMap();
		IChromatogram<?> chromatogram = getChromatogram(peaks);
		if(chromatogram != null) {
			retentionIndexMap.update(chromatogram);
		}
		//
		return retentionIndexMap;
	}

	private static IChromatogram<?> getChromatogram(List<? extends IPeak> peaks) {

		for(IPeak peak : peaks) {
			if(peak instanceof IChromatogramPeak) {
				IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
				return chromatogramPeak.getChromatogram();
			}
		}
		//
		return null;
	}
}