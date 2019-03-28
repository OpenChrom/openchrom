/*******************************************************************************
 * Copyright (c) 2018,2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - changes for ne processor api and process optimizations
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.filter.Filter;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.ComplexFIDData;
import net.openchrom.nmr.processing.supplier.base.settings.FourierTransformationSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class FourierTransformationProcessor extends AbstractFIDSignalFilter<FourierTransformationSettings> {

	private static final String NAME = "Fourier Transformation Processor";

	public FourierTransformationProcessor() {
		super(FourierTransformationSettings.class);
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(FIDMeasurement measurement, FourierTransformationSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		ComplexFIDData fidData = UtilityFunctions.toComplexFIDData(measurement.getSignals());
		ZeroFillingFactor zeroFillingFactor = settings.getZeroFillingFactor();
		Complex[] zeroFilledFid = ZeroFilling.fill(fidData.signals, zeroFillingFactor);
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(zeroFilledFid);
		BigDecimal min = BigDecimal.valueOf(measurement.getFirstDataPointOffset());
		BigDecimal max = BigDecimal.valueOf(measurement.getSweepWidth());
		BigDecimal step = max.subtract(min).divide(BigDecimal.valueOf(nmrSpectrumProcessed.length - 1).setScale(10), RoundingMode.HALF_UP);
		List<FFTSpectrumSignal> signals = new ArrayList<>(nmrSpectrumProcessed.length);
		for(int i = 0; i < nmrSpectrumProcessed.length; i++) {
			signals.add(new FFTSpectrumSignal(BigDecimal.valueOf(i).multiply(step).add(min), nmrSpectrumProcessed[i]));
		}
		return new FFTFilteredMeasurement(measurement, signals);
	}

	@Override
	public String getFilterName() {

		return NAME;
	}

	public static Complex[] fourierTransformNmrData(Complex[] fid) {

		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] nmrSpectrum = fFourierTransformer.transform(fid, TransformType.FORWARD);
		Complex[] nmrSpectrumProcessed = new Complex[nmrSpectrum.length];
		System.arraycopy(nmrSpectrum, 0, nmrSpectrumProcessed, 0, nmrSpectrum.length); // NmrData.SPECTRA
		UtilityFunctions.rightShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		ArrayUtils.reverse(nmrSpectrumProcessed);
		return nmrSpectrumProcessed;
	}

	public static Complex[] inverseFourierTransformData(Complex[] spectrum) {

		ArrayUtils.reverse(spectrum);
		UtilityFunctions.rightShiftNMRComplexData(spectrum, spectrum.length / 2);
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] unfilteredFID = fFourierTransformer.transform(spectrum, TransformType.INVERSE);
		Complex[] analogFID = new Complex[unfilteredFID.length];
		System.arraycopy(unfilteredFID, 0, analogFID, 0, unfilteredFID.length);
		return analogFID;
	}

	private static final class FFTFilteredMeasurement extends FilteredMeasurement<FIDMeasurement> implements SpectrumMeasurement {

		private static final long serialVersionUID = -3570180428815391262L;
		private Collection<? extends SpectrumSignal> signals;

		public FFTFilteredMeasurement(FIDMeasurement measurement, Collection<FFTSpectrumSignal> signals) {
			super(measurement);
			this.signals = Collections.unmodifiableCollection(signals);
		}

		@Override
		public Collection<? extends SpectrumSignal> getSignals() {

			return signals;
		}
	}

	private static final class FFTSpectrumSignal implements SpectrumSignal {

		private BigDecimal shift;
		private Complex complex;

		public FFTSpectrumSignal(BigDecimal shift, Complex complex) {
			this.shift = shift;
			this.complex = complex;
		}

		@Override
		public BigDecimal getChemicalShift() {

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
