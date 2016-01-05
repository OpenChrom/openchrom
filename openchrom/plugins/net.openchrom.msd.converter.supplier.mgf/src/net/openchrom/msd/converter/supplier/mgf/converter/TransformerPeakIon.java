/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerPeakIon extends AbstractTransformingListFactory<Peak, Ion> {

	private static final Logger logger = Logger.getLogger(TransformerPeakIon.class);

	@Override
	public Ion transform(Peak peak) {

		Ion result;
		try {
			result = new Ion(peak.getMz()).setAbundance((float)peak.getIntensity());
		} catch(AbundanceLimitExceededException | IonLimitExceededException e) {
			logger.warn(e.getLocalizedMessage(), e);
			return null;
		}
		return result;
	}
}
