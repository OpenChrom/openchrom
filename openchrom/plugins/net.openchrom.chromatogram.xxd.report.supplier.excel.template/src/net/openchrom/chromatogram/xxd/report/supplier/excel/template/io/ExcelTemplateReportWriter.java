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
 * Philip Wenig - modular placeholder support
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.INoiseCalculator;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.quantitation.IInternalStandard;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;

import net.openchrom.chromatogram.xxd.report.supplier.excel.template.settings.ChromatogramReportSettings;

public class ExcelTemplateReportWriter {

	public static final String DESCRIPTION = "Excel Template";
	public static final String FILE_EXTENSION = ".xltx";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";

	public List<PlaceholderProcessor> createPlaceholderProcessors() {

		List<PlaceholderProcessor> placeholderProcessors = new ArrayList<>();
		/*
		 * Chromatogram
		 */
		placeholderProcessors.add(new PlaceholderProcessor("chromatogram_name", getFunctionChromatogram(c -> c.getName())));
		placeholderProcessors.add(new PlaceholderProcessor("chromatogram_area", getFunctionChromatogram(c -> Double.toString(c.getPeakIntegratedArea()))));
		placeholderProcessors.add(new PlaceholderProcessor("number_peaks", getFunctionChromatogram(c -> Integer.toString(c.getNumberOfPeaks()))));
		/*
		 * Peak
		 */
		placeholderProcessors.add(new PlaceholderProcessor("peak_number", d -> Integer.toString(d.getPeakNumber() + 1)));
		placeholderProcessors.add(new PlaceholderProcessor("components", getFunctionPeak(p -> Integer.toString(p.getSuggestedNumberOfComponents()))));
		placeholderProcessors.add(new PlaceholderProcessor("peak_area", getFunctionPeak(p -> Double.toString(p.getIntegratedArea()))));
		placeholderProcessors.add(new PlaceholderProcessor("integrator", getFunctionPeak(p -> p.getIntegratorDescription())));
		placeholderProcessors.add(new PlaceholderProcessor("peak_model", getFunctionPeak(p -> p.getModelDescription())));
		placeholderProcessors.add(new PlaceholderProcessor("peak_detector", getFunctionPeak(p -> p.getDetectorDescription())));
		placeholderProcessors.add(new PlaceholderProcessor("quantifier", getFunctionPeak(p -> p.getQuantifierDescription())));
		/*
		 * Chromatogram Peak / Noise Factor
		 */
		placeholderProcessors.add(new PlaceholderProcessor("noise_factor", getFunctionNoiseFactor()));
		placeholderProcessors.add(new PlaceholderProcessor("purity", getFunctionPurity()));
		placeholderProcessors.add(new PlaceholderProcessor("s/n", getFunctionSN()));
		/*
		 * Peak Model
		 */
		placeholderProcessors.add(new PlaceholderProcessor("retention_time_start", getFunctionPeakModel(m -> Integer.toString(m.getStartRetentionTime()))));
		placeholderProcessors.add(new PlaceholderProcessor("retention_time", getFunctionPeakModel(m -> Integer.toString(m.getRetentionTimeAtPeakMaximum()))));
		placeholderProcessors.add(new PlaceholderProcessor("retention_time_stop", getFunctionPeakModel(m -> Integer.toString(m.getStopRetentionTime()))));
		placeholderProcessors.add(new PlaceholderProcessor("peak_height", getFunctionPeakModel(m -> Float.toString(m.getPeakAbundanceByInflectionPoints()))));
		placeholderProcessors.add(new PlaceholderProcessor("peak_width_baseline_from_inflection_point", getFunctionPeakModel(m -> Integer.toString(m.getWidthBaselineByInflectionPoints()))));
		placeholderProcessors.add(new PlaceholderProcessor("peak_width_baseline_total", getFunctionPeakModel(m -> Integer.toString(m.getWidthBaselineTotal()))));
		placeholderProcessors.add(new PlaceholderProcessor("peak_width_by_inflection_points", getFunctionPeakModel(m -> Integer.toString(m.getWidthByInflectionPoints()))));
		placeholderProcessors.add(new PlaceholderProcessor("leading", getFunctionPeakModel(m -> Float.toString(m.getLeading()))));
		placeholderProcessors.add(new PlaceholderProcessor("tailing", getFunctionPeakModel(m -> Float.toString(m.getTailing()))));
		placeholderProcessors.add(new PlaceholderProcessor("retention_index", getFunctionPeakModel(m -> Float.toString(m.getPeakMaximum().getRetentionIndex()))));
		placeholderProcessors.add(new PlaceholderProcessor("scans", getFunctionPeakModel(m -> Integer.toString(m.getNumberOfScans()))));
		placeholderProcessors.add(new PlaceholderProcessor("peak_total_signal", getFunctionPeakModel(m -> Float.toString(m.getPeakMaximum().getTotalSignal()))));
		/*
		 * Library Information
		 */
		placeholderProcessors.add(new PlaceholderProcessor("best_target", getFunctionLibraryInformation(l -> l.getName())));
		placeholderProcessors.add(new PlaceholderProcessor("formula", getFunctionLibraryInformation(l -> l.getFormula())));
		placeholderProcessors.add(new PlaceholderProcessor("cas", getFunctionLibraryInformation(l -> l.getCasNumber())));
		placeholderProcessors.add(new PlaceholderProcessor("smiles", getFunctionLibraryInformation(l -> l.getSmiles())));
		placeholderProcessors.add(new PlaceholderProcessor("inchi", getFunctionLibraryInformation(l -> l.getInChI())));
		placeholderProcessors.add(new PlaceholderProcessor("inchi_key", getFunctionLibraryInformation(l -> l.getInChIKey())));
		placeholderProcessors.add(new PlaceholderProcessor("mol_weight", getFunctionLibraryInformation(l -> Double.toString(l.getMolWeight()))));
		placeholderProcessors.add(new PlaceholderProcessor("reference_identifier", getFunctionLibraryInformation(l -> l.getReferenceIdentifier())));
		placeholderProcessors.add(new PlaceholderProcessor("database", getFunctionLibraryInformation(l -> l.getDatabase())));
		/*
		 * Comparison Result
		 */
		placeholderProcessors.add(new PlaceholderProcessor("match_factor", getFunctionComparisonResult(c -> Double.toString(c.getMatchFactor()))));
		placeholderProcessors.add(new PlaceholderProcessor("reverse_match_factor", getFunctionComparisonResult(c -> Double.toString(c.getReverseMatchFactor()))));
		placeholderProcessors.add(new PlaceholderProcessor("probability", getFunctionComparisonResult(c -> Float.toString(c.getProbability()))));
		/*
		 * Internal Standards
		 */
		placeholderProcessors.add(new PlaceholderProcessor("internal_standard_chemical_class", getFunctionInternalStandard(i -> i.getChemicalClass())));
		placeholderProcessors.add(new PlaceholderProcessor("internal_standard_concentration", getFunctionInternalStandard(i -> Double.toString(i.getConcentration()))));
		placeholderProcessors.add(new PlaceholderProcessor("internal_standard_concentration_unit", getFunctionInternalStandard(i -> i.getConcentrationUnit())));
		placeholderProcessors.add(new PlaceholderProcessor("internal_standard_name", getFunctionInternalStandard(i -> i.getName())));
		placeholderProcessors.add(new PlaceholderProcessor("internal_standard_compensation_factor", getFunctionInternalStandard(i -> Double.toString(i.getCompensationFactor()))));
		/*
		 * Quantitation Entry
		 */
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_entry_area", getFunctionQuantitationEntry(q -> Double.toString(q.getArea()))));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_calibration_method", getFunctionQuantitationEntry(q -> q.getCalibrationMethod())));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_chemical_class", getFunctionQuantitationEntry(q -> q.getChemicalClass())));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_concentration", getFunctionQuantitationEntry(q -> Double.toString(q.getConcentration()))));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_concentration_unit", getFunctionQuantitationEntry(q -> q.getConcentrationUnit())));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_description", getFunctionQuantitationEntry(q -> q.getDescription())));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_name", getFunctionQuantitationEntry(q -> q.getName())));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_flag", getFunctionQuantitationEntry(q -> q.getQuantitationFlag().label())));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_signal", getFunctionQuantitationEntry(q -> Double.toString(q.getSignal()))));
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_cross_zero", getFunctionQuantitationEntry(q -> Boolean.toString(q.getUsedCrossZero()))));
		/*
		 * Quantitation Reference
		 */
		placeholderProcessors.add(new PlaceholderProcessor("quantitation_reference", d -> d.getQuantitationReference()));
		//
		return placeholderProcessors;
	}

	public void generateTemplate(File file) throws IOException {

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				/*
				 * Write the template
				 */
				XSSFSheet sheet = workbook.createSheet("Template");
				XSSFRow row1 = sheet.createRow(0);
				XSSFRow row2 = sheet.createRow(1);
				//
				List<PlaceholderProcessor> placeholderProcessors = createPlaceholderProcessors();
				for(int i = 0; i < placeholderProcessors.size(); i++) {
					PlaceholderProcessor placeholderProcessor = placeholderProcessors.get(i);
					createCell(row1, i, placeholderProcessor.getKey()); // Header
					createCell(row2, i, placeholderProcessor.getPlaceholder()); // Placeholder
				}
				/*
				 * Save
				 */
				workbook.write(fileOutputStream);
			}
		}
	}

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings) throws IOException, InvalidFormatException {

		try (FileInputStream fileInputStreamTemplate = new FileInputStream(reportSettings.getTemplate())) {
			try (XSSFWorkbook workbookTemplate = new XSSFWorkbook(fileInputStreamTemplate)) {
				/*
				 * Use the template sheet. It will be copied on each reported chromatogram.
				 * Check if append is used.
				 */
				XSSFSheet sheetTemplate = workbookTemplate.getSheetAt(0);
				boolean appendData = false;
				if(append) {
					if(file.exists() && file.length() > 0) {
						appendData = true;
					}
				}
				/*
				 * Prepare a new Excel file.
				 */
				if(!appendData) {
					try (XSSFWorkbook workbookNew = new XSSFWorkbook()) {
						try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
							workbookNew.write(fileOutputStream);
						}
					}
				}
				/*
				 * Load the Excel template, but don't close the workbook.
				 * Otherwise "Unexpected end of ZLIB input stream" appears when
				 * trying to save the changes.
				 */
				XSSFWorkbook workbookTarget = null;
				try (FileInputStream fileInputStreamTarget = new FileInputStream(file)) {
					workbookTarget = new XSSFWorkbook(fileInputStreamTarget);
				}
				/*
				 * Populate the Excel file.
				 */
				if(workbookTarget != null) {
					/*
					 * Copy the template sheet
					 */
					XSSFSheet sheetTarget = workbookTarget.createSheet();
					SheetCopySupport.copy(sheetTemplate, sheetTarget);
					/*
					 * Populate the rows
					 */
					Row row = getPlaceholderRow(sheetTarget);
					if(row != null) {
						if(printChromatograms(chromatograms, workbookTarget, sheetTarget, row, sheetTemplate)) {
							recalculate(workbookTarget);
						}
					}
					/*
					 * Save changes
					 */
					try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
						workbookTarget.write(fileOutputStream);
					}
					workbookTarget.close();
				}
			}
		}
	}

	private Function<CellData, String> getFunctionChromatogram(Function<IChromatogram<? extends IPeak>, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IChromatogram<? extends IPeak> chromatogram = cellData.getChromatogram();
				if(chromatogram != null) {
					return function.apply(chromatogram);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionPeak(Function<IPeak, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IPeak peak = cellData.getPeak();
				if(peak != null) {
					return function.apply(peak);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionPeakModel(Function<IPeakModel, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IPeakModel peakModel = cellData.getPeakModel();
				if(peakModel != null) {
					return function.apply(peakModel);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionLibraryInformation(Function<ILibraryInformation, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				ILibraryInformation libraryInformation = cellData.getLibraryInformation();
				if(libraryInformation != null) {
					return function.apply(libraryInformation);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionComparisonResult(Function<IComparisonResult, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IComparisonResult comparisonResult = cellData.getComparisonResult();
				if(comparisonResult != null) {
					return function.apply(comparisonResult);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionInternalStandard(Function<IInternalStandard, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IInternalStandard internalStandard = cellData.getInternalStandard();
				if(internalStandard != null) {
					return function.apply(internalStandard);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionQuantitationEntry(Function<IQuantitationEntry, String> function) {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IQuantitationEntry quantitationEntry = cellData.getQuantitationEntry();
				if(quantitationEntry != null) {
					return function.apply(quantitationEntry);
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionNoiseFactor() {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IChromatogram<? extends IPeak> chromatogram = cellData.getChromatogram();
				chromatogram.getSignalToNoiseRatio(100); // Trigger the NoiseCalculator
				INoiseCalculator noiseCalculator = chromatogram.getNoiseCalculator();
				if(noiseCalculator != null) {
					return Float.toString(noiseCalculator.getNoiseFactor());
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionPurity() {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IPeak peak = cellData.getPeak();
				if(peak instanceof IChromatogramPeak chromatogramPeak) {
					return Float.toString(chromatogramPeak.getPurity());
				}
				//
				return "";
			}
		};
	}

	private Function<CellData, String> getFunctionSN() {

		return new Function<CellData, String>() {

			@Override
			public String apply(CellData cellData) {

				IPeak peak = cellData.getPeak();
				if(peak instanceof IChromatogramPeak chromatogramPeak) {
					return Float.toString(chromatogramPeak.getSignalToNoiseRatio());
				}
				//
				return "";
			}
		};
	}

	private Row getPlaceholderRow(Sheet sheet) {

		for(Row row : sheet) {
			for(Cell cell : row) {
				if(cell.getCellType() == CellType.STRING) {
					String cellValue = cell.getStringCellValue();
					if(cellValue.startsWith(PlaceholderProcessor.PLACEHOLDER_START) && cellValue.endsWith(PlaceholderProcessor.PLACEHOLDER_STOP)) {
						return row;
					}
				}
			}
		}
		return null;
	}

	private boolean printChromatograms(List<IChromatogram<? extends IPeak>> chromatograms, XSSFWorkbook workbook, XSSFSheet sheet, Row row, XSSFSheet sheetTemplate) {

		boolean success = false;
		boolean first = true;
		List<PlaceholderProcessor> placeholderProcessors = createPlaceholderProcessors();
		//
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			if(first) {
				success = populatePeaks(chromatogram, placeholderProcessors, sheet, row);
				first = false;
			} else {
				XSSFSheet sheetNew = workbook.createSheet();
				SheetCopySupport.copy(sheetTemplate, sheetNew);
				success = populatePeaks(chromatogram, placeholderProcessors, sheetNew, row);
			}
		}
		//
		return success;
	}

	private boolean populatePeaks(IChromatogram<? extends IPeak> chromatogram, List<PlaceholderProcessor> placeholderProcessors, Sheet sheet, Row row) {

		boolean success = false;
		//
		if(printPeaks(chromatogram, placeholderProcessors, sheet, row)) {
			deletePlaceholderRow(row, sheet);
			success = true;
		}
		//
		return success;
	}

	private boolean printPeaks(IChromatogram<? extends IPeak> chromatogram, List<PlaceholderProcessor> placeholderProcessors, Sheet sheet, Row row) {

		boolean success = false;
		if(row != null) {
			int numberPeaks = chromatogram.getNumberOfPeaks();
			if(numberPeaks > 0) {
				/*
				 * Iterate the peaks
				 */
				CellData cellData = new CellData("", chromatogram, -1);
				for(int i = 0; i < numberPeaks; i++) {
					cellData.setPeakNumber(i);
					int rowIndex = row.getRowNum() + i + 1;
					Row currentRow = sheet.createRow(rowIndex);
					for(int j = 0; j < row.getLastCellNum(); j++) {
						Cell templateCell = row.getCell(j);
						Cell cell = currentRow.createCell(j);
						if(templateCell != null) {
							/*
							 * Update
							 */
							cell.setCellStyle(templateCell.getCellStyle());
							switch(templateCell.getCellType()) {
								case STRING:
									cellData.setCellValue(templateCell.getStringCellValue());
									cell.setCellValue(populatePlaceholders(placeholderProcessors, cellData));
									break;
								case NUMERIC:
									cell.setCellValue(templateCell.getNumericCellValue());
									break;
								case BOOLEAN:
									cell.setCellValue(templateCell.getBooleanCellValue());
									break;
								case FORMULA:
									cellData.setCellValue(templateCell.getCellFormula());
									cell.setCellFormula(populatePlaceholders(placeholderProcessors, cellData));
									break;
								case BLANK:
									cell.setBlank();
									break;
								default:
									break;
							}
						}
					}
				}
				//
				success = true;
			}
		}
		//
		return success;
	}

	private String populatePlaceholders(List<PlaceholderProcessor> placeholderProcessors, CellData cellData) {

		for(PlaceholderProcessor placeholderProcessor : placeholderProcessors) {
			placeholderProcessor.populate(cellData);
		}
		//
		return cellData.getCellValue();
	}

	private void deletePlaceholderRow(Row row, Sheet sheet) {

		Row rowDelete = getPlaceholderRow(sheet);
		if(rowDelete != null) {
			sheet.shiftRows(rowDelete.getRowNum() + 1, sheet.getLastRowNum(), -1);
		}
	}

	private void recalculate(Workbook workbook) {

		for(Sheet sheet : workbook) {
			for(Row row : sheet) {
				for(Cell cell : row) {
					if(cell.getCellType() == CellType.STRING) {
						try {
							String cellValue = cell.getStringCellValue();
							if(cellValue.startsWith("=")) {
								cell.setCellFormula(cellValue);
							}
							double numericValue = Double.parseDouble(cellValue);
							cell.setCellValue(numericValue);
						} catch(NumberFormatException e) {
							// not a number
						}
					}
				}
			}
		}
		//
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();
	}

	private void createCell(XSSFRow row, int column, String value) {

		XSSFCell cell = row.createCell(column);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(value);
	}
}