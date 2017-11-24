/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ISampleModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.TrackStatistics;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class DataProcessor {

	public static final String PROCESSOR_FILE_EXTENSION = ".otc";
	//
	private static Map<String, Pattern> sampleMatchPatterns = new HashMap<String, Pattern>();

	public String getSampleGroup(String fileName) {

		Pattern pattern = getPattern();
		Matcher matcher = pattern.matcher(fileName);
		if(matcher.find()) {
			return matcher.group(1);
		} else {
			return "no match";
		}
	}

	public boolean measurementExists(String pathDirectory, String fileExtension, String pattern) {

		if(pathDirectory.equals("") || fileExtension.equals("") || pattern.equals("")) {
			return false;
		}
		//
		File directory = new File(pathDirectory);
		for(File file : directory.listFiles()) {
			if(file.isFile()) {
				String fileName = file.getName();
				if(fileName.endsWith(fileExtension)) {
					if(fileName.startsWith(pattern)) {
						return true;
					}
				}
			}
		}
		//
		return false;
	}

	public List<String> getMeasurementPatterns(String pathDirectory, String fileExtension) {

		List<String> patterns = new ArrayList<String>();
		//
		if(!pathDirectory.equals("") && !fileExtension.equals("")) {
			/*
			 * Extract the patterns.
			 */
			Set<String> measurementPatterns = new HashSet<String>();
			Pattern pattern = getPattern();
			File directory = new File(pathDirectory);
			for(File file : directory.listFiles()) {
				if(file.isFile()) {
					String fileName = file.getName();
					if(fileName.endsWith(fileExtension)) {
						Matcher matcher = pattern.matcher(fileName);
						if(matcher.find()) {
							measurementPatterns.add(matcher.group(1));
						}
					}
				}
			}
			//
			patterns.addAll(measurementPatterns);
			Collections.sort(patterns);
		}
		//
		return patterns;
	}

	public List<File> getMeasurementFiles(String pathDirectory, String fileExtension, String measurementPattern) {

		List<File> measurementFiles = new ArrayList<File>();
		//
		if(!pathDirectory.equals("") && !fileExtension.equals("") && !measurementPattern.equals("")) {
			File directory = new File(pathDirectory);
			for(File file : directory.listFiles()) {
				if(file.isFile()) {
					String fileName = file.getName();
					if(fileName.endsWith(fileExtension)) {
						if(fileName.startsWith(measurementPattern)) {
							measurementFiles.add(file);
						}
					}
				}
			}
		}
		//
		return measurementFiles;
	}

	public List<TrackStatistics> getTrackStatistics(IProcessorModel processorModel) {

		List<TrackStatistics> trackStatistics = new ArrayList<TrackStatistics>();
		if(processorModel != null) {
			for(IReferenceModel referenceModel : processorModel.getReferenceModels().values()) {
				trackStatistics.add(getTrackStatistics(referenceModel));
			}
			Collections.sort(trackStatistics, new TrackStatisticComparator(SortOrder.DESC));
		}
		return trackStatistics;
	}

	public TrackStatistics getTrackStatistics(IReferenceModel referenceModel) {

		TrackStatistics trackStatistics = new TrackStatistics();
		if(referenceModel != null) {
			trackStatistics.setReferenceGroup(referenceModel.getReferenceGroup());
			for(ISampleModel sampleModel : referenceModel.getSampleModels().values()) {
				for(ITrackModel trackModel : sampleModel.getTrackModels().values()) {
					trackStatistics.addTrackModel(trackModel);
				}
			}
		}
		return trackStatistics;
	}

	private Pattern getPattern() {

		String filePattern = PreferenceSupplier.getFilePattern();
		Pattern pattern = sampleMatchPatterns.get(filePattern);
		if(pattern == null) {
			pattern = Pattern.compile(filePattern);
			sampleMatchPatterns.put(filePattern, pattern);
		}
		//
		return pattern;
	}

	public int getWavelength(IChromatogram measurement) {

		for(IScan scan : measurement.getScans()) {
			if(scan instanceof IScanWSD) {
				IScanWSD scanWSD = (IScanWSD)scan;
				for(IScanSignalWSD signal : scanWSD.getScanSignals()) {
					int wavelength = (int)signal.getWavelength();
					return wavelength;
				}
			}
		}
		//
		return 0;
	}

	public String getImageName(IProcessorModel processorModel, String type, String group, int track) {

		StringBuilder builder = new StringBuilder();
		if(processorModel != null) {
			builder.append(processorModel.getImageDirectory());
			builder.append(File.separator);
			builder.append(type);
			builder.append("_");
			builder.append(group);
			builder.append("_");
			builder.append(track);
			builder.append(".png");
		}
		return builder.toString();
	}
}
