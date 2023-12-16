/*******************************************************************************
 * Copyright (c) 2017, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

import java.util.HashMap;
import java.util.Map;

public class CalculatedIonCertainties {

	private int startScan;
	private int stopScan;
	private int startRetentionTime;
	private int stopRetentionTime;
	//
	private Map<Integer, Integer> shiftLevelStartIonMap;
	private Map<Integer, Integer> shiftLevelStopIonMap;
	/*
	 * Detect the mass shift. The level defines which shifts shall be detected.
	 * 0 = m/z
	 * 1 = m/z +1
	 * 2 = m/z +2
	 * 3 = m/z +3
	 * Map<Integer, Map<Integer, Map<Integer, Double>>>: ShiftLevel, Scan, m/z, Intensity
	 */
	private Map<Integer, Map<Integer, Map<Integer, Double>>> calculatedIonCertainties;

	public CalculatedIonCertainties() {

		shiftLevelStartIonMap = new HashMap<>();
		shiftLevelStopIonMap = new HashMap<>();
		calculatedIonCertainties = new HashMap<>();
	}

	public int getStartScan() {

		return startScan;
	}

	public void setStartScan(int startScan) {

		this.startScan = startScan;
	}

	public int getStopScan() {

		return stopScan;
	}

	public void setStopScan(int stopScan) {

		this.stopScan = stopScan;
	}

	public int getStartRetentionTime() {

		return startRetentionTime;
	}

	public void setStartRetentionTime(int startRetentionTime) {

		this.startRetentionTime = startRetentionTime;
	}

	public int getStopRetentionTime() {

		return stopRetentionTime;
	}

	public void setStopRetentionTime(int stopRetentionTime) {

		this.stopRetentionTime = stopRetentionTime;
	}

	/**
	 * ShiftLevel, Start m/z
	 * 
	 * @return Map<Integer, Integer>
	 */
	public Map<Integer, Integer> getShiftLevelStartIonMap() {

		return shiftLevelStartIonMap;
	}

	/**
	 * ShiftLevel, Stop m/z
	 * 
	 * @return Map<Integer, Integer>
	 */
	public Map<Integer, Integer> getShiftLevelStopIonMap() {

		return shiftLevelStopIonMap;
	}

	public Map<Integer, Map<Integer, Map<Integer, Double>>> getMap() {

		return calculatedIonCertainties;
	}
}
