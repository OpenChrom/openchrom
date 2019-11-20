/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 * Christoph Läubrich - complete rework of filter
 *******************************************************************************/
package net.openchrom.nmr.processing.apodization;

import java.math.BigDecimal;

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
import net.openchrom.nmr.processing.supplier.base.settings.ExponentialApodizationSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class ExponentialApodizationFunctionProcessor extends AbstractFIDSignalFilter<ExponentialApodizationSettings> {

	private static final long serialVersionUID = -2227392287932085043L;
	private static final String FILTER_NAME = "Exponential Apodization";
	private static final BigDecimal PI = BigDecimal.valueOf(Math.PI);

	public ExponentialApodizationFunctionProcessor() {
		super(ExponentialApodizationSettings.class);
	}

	@Override
	public String getName() {

		return FILTER_NAME;
	}

	@Override
	protected IMeasurement doFiltering(FilterContext<FIDMeasurement, ExponentialApodizationSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		double broadeningFactor = context.getFilterConfig().getExponentialLineBroadeningFactor();
		if(broadeningFactor > 0 || broadeningFactor < 0) {
			BigDecimal factor = BigDecimal.valueOf(broadeningFactor);
			FilteredFIDMeasurement<ExponentialApodizationSettings> fidMeasurement = new FilteredFIDMeasurement<ExponentialApodizationSettings>(context);
			ComplexFIDData data = UtilityFunctions.toComplexFIDData(context.getFilteredObject().getSignals());
			for(int i = 0; i < data.times.length; i++) {
				BigDecimal time = data.times[i];
				// Lbfunc=exp(-Timescale'*pi*NmrData.lb);
				BigDecimal exponentialLineBroadenigTerm = time.negate().multiply(PI).multiply(factor);
				double exponentialLineBroadening = Math.exp(exponentialLineBroadenigTerm.doubleValue());
				data.signals[i] = data.signals[i].multiply(exponentialLineBroadening);
			}
			fidMeasurement.setSignals(data.toSignal());
			return fidMeasurement;
		} else {
			messageConsumer.addWarnMessage(FILTER_NAME, "ExponentialLineBroadeningFactor mut be greater or smaller than zero, skipped filtering");
			return null;
		}
	}
}
