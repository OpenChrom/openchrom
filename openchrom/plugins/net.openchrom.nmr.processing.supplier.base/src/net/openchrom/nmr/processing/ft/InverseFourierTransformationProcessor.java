/*******************************************************************************
 * Copyright (c) 2019 Alexander Stark.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.ft;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.AcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.DataDimension;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractSpectrumSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.settings.InverseFourierTransformationSettings;

@Component(service = { Filter.class, IMeasurementFilter.class })
public class InverseFourierTransformationProcessor extends AbstractSpectrumSignalFilter<InverseFourierTransformationSettings> {

	private static final long serialVersionUID = 2343292442585806324L;
	private static final String NAME = "Inverse Fourier Transformation";

	@Override
	public String getName() {

		return NAME;
	}

	public InverseFourierTransformationProcessor(){
		super(InverseFourierTransformationSettings.class);
	}

	@Override
	protected IMeasurement doFiltering(FilterContext<SpectrumMeasurement, InverseFourierTransformationSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		// IFFT spectrum to fid
		SpectrumMeasurement spectrumMeasurement = context.getFilteredObject();
		Complex[] spectrumSignals = UtilityFunctions.toComplexArray(spectrumMeasurement.getSignals());
		UtilityFunctions.rightShiftNMRComplexData(spectrumSignals, spectrumSignals.length / 2);
		Complex[] fid = inverseFourierTransformNmrData(spectrumSignals);
		BigDecimal max = spectrumMeasurement.getAcquisitionParameter().getAcquisitionTime();
		BigDecimal step = max.divide(BigDecimal.valueOf((fid.length) - 1).setScale(10), RoundingMode.HALF_UP);
		List<InverseFFTSpectrumSignal> signals = new ArrayList<>();
		for(int i = 0; i < spectrumMeasurement.getAcquisitionParameter().getNumberOfPoints(); i++) {
			/*
			 * API requires values from high to low after doing the forward fourier
			 * transformation. So to display the fid after the inverse fourier
			 * transformation the order of values has to be restored.
			 */
			int reversed_index = fid.length - 1 - i;
			signals.add(new InverseFFTSpectrumSignal(BigDecimal.valueOf(i).multiply(step), fid[reversed_index]));
		}
		return new InverseFFTFilteredMeasurement(context, signals);
	}

	private static Complex[] inverseFourierTransformNmrData(Complex[] spectrumSignals) {

		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] fid = fFourierTransformer.transform(spectrumSignals, TransformType.INVERSE);
		return fid;
	}

	private static final class InverseFFTFilteredMeasurement extends FilteredMeasurement<SpectrumMeasurement, InverseFourierTransformationSettings> implements FIDMeasurement {

		private static final long serialVersionUID = -3240032383041201512L;
		private List<InverseFFTSpectrumSignal> signals;

		public InverseFFTFilteredMeasurement(FilterContext<SpectrumMeasurement, InverseFourierTransformationSettings> filterContext, List<InverseFFTSpectrumSignal> signals){
			super(filterContext);
			this.signals = Collections.unmodifiableList(signals);
		}

		@Override
		public List<InverseFFTSpectrumSignal> getSignals() {

			return signals;
		}

		@Override
		public DataDimension getDataDimension() {

			return DataDimension.ONE_DIMENSIONAL;
		}

		@Override
		public AcquisitionParameter getAcquisitionParameter() {

			return getFilteredObject().getAcquisitionParameter();
		}
	}

	private static final class InverseFFTSpectrumSignal implements FIDSignal, Serializable {

		private static final long serialVersionUID = -8417725272449006279L;
		private BigDecimal time;
		private Complex complex;

		public InverseFFTSpectrumSignal(BigDecimal time, Complex complex){
			this.time = time;
			this.complex = complex;
		}

		@Override
		public BigDecimal getSignalTime() {

			return time;
		}

		@Override
		public Number getRealComponent() {

			return complex.getReal();
		}

		@Override
		public Number getImaginaryComponent() {

			return complex.getImaginary();
		}
	}
}
