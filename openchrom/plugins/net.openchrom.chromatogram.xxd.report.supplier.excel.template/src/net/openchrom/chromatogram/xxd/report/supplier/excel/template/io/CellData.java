/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.io;

import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.quantitation.IInternalStandard;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;

public class CellData {

	private String cellValue = "";
	private IChromatogram<? extends IPeak> chromatogram = null;
	private int peakNumber = 0;
	//
	private IPeak peak = null;
	private IPeakModel peakModel = null;
	private ILibraryInformation libraryInformation = null;
	private IComparisonResult comparisonResult = null;
	private IInternalStandard internalStandard = null;
	private IQuantitationEntry quantitationEntry = null;
	private String quantitationReference = "";

	public CellData(String cellValue, IChromatogram<? extends IPeak> chromatogram, int peakNumber) {

		this.cellValue = cellValue;
		this.chromatogram = chromatogram;
		this.peakNumber = peakNumber;
		updatePeak();
	}

	public String getCellValue() {

		return cellValue;
	}

	public void setCellValue(String cellValue) {

		this.cellValue = cellValue;
	}

	public IChromatogram<? extends IPeak> getChromatogram() {

		return chromatogram;
	}

	public void setChromatogram(IChromatogram<? extends IPeak> chromatogram) {

		this.chromatogram = chromatogram;
		updatePeak();
	}

	public int getPeakNumber() {

		return peakNumber;
	}

	public void setPeakNumber(int peakNumber) {

		this.peakNumber = peakNumber;
		updatePeak();
	}

	public IPeak getPeak() {

		return peak;
	}

	public IPeakModel getPeakModel() {

		return peakModel;
	}

	public ILibraryInformation getLibraryInformation() {

		return libraryInformation;
	}

	public IComparisonResult getComparisonResult() {

		return comparisonResult;
	}

	public IInternalStandard getInternalStandard() {

		return internalStandard;
	}

	public IQuantitationEntry getQuantitationEntry() {

		return quantitationEntry;
	}

	public String getQuantitationReference() {

		return quantitationReference;
	}

	private void updatePeak() {

		peak = null;
		peakModel = null;
		libraryInformation = null;
		comparisonResult = null;
		internalStandard = null;
		quantitationEntry = null;
		quantitationReference = "";
		//
		if(chromatogram != null) {
			List<? extends IPeak> peaks = chromatogram.getPeaks();
			if(peakNumber >= 0 && peakNumber < peaks.size()) {
				peak = peaks.get(peakNumber);
				if(peak != null) {
					/*
					 * Model
					 */
					peakModel = peak.getPeakModel();
					/*
					 * Targets
					 */
					IIdentificationTarget identificationTarget = IIdentificationTarget.getIdentificationTarget(peak);
					if(identificationTarget != null) {
						libraryInformation = identificationTarget.getLibraryInformation();
						comparisonResult = identificationTarget.getComparisonResult();
					}
					/*
					 * Internal Standards
					 */
					List<IInternalStandard> internalStandards = peak.getInternalStandards();
					if(!internalStandards.isEmpty()) {
						internalStandard = internalStandards.get(0);
					}
					/*
					 * Quantitation Entry
					 */
					List<IQuantitationEntry> quantitationEntries = peak.getQuantitationEntries();
					if(!quantitationEntries.isEmpty()) {
						quantitationEntry = quantitationEntries.get(0);
					}
					/*
					 * Quantitation Reference
					 */
					List<String> quantitationReferences = peak.getQuantitationReferences();
					if(!quantitationReferences.isEmpty()) {
						quantitationReference = quantitationReferences.get(0);
					}
				}
			}
		}
	}
}