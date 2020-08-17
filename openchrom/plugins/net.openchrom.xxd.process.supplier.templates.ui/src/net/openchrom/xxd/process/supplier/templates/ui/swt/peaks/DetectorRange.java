/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.PeakType;

public class DetectorRange {

	private IChromatogram<? extends IPeak> chromatogram = null;
	private int retentionTimeStart = 0;
	private int retentionTimeStop = 0;
	private Set<Integer> traces = new HashSet<>();
	private PeakType detectorType = PeakType.VV;
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

	public PeakType getDetectorType() {

		return detectorType;
	}

	public void setDetectorType(PeakType detectorType) {

		this.detectorType = detectorType;
	}

	public boolean isIncludeBackground() {

		return (PeakType.VV.equals(detectorType)) ? true : false;
	}

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}
}
