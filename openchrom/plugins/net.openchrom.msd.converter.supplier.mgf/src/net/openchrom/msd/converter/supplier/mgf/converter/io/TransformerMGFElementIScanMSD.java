/*******************************************************************************
 * Copyright (c) 2015 alex.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * alex - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;

class TransformerMGFElementIScanMSD {

	private final TransformerPeakIon transformer = new TransformerPeakIon();

	IScanMSD transform(MGFElement element) throws IonLimitExceededException, AbundanceLimitExceededException {

		ScanMSD result = new ScanMSD();
		result.setIdentifier(element.getTitle());
		result.setRetentionTime(element.getRetentionTimeInSeconds());
		result.setRetentionIndex(element.getPeaks().get(0).getFractionIndex());
		for(Peak p : element.getPeaks()) {
			result.addIon(transformer.transform(p));
		}
		return result;
	}
}
