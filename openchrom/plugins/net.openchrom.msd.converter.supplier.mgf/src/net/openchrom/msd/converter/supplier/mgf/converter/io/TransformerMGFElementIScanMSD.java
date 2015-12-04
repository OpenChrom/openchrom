/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;

public class TransformerMGFElementIScanMSD {

	private final TransformerPeakIon transformer = new TransformerPeakIon();
	private static final Logger logger = Logger.getLogger(TransformerMGFElementIScanMSD.class);

	public IScanMSD transform(MGFElement element) {

		ScanMSD massSpectrum = new ScanMSD();
		massSpectrum.setIdentifier(element.getTitle());
		massSpectrum.setRetentionTime(element.getRetentionTimeInSeconds());
		massSpectrum.setRetentionIndex(element.getPeaks().get(0).getFractionIndex());
		for(Peak p : element.getPeaks()) {
			try {
				massSpectrum.addIon(transformer.transform(p));
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			}
		}
		return massSpectrum;
	}
}
