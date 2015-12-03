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

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

public class TransformerPeakIon {

	public IIon transform(Peak peak) throws IonLimitExceededException, AbundanceLimitExceededException {

		Ion result = new Ion(peak.getMz());
		result.setAbundance((float)peak.getIntensity());
		return result;
	}
}
