/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 * Christoph Läubrich - complete rework
 * Philip Wenig - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.apodization;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
import net.openchrom.nmr.processing.supplier.base.settings.GaussianApodizationSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class GaussianApodizationFunctionProcessor extends AbstractFIDSignalFilter<GaussianApodizationSettings> {

	private static final long serialVersionUID = 4474164198521021220L;
	//
	private static final String FILTER_NAME = "Gaussian Apodization";
	private static final BigDecimal PI = BigDecimal.valueOf(Math.PI);
	private static final BigDecimal GAUS_CONSTANT = BigDecimal.valueOf(2).setScale(10).multiply(BigDecimal.valueOf(Math.sqrt(Math.log(2))));

	public GaussianApodizationFunctionProcessor() {

		super(GaussianApodizationSettings.class);
	}

	@Override
	public String getName() {

		return FILTER_NAME;
	}

	@Override
	protected IMeasurement doFiltering(FilterContext<FIDMeasurement, GaussianApodizationSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		double gaussianLineBroadeningFactor = context.getFilterConfig().getGaussianLineBroadeningFactor();
		if(gaussianLineBroadeningFactor > 0) {
			// gf=2*sqrt(log(2))/(pi*NmrData.gw);
			BigDecimal gaussFactor = GAUS_CONSTANT.divide(PI.multiply(BigDecimal.valueOf(gaussianLineBroadeningFactor)), RoundingMode.HALF_UP);
			FilteredFIDMeasurement<GaussianApodizationSettings> fidMeasurement = new FilteredFIDMeasurement<GaussianApodizationSettings>(context);
			ComplexFIDData data = UtilityFunctions.toComplexFIDData(context.getFilteredObject().getSignals());
			for(int i = 0; i < data.times.length; i++) {
				BigDecimal time = data.times[i];
				// Gwfunc=exp(-(Timescale'/gf).^2);
				BigDecimal gaussianLineBroadenigTerm = time.divide(gaussFactor, RoundingMode.HALF_UP).pow(2).negate();
				double factor = Math.exp(gaussianLineBroadenigTerm.doubleValue());
				data.signals[i] = data.signals[i].multiply(factor);
			}
			fidMeasurement.setSignals(data.toSignal());
			return fidMeasurement;
		}
		messageConsumer.addWarnMessage(FILTER_NAME, "GaussianLineBroadeningFactor must be greater than zero, skipped filtering");
		return null;
	}
}
