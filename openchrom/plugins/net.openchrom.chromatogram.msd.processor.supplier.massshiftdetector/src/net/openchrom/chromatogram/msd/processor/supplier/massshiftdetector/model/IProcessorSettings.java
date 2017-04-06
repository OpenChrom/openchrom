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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

public interface IProcessorSettings {

	String STRATEGY_SELECT_ALL = "STRATEGY_SELECT_ALL";
	String STRATEGY_ABOVE_MEAN = "STRATEGY_ABOVE_MEAN";
	String STRATEGY_ABOVE_MEDIAN = "STRATEGY_ABOVE_MEDIAN";
	String STRATEGY_N_HIGHEST_INTENSITY = "STRATEGY_N_HIGHEST_INTENSITY";
	//
	int MIN_N_HIGHEST_INTENSITY = 1;
	int MAX_N_HIGHEST_INTENSITY = 10;
	int DEF_N_HIGHEST_INTENSITY = 5;

	int getStartShiftLevel();

	void setStartShiftLevel(int startShiftLevel);

	int getStopShiftLevel();

	void setStopShiftLevel(int stopShiftLevel);

	boolean isNormalizeData();

	void setNormalizeData(boolean normalizeData);

	String getIonSelectionStrategy();

	void setIonSelectionStrategy(String ionSelectionStrategy);

	int getNumberHighestIntensityMZ();

	void setNumberHighestIntensityMZ(int numberHighestIntensityMZ);

	boolean isUsePeaks();

	void setUsePeaks(boolean usePeaks);
}
