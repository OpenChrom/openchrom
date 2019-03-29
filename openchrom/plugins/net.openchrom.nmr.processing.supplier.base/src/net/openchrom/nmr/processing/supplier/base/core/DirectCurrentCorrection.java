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
 * Jan Holy - implementation
 * Christoph Läubrich rework to use new filtering API, cleanup algorithm
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.filter.Filter;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FilteredFIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.ComplexFIDData;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.DirectCurrentCorrectionSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class DirectCurrentCorrection extends AbstractComplexSignalFilter<DirectCurrentCorrectionSettings, IComplexSignalMeasurement<?>> {

	public DirectCurrentCorrection() {
		super(DirectCurrentCorrectionSettings.class);
	}

	private static final String FILTER_NAME = "Direct Current Correction";

	private static void directCurrentCorrectionFID(ComplexFIDData fidData) {

		// following as used in GNAT
		int numberOfPoints = fidData.signals.length;
		int directCurrentPointsTerm = 5 * numberOfPoints / 20;
		int directCurrentPoints = Math.round(directCurrentPointsTerm);
		//
		Complex[] complexSignalsDCcopy = new Complex[numberOfPoints - directCurrentPoints];
		double[] complexSignalsReal = new double[numberOfPoints];
		double[] complexSignalsImag = new double[numberOfPoints];
		complexSignalsDCcopy = Arrays.copyOfRange(fidData.signals, directCurrentPoints, numberOfPoints);
		for(int i = 0; i < complexSignalsDCcopy.length; i++) {
			complexSignalsReal[i] = (complexSignalsDCcopy[i].getReal());
			complexSignalsImag[i] = (complexSignalsDCcopy[i].getImaginary());
		}
		double realAverage = Arrays.stream(complexSignalsReal).average().getAsDouble();
		double imagAverage = Arrays.stream(complexSignalsImag).average().getAsDouble();
		Complex complexSignalsDCcopyAverage = new Complex(realAverage, imagAverage);
		for(int i = 0; i < numberOfPoints; i++) {
			fidData.signals[i] = fidData.signals[i].subtract(complexSignalsDCcopyAverage);
		}
	}

	public Complex[] directCurrentCorrectionSpectrum(SpectrumData spectrumData) {

		/*
		 * to be used after phase correction
		 */
		// TODO select direct current correction for spectrum
		int length = spectrumData.signals.length;
		Complex[] nmrSpectrumProcessedPhasedDC = new Complex[length];
		double directCurrentPointsSpec = Math.ceil(2d * length / 20d);
		double directCurrentPointsSpecPart = Math.ceil(directCurrentPointsSpec / 10);
		int directCurrentPointsRange = (int)(directCurrentPointsSpec - directCurrentPointsSpecPart) + 1;
		Complex[] directCurrentPointsSpecFront = new Complex[directCurrentPointsRange];
		Complex[] directCurrentPointsSpecBack = new Complex[directCurrentPointsRange];
		int z = 0;
		for(int i = (int)directCurrentPointsSpecPart; i <= (int)directCurrentPointsSpec; i++) {
			directCurrentPointsSpecFront[z] = spectrumData.signals[i];
			z++;
		}
		z = 0;
		int forInitialization = (int)(length - directCurrentPointsSpec);
		int forTermination = (int)(length - directCurrentPointsSpecPart);
		for(int i = forInitialization; i <= forTermination; i++) {
			directCurrentPointsSpecBack[z] = spectrumData.signals[i];
			z++;
		}
		Complex[] combinedDirectCurrentPointsSpec = new Complex[directCurrentPointsRange * 2];
		combinedDirectCurrentPointsSpec = ArrayUtils.addAll(directCurrentPointsSpecFront, directCurrentPointsSpecBack);
		double[] tempCombinedArrayReal = new double[combinedDirectCurrentPointsSpec.length];
		double[] tempCombinedArrayImag = new double[combinedDirectCurrentPointsSpec.length];
		for(int i = 0; i < combinedDirectCurrentPointsSpec.length; i++) {
			tempCombinedArrayReal[i] = (combinedDirectCurrentPointsSpec[i].getReal());
			tempCombinedArrayImag[i] = (combinedDirectCurrentPointsSpec[i].getImaginary());
		}
		double tempCombinedArrayRealAverage = Arrays.stream(tempCombinedArrayReal).average().getAsDouble();
		double tempCombinedArrayImagAverage = Arrays.stream(tempCombinedArrayImag).average().getAsDouble();
		Complex tempCombinedArrayAverage = new Complex(tempCombinedArrayRealAverage, tempCombinedArrayImagAverage);
		for(int i = 0; i < nmrSpectrumProcessedPhasedDC.length; i++) {
			spectrumData.signals[i] = spectrumData.signals[i].subtract(tempCombinedArrayAverage);
		}
		return nmrSpectrumProcessedPhasedDC;
	}

	@Override
	public boolean acceptsIMeasurement(IMeasurement item) {

		return item instanceof FIDMeasurement || item instanceof SpectrumMeasurement;
	}

	@Override
	public String getFilterName() {

		return FILTER_NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(IComplexSignalMeasurement<?> measurement, DirectCurrentCorrectionSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		if(measurement instanceof FIDMeasurement) {
			FIDMeasurement fid = (FIDMeasurement)measurement;
			ComplexFIDData fidData = UtilityFunctions.toComplexFIDData(fid.getSignals());
			directCurrentCorrectionFID(fidData);
			FilteredFIDMeasurement filtered = new FilteredFIDMeasurement(fid);
			filtered.setSignals(fidData.toSignal());
			return filtered;
		} else if(measurement instanceof SpectrumMeasurement) {
			SpectrumMeasurement spectrum = (SpectrumMeasurement)measurement;
			SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(spectrum.getSignals());
			FilteredSpectrumMeasurement filtered = new FilteredSpectrumMeasurement(spectrum);
			directCurrentCorrectionSpectrum(spectrumData);
			filtered.setSignals(spectrumData.toSignal());
			return filtered;
		}
		return null;
	}
}
