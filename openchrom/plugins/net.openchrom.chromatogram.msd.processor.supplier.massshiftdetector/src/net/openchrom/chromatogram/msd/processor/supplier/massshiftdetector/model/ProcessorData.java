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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;

public class ProcessorData {

	private IProcessorModel processorModel;
	//
	private IChromatogramSelectionMSD referenceChromatogramSelection;
	private IChromatogramSelectionMSD isotopeChromatogramSelection;
	//
	private Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts;
	private Map<Integer, Integer> thresholdMassShiftLevel;

	public ProcessorData() {
		thresholdMassShiftLevel = new HashMap<Integer, Integer>();
	}

	public IProcessorModel getProcessorModel() {

		return processorModel;
	}

	public void setProcessorModel(IProcessorModel processorModel) {

		this.processorModel = processorModel;
	}

	public IChromatogramSelectionMSD getReferenceChromatogramSelection() {

		return referenceChromatogramSelection;
	}

	public void setReferenceChromatogramSelection(IChromatogramSelectionMSD referenceChromatogramSelection) {

		this.referenceChromatogramSelection = referenceChromatogramSelection;
	}

	public IChromatogramMSD getReferenceChromatogram() {

		if(referenceChromatogramSelection != null) {
			return referenceChromatogramSelection.getChromatogramMSD();
		} else {
			return null;
		}
	}

	public IChromatogramSelectionMSD getIsotopeChromatogramSelection() {

		return isotopeChromatogramSelection;
	}

	public void setIsotopeChromatogramSelection(IChromatogramSelectionMSD isotopeChromatogramSelection) {

		this.isotopeChromatogramSelection = isotopeChromatogramSelection;
	}

	public IChromatogramMSD getIsotopeChromatogram() {

		if(isotopeChromatogramSelection != null) {
			return isotopeChromatogramSelection.getChromatogramMSD();
		} else {
			return null;
		}
	}

	public Map<Integer, Map<Integer, Map<Integer, Double>>> getMassShifts() {

		return massShifts;
	}

	public void setMassShifts(Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts) {

		this.massShifts = massShifts;
	}

	public Map<Integer, Integer> getLevelUncertainty() {

		return thresholdMassShiftLevel;
	}
}
