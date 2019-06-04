/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Christoph Läubrich - add general purpose static filling function
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.filter.Filter;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
import org.eclipse.chemclipse.nmr.model.core.FilteredFIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SimpleFIDSignal;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.ft.ZeroFillingSettings;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.ComplexFIDData;
import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class ZeroFilling extends AbstractFIDSignalFilter<ZeroFillingSettings> {

	private static final long serialVersionUID = -4234206196493639300L;
	private static final String FILTER_NAME = "Zero Filling";

	public ZeroFilling() {

		super(ZeroFillingSettings.class);
	}

	@Override
	public String getFilterName() {

		return FILTER_NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(FIDMeasurement measurement, ZeroFillingSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		FilteredFIDMeasurement fidMeasurement = new FilteredFIDMeasurement(measurement);
		ComplexFIDData rawFID = UtilityFunctions.toComplexFIDData(measurement.getSignals());
		//
		ZeroFillingFactor zeroFillingFactor = settings.getZeroFillingFactor();
		Complex[] zeroFilledFID = ZeroFilling.fill(rawFID.signals, zeroFillingFactor);
		//
		BigDecimal max = BigDecimal.valueOf(measurement.getAcquisitionTime());
		BigDecimal step = max.divide(BigDecimal.valueOf((zeroFilledFID.length) - 1).setScale(10), RoundingMode.HALF_UP);
		Collection<FIDSignal> newSignals = new ArrayList<>();
		for(int i = 0; i < zeroFilledFID.length; i++) {
			newSignals.add(new SimpleFIDSignal(BigDecimal.valueOf(i).multiply(step), zeroFilledFID[i].getReal(), zeroFilledFID[i].getImaginary()));
		}
		fidMeasurement.setSignals(newSignals);
		return fidMeasurement;
	}

	public static Complex[] fill(Complex[] signals) {

		return fill(signals, ZeroFillingFactor.AUTO);
	}

	public static Complex[] fill(Complex[] signals, ZeroFillingFactor factor) {

		if(signals == null) {
			throw new IllegalArgumentException("Signals can't be null");
		}
		if(signals.length == 0) {
			throw new IllegalArgumentException("Signals can't be empty");
		}
		int lowerBound = factor == ZeroFillingFactor.AUTO ? 2 : factor.getValue();
		int newLength = Math.max(lowerBound, (int)Math.pow(lowerBound, (int)(Math.ceil((Math.log(signals.length) / Math.log(lowerBound))))));
		if(newLength == signals.length) {
			return signals;
		}
		Complex[] copyOf = Arrays.copyOf(signals, newLength);
		Arrays.fill(copyOf, signals.length, newLength, Complex.ZERO);
		return copyOf;
	}
}
