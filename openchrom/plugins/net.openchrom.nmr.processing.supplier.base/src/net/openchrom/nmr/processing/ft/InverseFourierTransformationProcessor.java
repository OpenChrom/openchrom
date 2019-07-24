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
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.AcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.DataDimension;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
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
public class InverseFourierTransformationProcessor extends AbstractSpectrumSignalFilter<InverseFourierTransformationSettings> {

	private static final long serialVersionUID = 886603297773084819L;
	private static final String NAME = "Inverse Fourier Transformation";

	@Override
	public String getName() {

		return NAME;
	}

	public InverseFourierTransformationProcessor() {
		super(InverseFourierTransformationSettings.class);
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(SpectrumMeasurement measurement, InverseFourierTransformationSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		// IFFT spectrum to fid
		FilteredSpectrumMeasurement spectrumMeasurement = new FilteredSpectrumMeasurement(measurement);
		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(spectrumMeasurement.getSignals());
		Complex[] spectrumSignals = spectrumData.signals;
		Complex[] fid = inverseFourierTransformData(spectrumSignals);
		List<InverseFFTSpectrumSignal> signals = new ArrayList<>();
		BigDecimal max = measurement.getAcquisitionParameter().getAcquisitionTime();
		BigDecimal step = max.divide(BigDecimal.valueOf((fid.length) - 1).setScale(10), RoundingMode.HALF_UP);
		for(int i = 0; i < measurement.getAcquisitionParameter().getNumberOfPoints(); i++) {
			signals.add(new InverseFFTSpectrumSignal(BigDecimal.valueOf(i).multiply(step), fid[i]));
		}
		return new InverseFFTFilteredMeasurement(measurement, signals);
	}

	public static Complex[] inverseFourierTransformData(Complex[] spectrum) {

		// ArrayUtils.reverse(spectrum);
		UtilityFunctions.rightShiftNMRComplexData(spectrum, spectrum.length / 2);
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] fid = fFourierTransformer.transform(spectrum, TransformType.INVERSE);
		return fid;
	}

	private static final class InverseFFTFilteredMeasurement extends FilteredMeasurement<SpectrumMeasurement> implements FIDMeasurement {

		private static final long serialVersionUID = -3240032383041201512L;
		private List<InverseFFTSpectrumSignal> signals;

		public InverseFFTFilteredMeasurement(SpectrumMeasurement measurement, List<InverseFFTSpectrumSignal> signals) {
			super(measurement);
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

		public InverseFFTSpectrumSignal(BigDecimal time, Complex complex) {
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
