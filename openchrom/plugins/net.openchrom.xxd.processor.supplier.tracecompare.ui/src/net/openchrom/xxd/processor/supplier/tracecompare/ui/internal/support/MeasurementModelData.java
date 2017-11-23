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
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ReferenceModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.TrackModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class MeasurementModelData {

	private Map<String, Map<Integer, Map<String, ISeriesData>>> modelDataMapQualification;
	private Map<Integer, Map<String, ISeriesData>> sampleMeasurementsDataQualification;
	private Map<Integer, Map<String, ISeriesData>> referenceMeasurementsDataQualification;
	//
	private Map<String, Map<Integer, Map<String, ISeriesData>>> modelDataMapValidation;
	private Map<Integer, Map<String, ISeriesData>> sampleMeasurementsDataValidation;
	private Map<Integer, Map<String, ISeriesData>> referenceMeasurementsDataValidation;
	//
	private DataProcessorUI dataProcessorUI;

	public MeasurementModelData() {
		dataProcessorUI = new DataProcessorUI();
		modelDataMapQualification = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
		modelDataMapValidation = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
	}

	public IReferenceModel loadModelData(String analysisType, IProcessorModel processorModel, List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		if(analysisType.equals(DataProcessorUI.ANALYSIS_TYPE_VALIDATION)) {
			return loadModelDataValidation(processorModel, sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		} else {
			return loadModelDataQualification(processorModel, sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		}
	}

	private IReferenceModel loadModelDataQualification(IProcessorModel processorModel, List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		/*
		 * Get the model.
		 */
		ReferenceModel_v1000 referenceModel = processorModel.getReferenceModels().get(referenceGroup);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel_v1000();
			referenceModel.setReferenceGroup(referenceGroup);
			referenceModel.setReferencePath(PreferenceSupplier.getReferenceDirectory());
			processorModel.getReferenceModels().put(referenceGroup, referenceModel);
		}
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
		sampleMeasurementsDataQualification = modelDataMapQualification.get(sampleGroup);
		if(sampleMeasurementsDataQualification == null) {
			sampleMeasurementsDataQualification = dataProcessorUI.extractMeasurementsData(sampleFiles, DataProcessorUI.MEASUREMENT_SAMPLE);
			modelDataMapQualification.put(sampleGroup, sampleMeasurementsDataQualification);
		}
		//
		referenceMeasurementsDataQualification = modelDataMapQualification.get(referenceGroup);
		if(referenceMeasurementsDataQualification == null) {
			referenceMeasurementsDataQualification = dataProcessorUI.extractMeasurementsData(referenceFiles, DataProcessorUI.MEASUREMENT_REFERENCE);
			modelDataMapQualification.put(referenceGroup, referenceMeasurementsDataQualification);
		}
		//
		return referenceModel;
	}

	private IReferenceModel loadModelDataValidation(IProcessorModel processorModel, List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

		/*
		 * Get the model.
		 */
		ReferenceModel_v1000 referenceModel = processorModel.getReferenceModels().get(referenceGroup);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel_v1000();
			referenceModel.setReferenceGroup(referenceGroup);
			referenceModel.setReferencePath(PreferenceSupplier.getReferenceDirectory());
			processorModel.getReferenceModels().put(referenceGroup, referenceModel);
		}
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
		sampleMeasurementsDataValidation = modelDataMapValidation.get(sampleGroup);
		if(sampleMeasurementsDataValidation == null) {
			sampleMeasurementsDataValidation = dataProcessorUI.extractMeasurementsData(sampleFiles, DataProcessorUI.MEASUREMENT_SAMPLE);
			modelDataMapValidation.put(sampleGroup, sampleMeasurementsDataValidation);
		}
		//
		referenceMeasurementsDataValidation = modelDataMapValidation.get(referenceGroup);
		if(referenceMeasurementsDataValidation == null) {
			referenceMeasurementsDataValidation = dataProcessorUI.extractMeasurementsData(referenceFiles, DataProcessorUI.MEASUREMENT_REFERENCE);
			modelDataMapValidation.put(referenceGroup, referenceMeasurementsDataValidation);
		}
		//
		return referenceModel;
	}

	public ITrackModel loadTrackModel(IReferenceModel referenceModel, int track) {

		ITrackModel trackModel = referenceModel.getTrackModels().get(track);
		if(trackModel == null) {
			trackModel = new TrackModel_v1000();
			trackModel.setSampleTrack(track);
			referenceModel.getTrackModels().put(track, (TrackModel_v1000)trackModel);
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

	public int getMeasurementDataSize(String analysisType, String type) {

		if(analysisType.equals(DataProcessorUI.ANALYSIS_TYPE_VALIDATION)) {
			return getMeasurementDataSizeValidation(type);
		} else {
			return getMeasurementDataSizeQualification(type);
		}
	}

	private int getMeasurementDataSizeQualification(String type) {

		int size = 0;
		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		if(isReference) {
			if(referenceMeasurementsDataQualification != null) {
				size = referenceMeasurementsDataQualification.keySet().size();
			}
		} else {
			if(sampleMeasurementsDataQualification != null) {
				size = sampleMeasurementsDataQualification.keySet().size();
			}
		}
		return size;
	}

	private int getMeasurementDataSizeValidation(String type) {

		int size = 0;
		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		if(isReference) {
			if(referenceMeasurementsDataValidation != null) {
				size = referenceMeasurementsDataValidation.keySet().size();
			}
		} else {
			if(sampleMeasurementsDataValidation != null) {
				size = sampleMeasurementsDataValidation.keySet().size();
			}
		}
		return size;
	}

	public Map<Integer, Map<String, ISeriesData>> getMeasurementData(String analysisType, String type) {

		if(analysisType.equals(DataProcessorUI.ANALYSIS_TYPE_VALIDATION)) {
			return getMeasurementDataValidation(type);
		} else {
			return getMeasurementDataQualification(type);
		}
	}

	private Map<Integer, Map<String, ISeriesData>> getMeasurementDataQualification(String type) {

		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		return (isReference) ? referenceMeasurementsDataQualification : sampleMeasurementsDataQualification;
	}

	private Map<Integer, Map<String, ISeriesData>> getMeasurementDataValidation(String type) {

		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		return (isReference) ? referenceMeasurementsDataValidation : sampleMeasurementsDataValidation;
	}
}
