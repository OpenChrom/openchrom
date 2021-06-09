/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.pcr.converter.core.AbstractPlateExportConverter;
import org.eclipse.chemclipse.pcr.converter.core.IPlateExportConverter;
import org.eclipse.chemclipse.pcr.model.core.IChannel;
import org.eclipse.chemclipse.pcr.model.core.IChannelSpecification;
import org.eclipse.chemclipse.pcr.model.core.IDetectionFormat;
import org.eclipse.chemclipse.pcr.model.core.IPlate;
import org.eclipse.chemclipse.pcr.model.core.IWell;
import org.eclipse.chemclipse.pcr.model.core.Position;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

public class PCRExportConverter extends AbstractPlateExportConverter implements IPlateExportConverter {

	private static final Logger logger = Logger.getLogger(PCRExportConverter.class);
	//
	private static final String DESCRIPTION = "PCR Export";
	private static final String TAB = "\t";
	private static final String SAMPLE_SUBSET_ALL = "--";
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");

	@Override
	public IProcessingInfo<File> convert(File file, IPlate plate, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		if(plate != null) {
			try {
				PrintWriter printWriter = new PrintWriter(file);
				//
				Map<String, String> headerDataMap = plate.getHeaderDataMap();
				printWriter.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				printWriter.println(headerDataMap.getOrDefault(IPlate.NAME, ""));
				printWriter.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				printValue(printWriter, IPlate.DATE, headerDataMap);
				printValue(printWriter, IPlate.NOISEBAND, headerDataMap);
				printValue(printWriter, IPlate.THRESHOLD, headerDataMap);
				printWriter.print("Detection Format: ");
				printWriter.println((plate.getDetectionFormat() == null) ? "--" : plate.getDetectionFormat().getName());
				printWriter.println("");
				//
				List<String> sampleSubsets = new ArrayList<>(getSampleSubsets(plate));
				Collections.sort(sampleSubsets);
				for(String sampleSubset : sampleSubsets) {
					printResultTable(plate, sampleSubset, printWriter);
					printWriter.println("");
				}
				printWriter.println("");
				//
				printDetectionFormats(plate, printWriter);
				//
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

	private Set<String> getSampleSubsets(IPlate plate) {

		Set<String> sampleSubsets = new HashSet<String>();
		sampleSubsets.add(SAMPLE_SUBSET_ALL);
		//
		for(IWell well : plate.getWells()) {
			String sampleSubset = well.getSampleSubset();
			if(!"".equals(sampleSubset)) {
				sampleSubsets.add(sampleSubset);
			}
		}
		return sampleSubsets;
	}

	private void printResultTable(IPlate plate, String targetSubset, PrintWriter printWriter) {

		printWriter.print("Sample Subset: ");
		printWriter.println(targetSubset);
		//
		printWriter.print("Position");
		printWriter.print(TAB);
		printWriter.print("Name");
		printWriter.print(TAB);
		printWriter.print("Subset");
		printWriter.print(TAB);
		printWriter.print("Target");
		//
		IDetectionFormat detectionFormat = plate.getDetectionFormat();
		if(detectionFormat != null) {
			for(IChannelSpecification channelSpecification : detectionFormat.getChannelSpecifications()) {
				printWriter.print(TAB);
				printWriter.print(channelSpecification.getName());
			}
		} else {
			printWriter.print(TAB);
			printWriter.print("Crossing Points...");
		}
		//
		printWriter.println("");
		//
		//
		for(IWell well : plate.getWells()) {
			if(!well.isEmptyMeasurement()) {
				/*
				 * Sample Subset
				 */
				Position position = well.getPosition();
				String sampleSubset = well.getSampleSubset();
				if(isSubsetMatch(sampleSubset, targetSubset)) {
					printWriter.print(position.getRow() + position.getColumn());
					printWriter.print(TAB);
					printWriter.print(well.getSampleId());
					printWriter.print(TAB);
					printWriter.print(well.getSampleSubset());
					printWriter.print(TAB);
					printWriter.print(well.getTargetName());
					//
					List<Integer> keys = new ArrayList<>(well.getChannels().keySet());
					Collections.sort(keys);
					for(int key : keys) {
						IChannel channel = well.getChannels().get(key);
						printWriter.print(TAB);
						printWriter.print(decimalFormat.format(channel.getCrossingPoint()));
					}
					printWriter.println("");
				}
			}
		}
	}

	private void printDetectionFormats(IPlate plate, PrintWriter printWriter) {

		for(IDetectionFormat detectionFormat : plate.getDetectionFormats()) {
			/*
			 * Detection Format
			 */
			printWriter.println("------");
			printWriter.println(detectionFormat.getData(IDetectionFormat.NAME, ""));
			printWriter.println("------");
			Map<String, String> dataMapFormat = detectionFormat.getData();
			for(Map.Entry<String, String> entry : dataMapFormat.entrySet()) {
				printValue(printWriter, entry.getKey(), dataMapFormat);
			}
			/*
			 * Channel
			 */
			for(IChannelSpecification channelSpecification : detectionFormat.getChannelSpecifications()) {
				printWriter.print("===> ");
				printWriter.print(channelSpecification.getData(IChannelSpecification.NAME, ""));
				printWriter.println("");
				//
				Map<String, String> dataMapChannel = channelSpecification.getData();
				for(Map.Entry<String, String> entry : dataMapChannel.entrySet()) {
					printValue(printWriter, TAB, entry.getKey(), dataMapChannel);
				}
				printWriter.println("");
			}
			printWriter.println("");
		}
	}

	private boolean isSubsetMatch(String sampleSubset, String targetSubset) {

		boolean isMatch = false;
		if(targetSubset == null || SAMPLE_SUBSET_ALL.equals(targetSubset)) {
			isMatch = true;
		} else {
			isMatch = sampleSubset.equals(targetSubset);
		}
		return isMatch;
	}

	private void printValue(PrintWriter printWriter, String key, Map<String, String> data) {

		printValue(printWriter, "", key, data);
	}

	private void printValue(PrintWriter printWriter, String prefix, String key, Map<String, String> data) {

		printWriter.print(prefix);
		printWriter.print(key);
		printWriter.print(": ");
		printWriter.println(data.getOrDefault(key, ""));
	}
}