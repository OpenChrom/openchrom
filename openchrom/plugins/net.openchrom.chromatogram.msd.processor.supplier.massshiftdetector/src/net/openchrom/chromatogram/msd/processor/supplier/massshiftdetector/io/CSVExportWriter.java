/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class CSVExportWriter {

	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();

	public void write(File file, ProcessorData processorData) throws FileNotFoundException {

		PrintWriter printWriter = new PrintWriter(file);

		if(processorData != null) {
			IChromatogramMSD referenceChromatogram = processorData.getReferenceChromatogram();
			IChromatogramMSD isotopeChromatogram = processorData.getIsotopeChromatogram();

			if(referenceChromatogram != null && isotopeChromatogram != null) {

				printWriter.print("Scan#");
				printWriter.print(",");
				printWriter.print("Reference RT (min)");
				printWriter.print(",");
				printWriter.print("Isotope RT (min)");
				printWriter.print(",");
				printWriter.println("Validated");

				if(processorData.getProcessorModel() != null) {
					if(processorData.getProcessorModel().getScanMarker() != null) {
						for(IScanMarker scanMarker : processorData.getProcessorModel().getScanMarker()) {
							int scanNumber = scanMarker.getScanNumber();
							printWriter.print(scanNumber);
							printWriter.print(",");
							printWriter.print(getRetentionTimeInMinutes(referenceChromatogram, scanNumber));
							printWriter.print(",");
							printWriter.print(getRetentionTimeInMinutes(isotopeChromatogram, scanNumber));
							printWriter.print(",");
							printWriter.println(scanMarker.isValidated());
						}
					}
				}
			} else {
				printWriter.println("The chromatogram data is not available.");
			}
		} else {
			printWriter.println("The processor data is not available.");
		}

		printWriter.flush();
		printWriter.close();
	}

	private String getRetentionTimeInMinutes(IChromatogramMSD chromatogram, int scanNumber) {

		IScan scan = chromatogram.getScan(scanNumber);
		if(scan != null) {
			return decimalFormat.format(scan.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
		} else {
			return decimalFormat.format(-1.0d);
		}
	}
}
