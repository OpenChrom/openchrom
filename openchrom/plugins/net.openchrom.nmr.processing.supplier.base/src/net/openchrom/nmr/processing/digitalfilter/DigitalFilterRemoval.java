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
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FilteredFIDMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractFIDSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.ComplexFIDData;
import net.openchrom.nmr.processing.supplier.base.settings.DigitalFilterRemovalSettings;

@Component(service = { Filter.class, IMeasurementFilter.class })
public class DigitalFilterRemoval extends AbstractFIDSignalFilter<DigitalFilterRemovalSettings> implements Serializable {

	private static final long serialVersionUID = 4481720817522447928L;
	private static final String FILTER_NAME = "Digital Filter Removal";
	private static final String MARKER = DigitalFilterRemoval.class.getName() + ".filtered";

	public DigitalFilterRemoval(){
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
	protected IMeasurement doFiltering(FilterContext<FIDMeasurement, DigitalFilterRemovalSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		DigitalFilterRemovalSettings config = context.getFilterConfig();
		double multiplicationFactor = config.getDcOffsetMultiplicationFactor();
		int leftRotationFid = config.getLeftRotationFid();
		if(Double.isNaN(leftRotationFid) && Double.isNaN(multiplicationFactor)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No Left Rotation value and no DC offset specified, skipp processing");
			return null;
		}
		FilteredFIDMeasurement<DigitalFilterRemovalSettings> filteredFIDMeasurement = new FilteredFIDMeasurement<DigitalFilterRemovalSettings>(context);
		ComplexFIDData fidData = UtilityFunctions.toComplexFIDData(context.getFilteredObject().getSignals());
		if(Double.isNaN(multiplicationFactor)) {
			messageConsumer.addInfoMessage(FILTER_NAME, "No DC Offset to remove");
		} else {
			fidData.signals[0] = fidData.signals[0].multiply(multiplicationFactor);
			messageConsumer.addInfoMessage(FILTER_NAME, "DC Offset was removed");
		}
		if(Math.abs(leftRotationFid) > 0) {
			int treatmentOption = 0;
			switch (config.getTreatmentOptions()) {
			case SHIFT_ONLY:
				treatmentOption = 1;
				break;
			case SUBSTITUTE_WITH_ZERO:
				treatmentOption = 2;
				break;
			case SUBSTITUTE_WITH_NOISE:
				treatmentOption = 3;
				break;
			default:
				throw new IllegalArgumentException("unsupported TreatmentOptions: " + config.getTreatmentOptions());
			}
			DigitalFilterRemoval.removeDigitalFilter(fidData, leftRotationFid, treatmentOption, messageConsumer);
			messageConsumer.addInfoMessage(FILTER_NAME, "Digital Filter was removed");
		} else {
			messageConsumer.addWarnMessage(FILTER_NAME, "Left Rotation value must be greater than zero, skipp processing");
		}
		filteredFIDMeasurement.putHeaderData(MARKER, "true");
		filteredFIDMeasurement.setSignals(fidData.toSignal());
		return filteredFIDMeasurement;
	}

	private static void removeDigitalFilter(ComplexFIDData fidData, int leftRotationFid, int treatmentOption, MessageConsumer messageConsumer) {

		Complex[] digitalFilter = Arrays.copyOfRange(fidData.signals, 0, leftRotationFid);
		Complex[] analogFID = Arrays.copyOfRange(fidData.signals, leftRotationFid, fidData.signals.length);
		if(treatmentOption == 2) {
			// overwrite w/ zero
			Arrays.fill(digitalFilter, Complex.ZERO);
		} else if(treatmentOption == 3) {
			// overwrite w/ noise
			digitalFilter = Arrays.copyOfRange(fidData.signals, fidData.signals.length - leftRotationFid, fidData.signals.length);
		}
		Complex[] unfilteredFID = ArrayUtils.addAll(analogFID, digitalFilter);
		for(int i = 0; i < fidData.signals.length; i++) {
			fidData.signals[i] = unfilteredFID[i];
		}
	}
}
