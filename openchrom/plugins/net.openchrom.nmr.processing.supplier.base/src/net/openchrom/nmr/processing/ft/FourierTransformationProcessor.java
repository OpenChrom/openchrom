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
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
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

	private static final long serialVersionUID = 5702896391785304170L;
	private static final String NAME = "Fourier Transformation";

	public FourierTransformationProcessor() {

		super(FourierTransformationSettings.class);
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(FIDMeasurement measurement, FourierTransformationSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		ComplexFIDData fidData = UtilityFunctions.toComplexFIDData(measurement.getSignals());
		Complex[] zeroFilledFid = ZeroFillingProcessor.fill(fidData.signals);
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(zeroFilledFid);
		BigDecimal min = BigDecimal.valueOf(measurement.getFirstDataPointOffset());
		BigDecimal max = BigDecimal.valueOf(measurement.getFirstDataPointOffset() + measurement.getSweepWidth());
		BigDecimal step = max.subtract(min).divide(BigDecimal.valueOf(nmrSpectrumProcessed.length - 1).setScale(10), RoundingMode.HALF_UP);
		List<FFTSpectrumSignal> signals = new ArrayList<>(nmrSpectrumProcessed.length);
		// handle another Bruker specific characteristic
		if(measurement.getDataName().equals("Digital Filter Removal")) {
			// Bruker spectral data has to be added in a "reversed" order to fit the chemical shift values
			for(int i = 0; i < nmrSpectrumProcessed.length; i++) {
				signals.add(new FFTSpectrumSignal(max.subtract(step.multiply(BigDecimal.valueOf(i))), nmrSpectrumProcessed[i]));
			}
		} else {
			// other types of nmr data are not affected and can be added the way it was done before
			for(int i = 0; i < nmrSpectrumProcessed.length; i++) {
				signals.add(new FFTSpectrumSignal(BigDecimal.valueOf(i).multiply(step).add(min), nmrSpectrumProcessed[i]));
			}
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
		UtilityFunctions.leftShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		// This has nothing to do with the inverted UI!
		// ArrayUtils.reverse(nmrSpectrumProcessed);
		return nmrSpectrumProcessed;
	}

	public static Complex[] inverseFourierTransformData(Complex[] spectrum) {

		// ArrayUtils.reverse(spectrum);
		UtilityFunctions.rightShiftNMRComplexData(spectrum, spectrum.length / 2);
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] unfilteredFID = fFourierTransformer.transform(spectrum, TransformType.INVERSE);
		Complex[] analogFID = new Complex[unfilteredFID.length];
		System.arraycopy(unfilteredFID, 0, analogFID, 0, unfilteredFID.length);
		return analogFID;
	}

	private static final class FFTFilteredMeasurement extends FilteredMeasurement<FIDMeasurement> implements SpectrumMeasurement {

		private static final long serialVersionUID = -3570180428815391262L;
		private List<? extends SpectrumSignal> signals;

		public FFTFilteredMeasurement(FIDMeasurement measurement, List<FFTSpectrumSignal> signals) {

			super(measurement);
			this.signals = Collections.unmodifiableList(signals);
		}

		@Override
		public List<? extends SpectrumSignal> getSignals() {

			return signals;
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
