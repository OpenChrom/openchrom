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
package net.openchrom.nmr.processing.peakdetection;

import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractSpectrumSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class WaveletPeakDetectorProcessor extends AbstractSpectrumSignalFilter<WaveletPeakDetectorSettings> {

	private static final long serialVersionUID = -2363858740271951917L;
	private static final String NAME = "Wavelet Peak Detector";

	public WaveletPeakDetectorProcessor() {

		super(WaveletPeakDetectorSettings.class);
	}

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	protected IMeasurement doFiltering(FilterContext<SpectrumMeasurement, WaveletPeakDetectorSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		SpectrumMeasurement measurement = context.getFilteredObject();
		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(measurement);
		/*
		 * WIP
		 */
		double[] peaks = detect(spectrumData, context.getFilterConfig());
		for(int i = 0; i < peaks.length; i++) {
			System.out.println(peaks[i]);
		}
		FilteredSpectrumMeasurement<WaveletPeakDetectorSettings> filtered = new FilteredSpectrumMeasurement<WaveletPeakDetectorSettings>(context);
		filtered.setSignals(spectrumData.toSignal());
		return filtered;
	}

	private double[] detect(SpectrumData spectrumData, Object clone) {

		/*
		 * TODO detect the peaks
		 */
		double[] signals = new double[spectrumData.signals.length];
		for(int i = 0; i < spectrumData.signals.length; i++) {
			signals[i] = spectrumData.signals[i].getReal();
		}
		return signals;
	}
}
