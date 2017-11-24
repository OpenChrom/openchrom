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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.eavp.service.swtchart.core.ISeriesData;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ISampleModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ReferenceModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.SampleModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.TrackModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class MeasurementModelData {

	/*
	 * Extract the data.
	 * 0196 [Group]
	 * -> 1 [Track] -> 190 [nm], ISeriesData
	 * -> 1 [Track] -> 200 [nm], ISeriesData
	 * ...
	 * -> 18 [Track] -> 190 [nm], ISeriesData
	 * -> 18 [Track] -> 200 [nm], ISeriesData
	 * 0197 [Group]
	 * -> 1 [Track] -> 190 [nm], ISeriesData
	 * -> 1 [Track] -> 200 [nm], ISeriesData
	 * ...
	 * -> 18 [Track] -> 190 [nm], ISeriesData
	 * -> 18 [Track] -> 200 [nm], ISeriesData
	 */
	private Map<String, Map<Integer, Map<String, ISeriesData>>> mapQualification;
	private Map<Integer, Map<String, ISeriesData>> samplesQualification;
	private Map<Integer, Map<String, ISeriesData>> referencesQualification;
	//
	private Map<String, Map<Integer, Map<String, ISeriesData>>> mapValidation;
	private Map<Integer, Map<String, ISeriesData>> samplesValidation;
	private Map<Integer, Map<String, ISeriesData>> referencesValidation;
	//
	private DataProcessorUI dataProcessorUI;

	public MeasurementModelData() {
		dataProcessorUI = new DataProcessorUI();
		mapQualification = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
		mapValidation = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
	}

	public ITrackModel loadTrackModel(IProcessorModel processorModel, int track, String analysisType, String sampleGroup, String referenceGroup) {

		ITrackModel trackModel = null;
		if(processorModel != null) {
			/*
			 * Retrieve the reference model.
			 */
			ReferenceModel_v1000 referenceModel = loadModelData(processorModel, analysisType, sampleGroup, referenceGroup);
			if(referenceModel != null) {
				/*
				 * Load the sample and track model.
				 */
				processorModel.getReferenceModels().put(referenceGroup, referenceModel);
				SampleModel_v1000 sampleModel = loadSampleModel(processorModel, referenceModel, sampleGroup);
				if(sampleModel != null) {
					referenceModel.getSampleModels().put(sampleGroup, sampleModel);
					trackModel = loadTrackModel(sampleModel, track);
				}
			}
		}
		//
		return trackModel;
	}

	public int getMeasurementDataSize(String analysisType, String type) {

		if(analysisType.equals(DataProcessorUI.ANALYSIS_TYPE_VALIDATION)) {
			return getMeasurementDataSize(samplesValidation, referencesValidation, type);
		} else {
			return getMeasurementDataSize(samplesQualification, referencesQualification, type);
		}
	}

	public Map<Integer, Map<String, ISeriesData>> getMeasurementData(String analysisType, String type) {

		if(analysisType.equals(DataProcessorUI.ANALYSIS_TYPE_VALIDATION)) {
			return getMeasurementData(samplesValidation, referencesValidation, type);
		} else {
			return getMeasurementData(samplesQualification, referencesQualification, type);
		}
	}

	private ReferenceModel_v1000 loadModelData(IProcessorModel processorModel, String analysisType, String sampleGroup, String referenceGroup) {

		List<File> sampleFiles = dataProcessorUI.getMeasurementFileList(processorModel, DataProcessorUI.MEASUREMENT_SAMPLE, sampleGroup);
		List<File> referenceFiles = dataProcessorUI.getMeasurementFileList(processorModel, DataProcessorUI.MEASUREMENT_REFERENCE, referenceGroup);
		//
		if(analysisType.equals(DataProcessorUI.ANALYSIS_TYPE_VALIDATION)) {
			return loadModelDataValidation(processorModel, sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		} else {
			return loadModelDataQualification(processorModel, sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		}
	}

	private ReferenceModel_v1000 loadModelDataQualification(IProcessorModel processorModel, List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		ReferenceModel_v1000 referenceModel = getReferenceModel(processorModel, referenceGroup);
		extractMeasurementQualification(sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		return referenceModel;
	}

	private ReferenceModel_v1000 loadModelDataValidation(IProcessorModel processorModel, List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		ReferenceModel_v1000 referenceModel = getReferenceModel(processorModel, referenceGroup);
		extractMeasurementValidation(sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		return referenceModel;
	}

	private ReferenceModel_v1000 getReferenceModel(IProcessorModel processorModel, String referenceGroup) {

		ReferenceModel_v1000 referenceModel = processorModel.getReferenceModels().get(referenceGroup);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel_v1000();
			referenceModel.setReferenceGroup(referenceGroup);
			referenceModel.setReferencePath(processorModel.getReferenceDirectory());
			processorModel.getReferenceModels().put(referenceGroup, referenceModel);
		}
		return referenceModel;
	}

	private SampleModel_v1000 loadSampleModel(IProcessorModel processorModel, IReferenceModel referenceModel, String sampleGroup) {

		SampleModel_v1000 sampleModel = referenceModel.getSampleModels().get(sampleGroup);
		if(sampleModel == null) {
			sampleModel = new SampleModel_v1000();
			sampleModel.setSampleGroup(sampleGroup);
			sampleModel.setSamplePath(processorModel.getSampleDirectory());
		}
		return sampleModel;
	}

	private ITrackModel loadTrackModel(ISampleModel sampleModel, int track) {

		ITrackModel trackModel = sampleModel.getTrackModels().get(track);
		if(trackModel == null) {
			trackModel = new TrackModel_v1000();
			trackModel.setSampleTrack(track);
			sampleModel.getTrackModels().put(track, (TrackModel_v1000)trackModel);
		}
		/*
		 * Set the current velocity and the reference track.
		 * Set the reference track only if it is 0.
		 * The user may have selected another reference track.
		 */
		trackModel.setScanVelocity(PreferenceSupplier.getScanVelocity());
		if(trackModel.getReferenceTrack() == 0) {
			trackModel.setReferenceTrack(track);
		}
		//
		return trackModel;
	}

	private void extractMeasurementQualification(List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		samplesQualification = mapQualification.get(sampleGroup);
		if(samplesQualification == null) {
			samplesQualification = dataProcessorUI.extractMeasurementsData(sampleFiles, DataProcessorUI.MEASUREMENT_SAMPLE);
			mapQualification.put(sampleGroup, samplesQualification);
		}
		//
		referencesQualification = mapQualification.get(referenceGroup);
		if(referencesQualification == null) {
			referencesQualification = dataProcessorUI.extractMeasurementsData(referenceFiles, DataProcessorUI.MEASUREMENT_REFERENCE);
			mapQualification.put(referenceGroup, referencesQualification);
		}
	}

	private void extractMeasurementValidation(List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		samplesValidation = mapValidation.get(sampleGroup);
		if(samplesValidation == null) {
			samplesValidation = dataProcessorUI.extractMeasurementsData(sampleFiles, DataProcessorUI.MEASUREMENT_SAMPLE);
			mapValidation.put(sampleGroup, samplesValidation);
		}
		//
		referencesValidation = mapValidation.get(referenceGroup);
		if(referencesValidation == null) {
			referencesValidation = dataProcessorUI.extractMeasurementsData(referenceFiles, DataProcessorUI.MEASUREMENT_REFERENCE);
			mapValidation.put(referenceGroup, referencesValidation);
		}
	}

	private int getMeasurementDataSize(Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData, Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData, String type) {

		int size = 0;
		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		if(isReference) {
			if(referenceMeasurementsData != null) {
				size = referenceMeasurementsData.keySet().size();
			}
		} else {
			if(sampleMeasurementsData != null) {
				size = sampleMeasurementsData.keySet().size();
			}
		}
		return size;
	}

	private Map<Integer, Map<String, ISeriesData>> getMeasurementData(Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData, Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData, String type) {

		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		return (isReference) ? referenceMeasurementsData : sampleMeasurementsData;
	}
}
