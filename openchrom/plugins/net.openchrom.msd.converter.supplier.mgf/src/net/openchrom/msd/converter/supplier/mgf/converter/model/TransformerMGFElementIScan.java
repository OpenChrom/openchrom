/*******************************************************************************
 * Copyright (c) 2015, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.model;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.IFragmentedIonScan;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerMGFElementIScan extends AbstractTransformingListFactory<MGFElement, IScan> {

	private final TransformerPeakIon transformer = new TransformerPeakIon();
	private final static IScanMSDFactory DEFAULT_SCAN_FACTORY = new IMGFMassSpectrumFactory();
	private IScanMSDFactory scanFactory = DEFAULT_SCAN_FACTORY;

	public synchronized IScanMSDFactory getScanFactory() {

		return scanFactory;
	}

	public synchronized TransformerMGFElementIScan setScanFactory(IScanMSDFactory scanFactory) {

		this.scanFactory = scanFactory;
		return this;
	}

	@Override
	public IScanMSD transform(final MGFElement element) {

		final IScanMSD result = getScanFactory().build();
		result.setIdentifier(element.getTitle());
		setRetentionTime(element, result);
		setPrecursorIon(element, result);
		for(final Peak p : element.getPeaks()) {
			IIon ion = transformer.transform(p);
			if(ion != null) {
				result.addIon(ion);
			}
		}
		return result;
	}

	private void setPrecursorIon(MGFElement element, IScanMSD scan) {

		String precursorIonTag = element.getTag(MGFElement.Identifier.PEPMASS);
		if(precursorIonTag != null && !precursorIonTag.equals(MGFElement.TAG_NA)) {
			String[] tags = precursorIonTag.split(" ");
			if(tags.length == 2) {
				double precursorIon = Double.parseDouble(tags[0].trim());
				if(scan instanceof IFragmentedIonScan) {
					((IFragmentedIonScan)scan).setPrecursorIon(precursorIon);
				}
			}
		}
	}

	private void setRetentionTime(final MGFElement element, IScanMSD scan) {

		String retentionTimeInSecondsTag = element.getTag(MGFElement.Identifier.RTINSECONDS);
		if(retentionTimeInSecondsTag != null && !retentionTimeInSecondsTag.equals(MGFElement.TAG_NA)) {
			double retentionTimeInSeconds = Double.parseDouble(retentionTimeInSecondsTag);
			final double scale = retentionTimeInSeconds * 1000;
			scan.setRetentionTime((int)Math.round(scale));
		}
	}
}
