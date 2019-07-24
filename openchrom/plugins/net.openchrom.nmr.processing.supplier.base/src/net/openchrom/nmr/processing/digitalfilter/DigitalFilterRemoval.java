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
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FilteredFIDMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractFIDSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.ComplexFIDData;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class DigitalFilterRemoval extends AbstractFIDSignalFilter<DigitalFilterRemovalSettings> implements Serializable {

	private static final long serialVersionUID = -5870091035488215126L;
	private static final String FILTER_NAME = "Digital Filter Removal";
	private static final String MARKER = DigitalFilterRemoval.class.getName() + ".filtered";

	public DigitalFilterRemoval() {

		super(DigitalFilterRemovalSettings.class);
	}

	@Override
	protected boolean accepts(FIDMeasurement item) {

		return item.getHeaderData(MARKER) == null;
	}

	@Override
	public String getName() {

		return FILTER_NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(FIDMeasurement measurement, DigitalFilterRemovalSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		double multiplicationFactor = settings.getDcOffsetMultiplicationFactor();
		int leftRotationFid = (int)settings.getLeftRotationFid();
		if(Double.isNaN(leftRotationFid) && Double.isNaN(multiplicationFactor)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No Left Rotation value and no DC offset specified, skipp processing");
			return null;
		}
		FilteredFIDMeasurement filteredFIDMeasurement = new FilteredFIDMeasurement(measurement);
		ComplexFIDData fidData = UtilityFunctions.toComplexFIDData(measurement.getSignals());
		if(Double.isNaN(multiplicationFactor)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No DC Offset to remove");
		} else {
			fidData.signals[0] = fidData.signals[0].multiply(multiplicationFactor);
			messageConsumer.addInfoMessage(FILTER_NAME, "DC Offset was removed");
		}
		if(Double.isNaN(leftRotationFid)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No left rotation value was given, skipp processing");
		} else if(Math.abs(leftRotationFid) > 0.0) {
			DigitalFilterRemoval.removeDigitalFilter(fidData, leftRotationFid, messageConsumer);
			messageConsumer.addInfoMessage(FILTER_NAME, "Digital FIlter was removed");
		} else {
			messageConsumer.addWarnMessage(FILTER_NAME, "Left Rotation value must be greater than zero, skipp processing");
		}
		filteredFIDMeasurement.putHeaderData(MARKER, "true");
		filteredFIDMeasurement.setSignals(fidData.toSignal());
		return filteredFIDMeasurement;
	}

	private static void removeDigitalFilter(ComplexFIDData fidData, int leftRotationFid, MessageConsumer messageConsumer) {

		Complex[] digitalFilter = Arrays.copyOfRange(fidData.signals, 0, leftRotationFid);
		Complex[] analogFID = Arrays.copyOfRange(fidData.signals, leftRotationFid, fidData.signals.length);
		Complex[] unfilteredFID = ArrayUtils.addAll(analogFID, digitalFilter);
		for(int i = 0; i < fidData.signals.length; i++) {
			fidData.signals[i] = unfilteredFID[i];
		}
	}
}
