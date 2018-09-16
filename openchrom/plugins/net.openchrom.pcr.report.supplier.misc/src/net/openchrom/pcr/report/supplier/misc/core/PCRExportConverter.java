/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.pcr.report.supplier.misc.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.pcr.converter.core.AbstractPlateExportConverter;
import org.eclipse.chemclipse.pcr.converter.core.IPlateExportConverter;
import org.eclipse.chemclipse.pcr.model.core.IPlate;
import org.eclipse.chemclipse.pcr.model.core.IWell;
import org.eclipse.chemclipse.pcr.model.core.Position;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class PCRExportConverter extends AbstractPlateExportConverter implements IPlateExportConverter {

	private static final Logger logger = Logger.getLogger(PCRExportConverter.class);
	private static final String DESCRIPTION = "PCR Export";

	@Override
	public IProcessingInfo convert(File file, IPlate plate, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		if(plate != null) {
			try {
				PrintWriter printWriter = new PrintWriter(file);
				//
				Map<String, String> headerDataMap = plate.getHeaderDataMap();
				printWriter.println("-------------------");
				printValue(printWriter, IPlate.NAME, headerDataMap);
				printWriter.println("-------------------");
				printValue(printWriter, IPlate.DATE, headerDataMap);
				printValue(printWriter, IPlate.NOISEBAND, headerDataMap);
				printValue(printWriter, IPlate.THRESHOLD, headerDataMap);
				printWriter.println("");
				//
				for(IWell well : plate.getWells()) {
					if(!well.isEmptyMeasurement()) {
						Position position = well.getPosition();
						Map<String, String> dataMap = well.getData();
						printWriter.println(position.getRow() + position.getColumn());
						printValue(printWriter, IWell.SAMPLE_ID, dataMap);
						printValue(printWriter, IWell.TARGET_NAME, dataMap);
						printValue(printWriter, IWell.CROSSING_POINT, dataMap);
						printValue(printWriter, IWell.SAMPLE_SUBSET, dataMap);
						printWriter.println("");
					}
				}
				printWriter.flush();
				printWriter.close();
				processingInfo.setProcessingResult(file);
			} catch(FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Error to export the plate.");
			}
		} else {
			processingInfo.addErrorMessage(DESCRIPTION, "The PCR plate is not available.");
		}
		//
		return processingInfo;
	}

	private void printValue(PrintWriter printWriter, String key, Map<String, String> data) {

		printWriter.print(key);
		printWriter.print(": ");
		printWriter.println(data.getOrDefault(key, ""));
	}
}
