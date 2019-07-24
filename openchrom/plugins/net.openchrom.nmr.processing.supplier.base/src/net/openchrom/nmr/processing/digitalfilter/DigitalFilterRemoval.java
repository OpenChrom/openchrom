/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Christoph Läubrich - Change to use a more generic API, cleanup the code and streamline the algorithm flow
 *******************************************************************************/
package net.openchrom.nmr.processing.digitalfilter;

import java.io.Serializable;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractSpectrumSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class DigitalFilterRemoval extends AbstractSpectrumSignalFilter<DigitalFilterRemovalSettings> implements Serializable {

	private static final long serialVersionUID = -3085947876861708195L;
	private static final String FILTER_NAME = "Digital Filter Removal";
	private static final String MARKER = DigitalFilterRemoval.class.getName() + ".filtered";

	public DigitalFilterRemoval() {

		super(DigitalFilterRemovalSettings.class);
	}

	@Override
	protected boolean accepts(SpectrumMeasurement item) {

		return item.getHeaderData(MARKER) == null;
	}

	@Override
	public String getName() {

		return FILTER_NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(SpectrumMeasurement measurement, DigitalFilterRemovalSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		double multiplicationFactor = settings.getDcOffsetMultiplicationFactor();
		double leftRotationFid = settings.getLeftRotationFid();
		if(Double.isNaN(leftRotationFid) && Double.isNaN(multiplicationFactor)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No Left Rotation value and no DC offset specified, skipp processing");
			return null;
		}
		FilteredSpectrumMeasurement filteredMeasurement = new FilteredSpectrumMeasurement(measurement);
		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(filteredMeasurement.getSignals());
		if(Double.isNaN(multiplicationFactor)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No DC Offset to remove");
		} else {
			spectrumData.signals[0] = spectrumData.signals[0].multiply(multiplicationFactor);
			messageConsumer.addInfoMessage(FILTER_NAME, "DC Offset was removed");
		}
		if(Double.isNaN(leftRotationFid)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No left rotation value was given, skipp processing");
		} else if(Math.abs(leftRotationFid) > 0.0) {
			DigitalFilterRemoval.removeDigitalFilter(spectrumData, leftRotationFid, messageConsumer);
			messageConsumer.addInfoMessage(FILTER_NAME, "Digital FIlter was removed");
		} else {
			messageConsumer.addWarnMessage(FILTER_NAME, "Left Rotation value must be greater than zero, skipp processing");
		}
		filteredMeasurement.putHeaderData(MARKER, "true");
		filteredMeasurement.setSignals(spectrumData.toSignal());
		return filteredMeasurement;
	}

	private static void removeDigitalFilter(SpectrumData spectrumData, double leftRotationFid, MessageConsumer messageConsumer) {

		// create filtered spectrum
		Complex[] unfilteredNMRSpectrum = new Complex[spectrumData.signals.length];
		double digitalFilterFactor = 0;
		int spectrumSize = spectrumData.signals.length;
		Complex complexFactor = new Complex(-0.0, -1.0);
		// remove the filter!
		for(int i = 0; i < spectrumSize; i++) {
			double filterTermA = (double)i / spectrumSize;
			double filterTermB = Math.floor(spectrumSize / 2);
			digitalFilterFactor = filterTermB - filterTermA;
			Complex exponentialFactor = complexFactor.multiply(leftRotationFid * 2 * Math.PI * digitalFilterFactor);
			unfilteredNMRSpectrum[i] = spectrumData.signals[i].multiply(exponentialFactor.exp());
		}
		// copy transformed data back to datastructure
		for(int i = 0; i < spectrumData.signals.length; i++) {
			spectrumData.signals[i] = unfilteredNMRSpectrum[i];
		}
	}
}
