/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.core.PeakList;
import org.eclipse.chemclipse.model.core.PeakPosition;
import org.eclipse.chemclipse.model.detector.IMeasurementPeakDetector;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.detector.Detector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.peakdetection.peakmodel.CwtPeak;
import net.openchrom.nmr.processing.peakdetection.peakmodel.CwtPeakSupport;

@Component(service = { Detector.class, IMeasurementPeakDetector.class })
public class WaveletPeakDetectorProcessor implements IMeasurementPeakDetector<WaveletPeakDetectorSettings> {

	/*
	 * Reference for the Wavelet Peak Detector:
	 *
	 * Du, Pan et al. Bioinformatics 22, Nr. 17 (1. September 2006): 2059–65.
	 */
	private static final String NAME = "Wavelet Peak Detector";

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	public Class<WaveletPeakDetectorSettings> getConfigClass() {

		return WaveletPeakDetectorSettings.class;
	}

	@Override
	public <T extends IMeasurement> Map<T, PeakList> detectIMeasurementPeaks(Collection<T> detectorInputItems, WaveletPeakDetectorSettings configuration, MessageConsumer messageConsumer, IProgressMonitor monitor) throws IllegalArgumentException {

		if(configuration == null) {
			configuration = new WaveletPeakDetectorSettings();
		}
		SubMonitor convert = SubMonitor.convert(monitor, getName(), detectorInputItems.size() * 100);
		LinkedHashMap<T, PeakList> map = new LinkedHashMap<>();
		for(T measurement : detectorInputItems) {
			if(measurement instanceof SpectrumMeasurement) {
				map.put(measurement, detect(((SpectrumMeasurement) measurement).getSignals(), configuration, messageConsumer, convert.split(100)));
			}
		}
		return map;
	}

	private PeakList detect(List<? extends SpectrumSignal> signals, WaveletPeakDetectorSettings configuration, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		/*
		 * TODO detect the peaks
		 */
		CwtPeakSupport cwtPeakSupport = new CwtPeakSupport();
		//
		WaveletPeakDetector.calculateWaveletCoefficients(cwtPeakSupport, signals, configuration);
		//
		WaveletPeakDetector.calculateLocalMaxima(cwtPeakSupport, signals, configuration);
		//
		WaveletPeakDetectorRidges.constructRidgeList(cwtPeakSupport, configuration);
		//
		CwtPeak cwtPeak = new CwtPeak();
		cwtPeak.setIndex(1054);
		cwtPeak.setValue(15248.54);
		cwtPeakSupport.getCwtPeakList().put("Peak", cwtPeak);
		cwtPeakSupport.getCwtPeakList().get("Peak").getIndex(); // getX
		cwtPeakSupport.getCwtPeakList().get("Peak").getValue(); // getY
		//
		SubMonitor subMonitor = SubMonitor.convert(monitor, signals.size());
		List<PeakPosition> peakPositions = new ArrayList<>();
		int index = 0;
		for(SpectrumSignal signal : signals) {
			@SuppressWarnings("unused")
			double x = signal.getX();
			@SuppressWarnings("unused")
			double y = signal.getY();
			subMonitor.worked(1);
			if(index == 100) {
				peakPositions.add(new WavletPeakPosition());
			}
		}
		return new PeakList(peakPositions, getID(), getName(), getDescription());
	}

	@Override
	public boolean acceptsIMeasurements(Collection<? extends IMeasurement> items) {

		for(IMeasurement measurement : items) {
			if(!(measurement instanceof SpectrumMeasurement)) {
				return false;
			}
		}
		return true;
	}

}
