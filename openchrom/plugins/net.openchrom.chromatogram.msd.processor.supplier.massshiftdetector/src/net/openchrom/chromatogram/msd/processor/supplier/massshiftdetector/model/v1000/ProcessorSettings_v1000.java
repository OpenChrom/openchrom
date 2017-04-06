/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;

public class ProcessorSettings_v1000 implements IProcessorSettings {

	@XmlElement(name = "StartShiftLevel")
	private int startShiftLevel = 0;
	@XmlElement(name = "StopShiftLevel")
	private int stopShiftLevel = 0;
	@XmlElement(name = "NormalizeData")
	private boolean normalizeData = false;
	@XmlElement(name = "IonSelectionStrategy")
	private String ionSelectionStrategy = STRATEGY_ABOVE_MEDIAN;
	@XmlElement(name = "NumberHighestIntensityMZ")
	private int numberHighestIntensityMZ = DEF_N_HIGHEST_INTENSITY;
	@XmlElement(name = "UsePeaks")
	private boolean usePeaks = false; // If no, then scans.

	@Override
	@XmlTransient
	public int getStartShiftLevel() {

		return startShiftLevel;
	}

	@Override
	public void setStartShiftLevel(int startShiftLevel) {

		this.startShiftLevel = startShiftLevel;
	}

	@Override
	@XmlTransient
	public int getStopShiftLevel() {

		return stopShiftLevel;
	}

	@Override
	public void setStopShiftLevel(int stopShiftLevel) {

		this.stopShiftLevel = stopShiftLevel;
	}

	@Override
	@XmlTransient
	public boolean isNormalizeData() {

		return normalizeData;
	}

	@Override
	public void setNormalizeData(boolean normalizeData) {

		this.normalizeData = normalizeData;
	}

	@Override
	@XmlTransient
	public String getIonSelectionStrategy() {

		return ionSelectionStrategy;
	}

	@Override
	public void setIonSelectionStrategy(String ionSelectionStrategy) {

		this.ionSelectionStrategy = ionSelectionStrategy;
	}

	@Override
	@XmlTransient
	public int getNumberHighestIntensityMZ() {

		return numberHighestIntensityMZ;
	}

	@Override
	public void setNumberHighestIntensityMZ(int numberHighestIntensityMZ) {

		this.numberHighestIntensityMZ = numberHighestIntensityMZ;
	}

	@Override
	@XmlTransient
	public boolean isUsePeaks() {

		return usePeaks;
	}

	@Override
	public void setUsePeaks(boolean usePeaks) {

		this.usePeaks = usePeaks;
	}
}
