/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.masc.escape.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.math.IonRoundMethod;
import org.eclipse.chemclipse.model.targets.TargetSupport;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.settings.ChromatogramReportSettings;

public class MascEscapeReportWriter {

	public void generate(File file, IChromatogramMSD chromatogram, ChromatogramReportSettings reportSettings) throws IOException {

		try (FileInputStream fileInputStreamTemplate = new FileInputStream(reportSettings.getTemplate())) {
			try (XSSFWorkbook workbook = new XSSFWorkbook(fileInputStreamTemplate)) {

				workbook.setWorkbookType(XSSFWorkbookType.XLSX);

				XSSFSheet amdisReportSheet = workbook.getSheet("Amdis report Sample");
				writePeaks(amdisReportSheet, chromatogram);

				XSSFSheet chromThermoReportSheet = workbook.getSheet("Chrom-Thermo");
				writeTIC(chromThermoReportSheet, chromatogram);

				try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
					workbook.write(fileOutputStream);
				}
			}
		}
	}

	private void writePeaks(XSSFSheet amdisReportSheet, IChromatogramMSD chromatogram) {

		createPeakHeader(amdisReportSheet);

		double areaSum = calculateAreaSum(chromatogram.getPeaks());

		int p = 1;
		for(IChromatogramPeakMSD peak : chromatogram.getPeaks()) {
			XSSFRow row = amdisReportSheet.createRow(p);
			IScanMSD peakMax = (IScanMSD)peak.getPeakModel().getPeakMaximum();
			IIdentificationTarget identificationTarget = TargetSupport.getBestIdentificationTarget(peak);
			if(identificationTarget == null) {
				continue;
			}
			ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
			if(libraryInformation == null) {
				continue;
			}

			createTextCell(row, 0, chromatogram.getFile().getAbsolutePath());
			createTextCell(row, 1, libraryInformation.getCasNumber());
			createTextCell(row, 2, libraryInformation.getName());
			createNumericCell(row, 3, peakMax.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
			createNumericCell(row, 4, peakMax.getRetentionIndex());
			createNumericCell(row, 5, peak.getPeakModel().getNumberOfScans());
			createNumericCell(row, 6, peak.getPurity());
			createTextCell(row, 7, peak.getModelDescription());
			// TODO: min abundance
			createTextCell(row, 9, calculateAreaPercent(peak, areaSum) + "%");
			createNumericCell(row, 10, peakMax.getScanNumber());
			createNumericCell(row, 11, peak.getPeakModel().getTailing());
			createNumericCell(row, 12, peak.getSignalToNoiseRatio());
			createNumericCell(row, 13, peakMax.getBasePeak());
			// TODO: max amount
			createNumericCell(row, 15, peak.getIntegratedArea());
			// TODO: Intgr.Signal
			// TODO: Max. Area
			// TODO: Extra Width
			createTextCell(row, 19, peakMax.getIons().size() + ": " + peakMax.getIons().stream() //
					.map(IIon::getIon) //
					.map(IonRoundMethod.getActive()::round) //
					.map(String::valueOf) //
					.collect(Collectors.joining(",")));
			// TODO: Frac. Good
			double retentionIndexDeviation = getRetentionIndexDeviation(peak);
			if(retentionIndexDeviation > 0) {
				createNumericCell(row, 21, (int)retentionIndexDeviation);
			}
			// TODO Net
			IComparisonResult comparisonResult = identificationTarget.getComparisonResult();
			createNumericCell(row, 23, comparisonResult.getMatchFactor());
			createNumericCell(row, 24, comparisonResult.getMatchFactorDirectNotAdjusted());
			createNumericCell(row, 25, comparisonResult.getReverseMatchFactorNotAdjusted());
			// TODO Corrections
			createNumericCell(row, 27, libraryInformation.getMolWeight());
			// TODO S/N (m/z)
			// TODO Area % (m/z)
			// TODO Conc.
			double retentionTimeDeviation = getRetentionTimeDeviation(peak);
			if(retentionTimeDeviation > 0) {
				createNumericCell(row, 31, retentionTimeDeviation);
			}
			p++;
		}

	}

	private void createPeakHeader(XSSFSheet sheet) {

		XSSFRow headerRow = sheet.createRow(0);
		createTextCell(headerRow, 0, "FileName");
		createTextCell(headerRow, 1, "CAS");
		createTextCell(headerRow, 2, "Name");
		createTextCell(headerRow, 3, "RT");
		createTextCell(headerRow, 4, "RI");
		createTextCell(headerRow, 5, "Width");
		createTextCell(headerRow, 6, "Purity");
		createTextCell(headerRow, 7, "Model");
		createTextCell(headerRow, 8, "Min. Abund.");
		createTextCell(headerRow, 9, "Amount");
		createTextCell(headerRow, 10, "Scan");
		createTextCell(headerRow, 11, "Peak Tailing");
		createTextCell(headerRow, 12, "S/N (total)");
		createTextCell(headerRow, 13, "Base Peak");
		createTextCell(headerRow, 14, "Max. Amount");
		createTextCell(headerRow, 15, "Area");
		createTextCell(headerRow, 16, "Intgr.Signal");
		createTextCell(headerRow, 17, "Max. Area");
		createTextCell(headerRow, 18, "Extra Width");
		createTextCell(headerRow, 19, "Models");
		createTextCell(headerRow, 20, "Frac. Good");
		createTextCell(headerRow, 21, "RI-RI(lib)");
		createTextCell(headerRow, 22, "Net");
		createTextCell(headerRow, 23, "Weighted");
		createTextCell(headerRow, 24, "Simple");
		createTextCell(headerRow, 25, "Reverse");
		createTextCell(headerRow, 26, "Corrections");
		createTextCell(headerRow, 27, "(m/z)");
		createTextCell(headerRow, 28, "S/N (m/z)");
		createTextCell(headerRow, 29, "Area % (m/z)");
		createTextCell(headerRow, 30, "Conc.");
		createTextCell(headerRow, 31, "RT-RT(lib)");
	}

	private void writeTIC(XSSFSheet chromThermoReportSheet, IChromatogram chromatogram) {

		XSSFRow firstRow = chromThermoReportSheet.createRow(0);
		createTextCell(firstRow, 0, "CHROMATOGRAM");
		createTextCell(firstRow, 0, chromatogram.getSampleName());

		XSSFRow secondRow = chromThermoReportSheet.createRow(1);
		createTextCell(secondRow, 0, chromatogram.getName());

		XSSFRow thirdRow = chromThermoReportSheet.createRow(2);
		createTextCell(thirdRow, 0, "Data Points: " + chromatogram.getNumberOfScans());

		XSSFRow headerRow = chromThermoReportSheet.createRow(3);
		createTextCell(headerRow, 0, "Time");
		createTextCell(headerRow, 1, "Intensity");

		for(IScan scan : chromatogram.getScans()) {
			XSSFRow row = chromThermoReportSheet.createRow(3 + scan.getScanNumber());
			createNumericCell(row, 0, scan.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
			createNumericCell(row, 1, scan.getTotalSignal());
		}
	}

	private void createTextCell(XSSFRow row, int column, String value) {

		XSSFCell cell = row.createCell(column);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(value);
	}

	private void createNumericCell(XSSFRow row, int column, double value) {

		XSSFCell cell = row.createCell(column);
		cell.setCellType(CellType.NUMERIC);
		cell.setCellValue(value);
	}

	private static double calculateAreaPercent(IPeak peak, double areaSum) {

		if(areaSum != 0) {
			return peak.getIntegratedArea() / areaSum * 100;
		} else {
			return 0.0d;
		}
	}

	private static double calculateAreaSum(Collection<IChromatogramPeakMSD> peaks) {

		double areaSum = 0;
		for(IPeak peak : peaks) {
			areaSum = areaSum + peak.getIntegratedArea();
		}
		return areaSum;
	}

	private static double getRetentionIndexDeviation(IChromatogramPeak peak) {

		IScan scan = peak.getPeakModel().getPeakMaximum();
		if(scan.getRetentionIndex() == 0) {
			return 0;
		}
		IIdentificationTarget identificationTarget = TargetSupport.getBestIdentificationTarget(peak);
		ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
		return scan.getRetentionIndex() - libraryInformation.getRetentionIndex();
	}

	private double getRetentionTimeDeviation(IChromatogramPeakMSD peak) {

		IScan scan = peak.getPeakModel().getPeakMaximum();
		IIdentificationTarget identificationTarget = TargetSupport.getBestIdentificationTarget(peak);
		ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
		if(libraryInformation.getRetentionTime() == 0) {
			return 0;
		}
		return scan.getRetentionTime() - libraryInformation.getRetentionTime();
	}
}
