/*******************************************************************************
<<<<<<< HEAD
 * Copyright (c) 2019, 2020 Lablicate GmbH.
=======
 * Copyright (c) 2019 Lablicate GmbH.
>>>>>>> upstream/wavelet-peak-detector
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
<<<<<<< HEAD
 * Christoph Läubrich - use PeakType instead of plain String
=======
>>>>>>> upstream/wavelet-peak-detector
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
<<<<<<< HEAD
import org.eclipse.chemclipse.model.core.PeakType;
=======

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
>>>>>>> upstream/wavelet-peak-detector

public class DetectorRange {

	private IChromatogram<? extends IPeak> chromatogram = null;
	private int retentionTimeStart = 0;
	private int retentionTimeStop = 0;
	private Set<Integer> traces = new HashSet<>();
<<<<<<< HEAD
	private String detectorType = PeakType.VV.name();
=======
	private String detectorType = DetectorSetting.DETECTOR_TYPE_VV;
>>>>>>> upstream/wavelet-peak-detector
	private boolean optimizeRange = true;

	public IChromatogram<? extends IPeak> getChromatogram() {

		return chromatogram;
	}

	public void setChromatogram(IChromatogram<? extends IPeak> chromatogram) {

		this.chromatogram = chromatogram;
	}

	public int getRetentionTimeStart() {

		return retentionTimeStart;
	}

	public void setRetentionTimeStart(int retentionTimeStart) {

		this.retentionTimeStart = retentionTimeStart;
	}

	public int getRetentionTimeStop() {

		return retentionTimeStop;
	}

	public void setRetentionTimeStop(int retentionTimeStop) {

		this.retentionTimeStop = retentionTimeStop;
	}

	public Set<Integer> getTraces() {

		return traces;
	}

	public void setTraces(Set<Integer> traces) {

		this.traces = traces;
	}

	public String getDetectorType() {

		return detectorType;
	}

	public void setDetectorType(String detectorType) {

		this.detectorType = detectorType;
	}

	public boolean isIncludeBackground() {

<<<<<<< HEAD
		return (PeakType.VV.name().equals(detectorType)) ? true : false;
=======
		return (DetectorSetting.DETECTOR_TYPE_VV.equals(detectorType)) ? true : false;
>>>>>>> upstream/wavelet-peak-detector
	}

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}
}
