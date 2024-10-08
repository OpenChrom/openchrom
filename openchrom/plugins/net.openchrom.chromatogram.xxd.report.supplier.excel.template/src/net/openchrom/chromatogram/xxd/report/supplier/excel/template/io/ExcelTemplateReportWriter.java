/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.quantitation.IInternalStandard;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.support.comparator.SortOrder;

import net.openchrom.chromatogram.xxd.report.supplier.excel.template.settings.ChromatogramReportSettings;

public class ExcelTemplateReportWriter {

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings) throws IOException {

		try (FileInputStream fileInputStream = new FileInputStream(reportSettings.getTemplate());
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			Sheet firstSheet = workbook.getSheetAt(0);
			Row templateRow = findTemplateRow(firstSheet);
			if(templateRow == null) {
				return;
			}
			printChromatograms(workbook, chromatograms, firstSheet, append, templateRow);
			deleteRow(templateRow, firstSheet);
			recalculate(workbook);
			try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
				workbook.write(fileOutputStream);
			}
		}
	}

	private Row findTemplateRow(Sheet sheet) {

		for(Row row : sheet) {
			for(Cell cell : row) {
				if(cell.getCellType() == CellType.STRING) {
					String cellValue = cell.getStringCellValue();
					if(cellValue.startsWith("{") && cellValue.endsWith("}")) {
						return row;
					}
				}
			}
		}
		return null;
	}

	private void printChromatograms(Workbook workbook, List<IChromatogram<? extends IPeak>> chromatograms, Sheet firstSheet, boolean append, Row templateRow) {

		boolean first = true;
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			if(append || first) {
				printPeaks(firstSheet, templateRow, chromatogram);
			} else {
				Sheet newSheet = workbook.createSheet();
				printPeaks(newSheet, templateRow, chromatogram);
			}
			first = false;
		}
	}

	private void printPeaks(Sheet sheet, Row templateRow, IChromatogram<? extends IPeak> chromatogram) {

		if(templateRow == null) {
			return;
		}
		for(int i = 0; i < chromatogram.getNumberOfPeaks(); i++) {
			int newRowIdx = templateRow.getRowNum() + i + 1;
			Row newRow = sheet.createRow(newRowIdx);
			for(int j = 0; j < templateRow.getLastCellNum(); j++) {
				Cell templateCell = templateRow.getCell(j);
				Cell newCell = newRow.createCell(j);
				if(templateCell != null) {
					newCell.setCellStyle(templateCell.getCellStyle());
					switch(templateCell.getCellType()) {
						case STRING:
							newCell.setCellValue(populatePlaceholders(templateCell.getStringCellValue(), chromatogram, i));
							break;
						case NUMERIC:
							newCell.setCellValue(templateCell.getNumericCellValue());
							break;
						case BOOLEAN:
							newCell.setCellValue(templateCell.getBooleanCellValue());
							break;
						case FORMULA:
							newCell.setCellFormula(populatePlaceholders(templateCell.getCellFormula(), chromatogram, i));
							break;
						case BLANK:
							newCell.setBlank();
							break;
						default:
							break;
					}
				}
			}
		}
	}

	private String populatePlaceholders(String cellValue, IChromatogram<? extends IPeak> chromatogram, int peakNumber) {

		if(cellValue.contains("{chromatogram_name}")) {
			cellValue = cellValue.replace("{chromatogram_name}", chromatogram.getName());
		}
		if(cellValue.contains("{chromatogram_area}")) {
			cellValue = cellValue.replace("{chromatogram_area}", String.valueOf(chromatogram.getPeakIntegratedArea()));
		}
		if(cellValue.contains("{peak_number}")) {
			cellValue = cellValue.replace("{peak_number}", String.valueOf(peakNumber + 1));
		}
		if(cellValue.contains("{number_peaks}")) {
			cellValue = cellValue.replace("{number_peaks}", String.valueOf(chromatogram.getNumberOfPeaks()));
		}
		IPeak peak = chromatogram.getPeaks().get(peakNumber);
		if(cellValue.contains("{purity}")) {
			String purity = "";
			if(peak instanceof IChromatogramPeak chromatogramPeak) {
				purity = String.valueOf(chromatogramPeak.getPurity());
				cellValue = cellValue.replace("{purity}", purity);
			}
		}
		if(cellValue.contains("{s/n}")) {
			String signalToNoise = "";
			if(peak instanceof IChromatogramPeak chromatogramPeak) {
				signalToNoise = String.valueOf(chromatogramPeak.getSignalToNoiseRatio());
				cellValue = cellValue.replace("{s/n}", signalToNoise);
			}
		}
		if(cellValue.contains("{components}")) {
			cellValue = cellValue.replace("{components}", String.valueOf(peak.getSuggestedNumberOfComponents()));
		}
		if(cellValue.contains("{peak_area}")) {
			cellValue = cellValue.replace("{peak_area}", String.valueOf(peak.getIntegratedArea()));
		}
		if(cellValue.contains("{integrator}")) {
			cellValue = cellValue.replace("{integrator}", String.valueOf(peak.getIntegratorDescription()));
		}
		if(cellValue.contains("{peak_model}")) {
			cellValue = cellValue.replace("{peak_model}", String.valueOf(peak.getModelDescription()));
		}
		if(cellValue.contains("{peak_detector}")) {
			cellValue = cellValue.replace("{peak_detector}", String.valueOf(peak.getDetectorDescription()));
		}
		if(cellValue.contains("{quantifier}")) {
			cellValue = cellValue.replace("{quantifier}", String.valueOf(peak.getQuantifierDescription()));
		}
		IPeakModel peakModel = peak.getPeakModel();
		if(cellValue.contains("{retention_time_start}")) {
			cellValue = cellValue.replace("{rt_start}", String.valueOf(peakModel.getStartRetentionTime()));
		}
		if(cellValue.contains("{retention_time}")) {
			cellValue = cellValue.replace("{retention_time}", String.valueOf(peakModel.getRetentionTimeAtPeakMaximum()));
		}
		if(cellValue.contains("{retention_time_stop}")) {
			cellValue = cellValue.replace("{retention_time_stop}", String.valueOf(peakModel.getStopRetentionTime()));
		}
		if(cellValue.contains("{peak_height}")) {
			cellValue = cellValue.replace("{peak_height}", String.valueOf(peakModel.getPeakAbundanceByInflectionPoints()));
		}
		if(cellValue.contains("{peak_width_baseline_from_inflection_point}")) {
			cellValue = cellValue.replace("{peak_width_baseline_from_inflection_point}", String.valueOf(peakModel.getWidthBaselineByInflectionPoints()));
		}
		if(cellValue.contains("{peak_width_baseline_total}")) {
			cellValue = cellValue.replace("{peak_width_baseline_total}", String.valueOf(peakModel.getWidthBaselineTotal()));
		}
		if(cellValue.contains("{peak_width_by_inflection_points}")) {
			cellValue = cellValue.replace("{peak_width_by_inflection_points}", String.valueOf(peakModel.getWidthByInflectionPoints()));
		}
		if(cellValue.contains("{leading}")) {
			cellValue = cellValue.replace("{leading}", String.valueOf(peakModel.getLeading()));
		}
		if(cellValue.contains("{tailing}")) {
			cellValue = cellValue.replace("{tailing}", String.valueOf(peakModel.getTailing()));
		}
		if(cellValue.contains("{retention_index}")) {
			cellValue = cellValue.replace("{retention_index}", String.valueOf(peakModel.getPeakMaximum().getRetentionIndex()));
		}
		if(cellValue.contains("{scans}")) {
			cellValue = cellValue.replace("{scans}", String.valueOf(peakModel.getNumberOfScans()));
		}
		Set<IIdentificationTarget> peakTargets = peak.getTargets();
		ILibraryInformation libraryInformation = null;
		IComparisonResult comparisonResult = null;
		if(!peakTargets.isEmpty()) {
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC);
			List<IIdentificationTarget> peakTargetList = new ArrayList<>(peakTargets);
			Collections.sort(peakTargetList, identificationTargetComparator);
			IIdentificationTarget peakTarget = peakTargetList.get(0);
			libraryInformation = peakTarget.getLibraryInformation();
			comparisonResult = peakTarget.getComparisonResult();
		}
		if(cellValue.contains("{best_target}")) {
			String bestTarget = "";
			if(libraryInformation != null) {
				bestTarget = libraryInformation.getName();
			}
			cellValue = cellValue.replace("{best_target}", bestTarget);
		}
		if(cellValue.contains("{formula}")) {
			String formula = "";
			if(libraryInformation != null) {
				formula = libraryInformation.getFormula();
			}
			cellValue = cellValue.replace("{formula}", formula);
		}
		if(cellValue.contains("{cas}")) {
			String cas = "";
			if(libraryInformation != null) {
				cas = libraryInformation.getCasNumber();
			}
			cellValue = cellValue.replace("{cas}", cas);
		}
		if(cellValue.contains("{smiles}")) {
			String smiles = "";
			if(libraryInformation != null) {
				smiles = libraryInformation.getSmiles();
			}
			cellValue = cellValue.replace("{smiles}", smiles);
		}
		if(cellValue.contains("{inchi}")) {
			String inchi = "";
			if(libraryInformation != null) {
				inchi = libraryInformation.getInChI();
			}
			cellValue = cellValue.replace("{inchi}", inchi);
		}
		if(cellValue.contains("{inchi_key}")) {
			String inchiKey = "";
			if(libraryInformation != null) {
				inchiKey = libraryInformation.getInChIKey();
			}
			cellValue = cellValue.replace("{inchi_key}", inchiKey);
		}
		if(cellValue.contains("{match_factor}")) {
			String matchFactor = "";
			if(comparisonResult != null) {
				matchFactor = String.valueOf(comparisonResult.getMatchFactor());
			}
			cellValue = cellValue.replace("{match_factor}", matchFactor);
		}
		if(cellValue.contains("{reverse_match_factor}")) {
			String reverseMatchFactor = "";
			if(comparisonResult != null) {
				reverseMatchFactor = String.valueOf(comparisonResult.getReverseMatchFactor());
			}
			cellValue = cellValue.replace("{reverse_match_factor}", reverseMatchFactor);
		}
		if(cellValue.contains("{probability}")) {
			String probability = "";
			if(comparisonResult != null) {
				probability = String.valueOf(comparisonResult.getProbability());
			}
			cellValue = cellValue.replace("{probability}", probability);
		}
		if(cellValue.contains("{mol_weight}")) {
			String molWeight = "";
			if(libraryInformation != null) {
				molWeight = String.valueOf(libraryInformation.getMolWeight());
			}
			cellValue = cellValue.replace("{mol_weight}", molWeight);
		}
		if(cellValue.contains("{reference_identifier}")) {
			String referenceIdentifier = "";
			if(libraryInformation != null) {
				referenceIdentifier = String.valueOf(libraryInformation.getReferenceIdentifier());
			}
			cellValue = cellValue.replace("{reference_identifier}", referenceIdentifier);
		}
		if(cellValue.contains("{database}")) {
			String database = "";
			if(libraryInformation != null) {
				database = String.valueOf(libraryInformation.getDatabase());
			}
			cellValue = cellValue.replace("{database}", database);
		}
		List<IInternalStandard> internalStandards = peak.getInternalStandards();
		IInternalStandard internalStandard = null;
		if(!internalStandards.isEmpty()) {
			internalStandard = internalStandards.get(0);
		}
		if(cellValue.contains("{internal_standard_chemical_class}")) {
			String internalStandardChemicalClass = "";
			if(internalStandard != null) {
				internalStandardChemicalClass = internalStandard.getChemicalClass();
			}
			cellValue = cellValue.replace("{internal_standard_chemical_class}", internalStandardChemicalClass);
		}
		if(cellValue.contains("{internal_standard_concentration}")) {
			String internalStandardConcentration = "";
			if(internalStandard != null) {
				internalStandardConcentration = String.valueOf(internalStandard.getConcentration());
			}
			cellValue = cellValue.replace("{internal_standard_concentration}", internalStandardConcentration);
		}
		if(cellValue.contains("{internal_standard_concentration_unit}")) {
			String internalStandardConcentrationUnit = "";
			if(internalStandard != null) {
				internalStandardConcentrationUnit = internalStandard.getConcentrationUnit();
			}
			cellValue = cellValue.replace("{internal_standard_concentration_unit}", internalStandardConcentrationUnit);
		}
		if(cellValue.contains("{internal_standard_name}")) {
			String internalStandardName = "";
			if(internalStandard != null) {
				internalStandardName = internalStandard.getName();
			}
			cellValue = cellValue.replace("{internal_standard_name}", internalStandardName);
		}
		if(cellValue.contains("{internal_standard_compensation_factor}")) {
			String internalStandardCompensationFactor = "";
			if(internalStandard != null) {
				internalStandardCompensationFactor = String.valueOf(internalStandard.getCompensationFactor());
			}
			cellValue = cellValue.replace("{internal_standard_compensation_factor}", internalStandardCompensationFactor);
		}
		List<IQuantitationEntry> quantitationEntries = peak.getQuantitationEntries();
		IQuantitationEntry quantitationEntry = null;
		if(!quantitationEntries.isEmpty()) {
			quantitationEntry = quantitationEntries.get(0);
		}
		if(cellValue.contains("{quantitation_entry_area}")) {
			String quantitationEntryArea = "";
			if(quantitationEntry != null) {
				quantitationEntryArea = String.valueOf(quantitationEntry.getArea());
			}
			cellValue = cellValue.replace("{quantitation_entry_area}", quantitationEntryArea);
		}
		if(cellValue.contains("{quantitation_calibration_method}")) {
			String calibrationMethod = "";
			if(quantitationEntry != null) {
				calibrationMethod = String.valueOf(quantitationEntry.getCalibrationMethod());
			}
			cellValue = cellValue.replace("{quantitation_calibration_method}", calibrationMethod);
		}
		if(cellValue.contains("{quantitation_chemical_class}")) {
			String chemicalClass = "";
			if(quantitationEntry != null) {
				chemicalClass = quantitationEntry.getChemicalClass();
			}
			cellValue = cellValue.replace("{quantitation_chemical_class}", chemicalClass);
		}
		if(cellValue.contains("{quantitation_concentration}")) {
			String quantitationConcentration = "";
			if(quantitationEntry != null) {
				quantitationConcentration = String.valueOf(quantitationEntry.getConcentration());
			}
			cellValue = cellValue.replace("{quantitation_concentration}", quantitationConcentration);
		}
		if(cellValue.contains("{quantitation_concentration_unit}")) {
			String quantitationConcentrationUnit = "";
			if(quantitationEntry != null) {
				quantitationConcentrationUnit = quantitationEntry.getConcentrationUnit();
			}
			cellValue = cellValue.replace("{quantitation_concentration_unit}", quantitationConcentrationUnit);
		}
		if(cellValue.contains("{quantitation_description}")) {
			String quantitationDescription = "";
			if(quantitationEntry != null) {
				quantitationDescription = quantitationEntry.getDescription();
			}
			cellValue = cellValue.replace("{quantitation_description}", quantitationDescription);
		}
		if(cellValue.contains("{quantitation_name}")) {
			String quantitationName = "";
			if(quantitationEntry != null) {
				quantitationName = quantitationEntry.getName();
			}
			cellValue = cellValue.replace("{quantitation_name}", quantitationName);
		}
		if(cellValue.contains("{quantitation_flag}")) {
			String quantitationFlag = "";
			if(quantitationEntry != null) {
				quantitationFlag = quantitationEntry.getQuantitationFlag().label();
			}
			cellValue = cellValue.replace("{quantitation_flag}", quantitationFlag);
		}
		if(cellValue.contains("{quantitation_signal}")) {
			String quantitationSignal = "";
			if(quantitationEntry != null) {
				quantitationSignal = String.valueOf(quantitationEntry.getSignal());
			}
			cellValue = cellValue.replace("{quantitation_signal}", quantitationSignal);
		}
		if(cellValue.contains("{quantitation_cross_zero}")) {
			String quantitationCrossZero = "";
			if(quantitationEntry != null) {
				quantitationCrossZero = String.valueOf(quantitationEntry.getUsedCrossZero());
			}
			cellValue = cellValue.replace("{quantitation_cross_zero}", quantitationCrossZero);
		}
		if(cellValue.contains("{quantitation_reference}")) {
			String quantitationReference = "";
			List<String> quantitationReferences = peak.getQuantitationReferences();
			if(!quantitationReferences.isEmpty()) {
				quantitationReference = quantitationReferences.get(0);
			}
			cellValue = cellValue.replace("{quantitation_reference}", quantitationReference);
		}
		return cellValue;
	}

	private void deleteRow(Row row, Sheet sheet) {

		sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum(), -1);
	}

	private void recalculate(Workbook workbook) {

		for(Sheet sheet : workbook) {
			for(Row row : sheet) {
				for(Cell cell : row) {
					if(cell.getCellType() == CellType.STRING) {
						String cellValue = cell.getStringCellValue();
						if(cellValue.startsWith("=")) {
							cell.setCellFormula(cellValue);
						}
						try {
							double numericValue = Double.parseDouble(cellValue);
							cell.setCellValue(numericValue);
						} catch(NumberFormatException e) {
							// not a number
						}
					}
				}
			}
		}
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();
	}
}