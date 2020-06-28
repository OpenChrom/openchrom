/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.Set;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;

public class PeakSupport {

	public static boolean isPeakRelevant(IPeak peak, Set<Integer> traces) {

		boolean isPeakRelevant = true;
		//
		if(peak instanceof IPeakMSD) {
			IPeakMSD peakMSD = (IPeakMSD)peak;
			IScanMSD scanMSD = peakMSD.getPeakModel().getPeakMassSpectrum();
			IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
			exitloop:
			for(int trace : traces) {
				float abundance = extractedIonSignal.getAbundance(trace);
				if(abundance == 0) {
					isPeakRelevant = false;
					break exitloop;
				}
			}
		}
		//
		return isPeakRelevant;
	}
}
