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
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;

import net.openchrom.msd.converter.supplier.mgf.IMGFVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.mgf.MGFVendorLibraryMassSpectrum;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerMGFElementIVendorLibraryMassSpectrum extends AbstractTransformingListFactory<MGFElement, IMGFVendorLibraryMassSpectrum> {

	private static final Logger log = Logger.getLogger(TransformerMGFElementIVendorLibraryMassSpectrum.class);
	private final TransformerPeakIon transformer = new TransformerPeakIon();

	@Override
	public IMGFVendorLibraryMassSpectrum transform(final MGFElement element) {

		final IMGFVendorLibraryMassSpectrum result = new MGFVendorLibraryMassSpectrum();
		result.setIdentifier(element.getTitle());
		try {
			final double retentionTimeInSeconds = Double.parseDouble(element.getElement(MGFElement.Identifier.RTINSECONDS));
			final double scale = retentionTimeInSeconds * 1000;
			result.setRetentionTime((int)Math.round(scale));
		} catch(final NumberFormatException e) {
			// if(log.isDebugEnabled()) {
			log.debug("No retention time available");
			// }
		}
		for(final Peak p : element.getPeaks()) {
			try {
				result.addIon(transformer.transform(p));
			} catch(final IonLimitExceededException e) {
				log.warn(e.getLocalizedMessage(), e);
			} catch(final AbundanceLimitExceededException e) {
				log.warn(e.getLocalizedMessage(), e);
			}
		}
		return result;
	}
}
