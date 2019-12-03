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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
import org.eclipse.chemclipse.nmr.model.core.FilteredFIDMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.settings.ZeroFillingSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

@Component(service = { Filter.class, IMeasurementFilter.class })
public class ZeroFillingProcessor extends AbstractFIDSignalFilter<ZeroFillingSettings> {

	private static final long serialVersionUID = -7190114750655302768L;
	private static final String FILTER_NAME = "Zero Filling";

	public ZeroFillingProcessor(){
		super(ZeroFillingSettings.class);
	}

	@Override
	public String getName() {

		return FILTER_NAME;
	}

	@Override
	protected IMeasurement doFiltering(FilterContext<FIDMeasurement, ZeroFillingSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		List<? extends FIDSignal> signals = context.getFilteredObject().getSignals();
		int signalsize = signals.size();
		if(signalsize < 2) {
			messageConsumer.addErrorMessage(getName(), "At least two datapoints are requred for zerofilling");
			return null;
		}
		ZeroFillingFactor zeroFillingFactor = context.getFilterConfig().getZeroFillingFactor();
		FilteredFIDMeasurement<ZeroFillingSettings> fidMeasurement = new FilteredFIDMeasurement<>(context);
		int zeroFillLength = getZeroFillLength(signalsize, zeroFillingFactor);
		if(zeroFillLength == signalsize) {
			return fidMeasurement;
		}
		List<FIDSignal> newSignals = new ArrayList<>(zeroFillLength);
		newSignals.addAll(signals);
		BigDecimal step = signals.get(1).getSignalTime().subtract(signals.get(0).getSignalTime());
		for(int i = signalsize; i < zeroFillLength; i++) {
			newSignals.add(new ZeroFIDSignal(BigDecimal.valueOf(i).multiply(step)));
		}
		fidMeasurement.setSignals(newSignals);
		return fidMeasurement;
	}

	private static final class ZeroFIDSignal implements FIDSignal {

		private BigDecimal time;

		public ZeroFIDSignal(BigDecimal time){
			this.time = time;
		}

		@Override
		public BigDecimal getSignalTime() {

			return time;
		}

		@Override
		public Number getRealComponent() {

			return BigDecimal.ZERO;
		}

		@Override
		public Number getImaginaryComponent() {

			return BigDecimal.ZERO;
		}
	}

	public static Complex[] fill(Complex[] signals) {

		return fill(signals, ZeroFillingFactor.AUTO);
	}

	public static Complex[] fill(Complex[] signals, ZeroFillingFactor factor) {

		if(signals == null) {
			throw new IllegalArgumentException("Signals can't be null");
		}
		int newLength = getZeroFillLength(signals.length, factor);
		if(newLength == signals.length) {
			return signals;
		}
		Complex[] copyOf = Arrays.copyOf(signals, newLength);
		Arrays.fill(copyOf, signals.length, newLength, Complex.ZERO);
		return copyOf;
	}

	public static int getZeroFillLength(int currentLenth, ZeroFillingFactor factor) {

		if(currentLenth == 0) {
			throw new IllegalArgumentException("Current length can't be 0");
		}
		int lowerBound = factor == ZeroFillingFactor.AUTO ? 2 : factor.getValue();
		if(lowerBound >= currentLenth) {
			return lowerBound;
		}
		int value = 1;
		while (value <= currentLenth) {
			value = value << 1;
		}
		return value;
	}
}
