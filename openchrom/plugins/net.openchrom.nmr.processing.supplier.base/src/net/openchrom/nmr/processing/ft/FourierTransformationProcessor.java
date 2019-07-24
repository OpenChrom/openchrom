/*******************************************************************************
 * Copyright (c) 2018,2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Christoph Läubrich - changes for ne processor api and process optimizations
 *******************************************************************************/
package net.openchrom.nmr.processing.ft;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDAcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumAcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractFIDSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.ComplexFIDData;
import net.openchrom.nmr.processing.supplier.base.core.ZeroFillingProcessor;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class FourierTransformationProcessor extends AbstractFIDSignalFilter<FourierTransformationSettings> {

	private static final long serialVersionUID = -9149804307608333565L;
	private static final String NAME = "Fourier Transformation";

	public FourierTransformationProcessor() {
		super(FourierTransformationSettings.class);
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(FIDMeasurement measurement, FourierTransformationSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		ComplexFIDData fidData = UtilityFunctions.toComplexFIDData(measurement.getSignals());
		Complex[] zeroFilledFid = ZeroFillingProcessor.fill(fidData.signals);
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(zeroFilledFid);
		List<FFTSpectrumSignal> signals = new ArrayList<>(nmrSpectrumProcessed.length);
		for(int i = 0; i < fidData.times.length; i++) {
			BigDecimal frequency;
			if(BigDecimal.ZERO.compareTo(fidData.times[i]) == 0) {
				frequency = BigDecimal.ZERO;
			} else {
				frequency = BigDecimal.ONE.divide(fidData.times[i], 10, BigDecimal.ROUND_HALF_EVEN);
			}
			signals.add(new FFTSpectrumSignal(frequency, nmrSpectrumProcessed[i]));
		}
		return new FFTFilteredMeasurement(measurement, signals);
	}

	@Override
	public String getName() {

		return NAME;
	}

	public static Complex[] fourierTransformNmrData(Complex[] fid) {

		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] nmrSpectrum = fFourierTransformer.transform(fid, TransformType.FORWARD);
		Complex[] nmrSpectrumProcessed = new Complex[nmrSpectrum.length];
		System.arraycopy(nmrSpectrum, 0, nmrSpectrumProcessed, 0, nmrSpectrum.length); // NmrData.SPECTRA
		UtilityFunctions.leftShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		return nmrSpectrumProcessed;
	}

	private static final class FFTFilteredMeasurement extends FilteredMeasurement<FIDMeasurement> implements SpectrumMeasurement, SpectrumAcquisitionParameter {

		private static final long serialVersionUID = -3570180428815391262L;
		private List<? extends SpectrumSignal> signals;
		private double spectralWidth;

		public FFTFilteredMeasurement(FIDMeasurement measurement, List<FFTSpectrumSignal> signals) {
			super(measurement);
			this.signals = Collections.unmodifiableList(signals);
			FIDAcquisitionParameter parameter = measurement.getAcquisitionParameter();
			// TODO check scaling
			this.spectralWidth = (parameter.getNumberOfPoints() / parameter.getAcquisitionTime().doubleValue()) / 2;
		}

		@Override
		public List<? extends SpectrumSignal> getSignals() {

			return signals;
		}

		@Override
		public SpectrumAcquisitionParameter getAcquisitionParameter() {

			return this;
		}

		@Override
		public double getSpectrometerFrequency() {

			SpectrumAcquisitionParameter spectrumAcquisitionParameter = getFilteredObject().getAdapter(SpectrumAcquisitionParameter.class);
			if(spectrumAcquisitionParameter != null) {
				return spectrumAcquisitionParameter.getSpectrometerFrequency();
			}
			return Double.NaN;
		}

		@Override
		public double getSpectralWidth() {

			return spectralWidth;
		}
	}

	private static final class FFTSpectrumSignal implements SpectrumSignal, Serializable {

		private static final long serialVersionUID = 343539516695828431L;
		private BigDecimal shift;
		private Complex complex;

		public FFTSpectrumSignal(BigDecimal shift, Complex complex) {
			this.shift = shift;
			this.complex = complex;
		}

		@Override
		public BigDecimal getFrequency() {

			return shift;
		}

		@Override
		public Number getAbsorptiveIntensity() {

			return complex.getReal();
		}

		@Override
		public Number getDispersiveIntensity() {

			return complex.getImaginary();
		}
	}
}
