/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Philip Wenig - refactoring
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
import org.eclipse.chemclipse.processing.core.IMessageConsumer;
import org.eclipse.chemclipse.processing.detector.Detector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.osgi.service.component.annotations.Component;

@Component(service = {Detector.class, IMeasurementPeakDetector.class})
public class WaveletPeakDetectorProcessor implements IMeasurementPeakDetector<WaveletPeakDetectorSettings> {

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
	public <T extends IMeasurement> Map<T, PeakList> detectIMeasurementPeaks(Collection<T> detectorInputItems, WaveletPeakDetectorSettings configuration, IMessageConsumer messageConsumer, IProgressMonitor monitor) throws IllegalArgumentException {

		SubMonitor convert = SubMonitor.convert(monitor, getName(), detectorInputItems.size() * 100);
		LinkedHashMap<T, PeakList> map = new LinkedHashMap<>();
		for(T measurement : detectorInputItems) {
			if(measurement instanceof SpectrumMeasurement spectrumMeasurement) {
				map.put(measurement, detect(spectrumMeasurement.getSignals(), configuration, messageConsumer, convert.split(100)));
			}
		}
		return map;
	}

	@SuppressWarnings("unused")
	private PeakList detect(List<? extends SpectrumSignal> signals, WaveletPeakDetectorSettings configuration, IMessageConsumer messageConsumer, IProgressMonitor monitor) {

		/*
		 * TODO detect the peaks
		 */
		SubMonitor subMonitor = SubMonitor.convert(monitor, signals.size());
		List<PeakPosition> peakPositions = new ArrayList<>();
		int index = 0;
		for(SpectrumSignal signal : signals) {
			double x = signal.getX();
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
