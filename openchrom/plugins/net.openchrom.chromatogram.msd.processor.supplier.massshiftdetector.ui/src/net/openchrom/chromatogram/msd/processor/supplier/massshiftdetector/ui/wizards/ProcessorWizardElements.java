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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.wizards;

import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;

public class ProcessorWizardElements extends ChromatogramWizardElements implements IProcessorWizardElements {

	private String referenceChromatogramPath = "";
	private String isotopeChromatogramPath = "";
	private int startShiftLevel = 0;
	private int stopShiftLevel = 0;
	private String notes = ""; // Could be ""
	private String description = ""; // Could be ""

	@Override
	public String getReferenceChromatogramPath() {

		return referenceChromatogramPath;
	}

	@Override
	public void setReferenceChromatogramPath(String referenceChromatogramPath) {

		this.referenceChromatogramPath = referenceChromatogramPath;
	}

	@Override
	public String getIsotopeChromatogramPath() {

		return isotopeChromatogramPath;
	}

	@Override
	public void setIsotopeChromatogramPath(String isotopeChromatogramPath) {

		this.isotopeChromatogramPath = isotopeChromatogramPath;
	}

	@Override
	public int getStartShiftLevel() {

		return startShiftLevel;
	}

	@Override
	public void setStartShiftLevel(int startShiftLevel) {

		this.startShiftLevel = startShiftLevel;
	}

	@Override
	public int getStopShiftLevel() {

		return stopShiftLevel;
	}

	@Override
	public void setStopShiftLevel(int stopShiftLevel) {

		this.stopShiftLevel = stopShiftLevel;
	}

	@Override
	public String getNotes() {

		return notes;
	}

	@Override
	public void setNotes(String notes) {

		this.notes = notes;
	}

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public void setDescription(String description) {

		this.description = description;
	}
}
