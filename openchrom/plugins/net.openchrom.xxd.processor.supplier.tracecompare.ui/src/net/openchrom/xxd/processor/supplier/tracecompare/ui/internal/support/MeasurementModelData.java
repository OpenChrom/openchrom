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

	private Map<String, Map<Integer, Map<String, ISeriesData>>> modelDataMap;
	//
	private Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData;
	private Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData;
	//
	private DataProcessorUI dataProcessorUI;

	public MeasurementModelData() {
		modelDataMap = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
		dataProcessorUI = new DataProcessorUI();
	}

	public IReferenceModel loadModelData(IProcessorModel processorModel, List<File> sampleFiles, List<File> referenceFiles, String sampleGroup, String referenceGroup) {

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
		sampleMeasurementsData = modelDataMap.get(sampleGroup);
		if(sampleMeasurementsData == null) {
			sampleMeasurementsData = dataProcessorUI.extractMeasurementsData(sampleFiles, DataProcessorUI.MEASUREMENT_SAMPLE);
			modelDataMap.put(sampleGroup, sampleMeasurementsData);
		}
		//
		referenceMeasurementsData = modelDataMap.get(referenceGroup);
		if(referenceMeasurementsData == null) {
			referenceMeasurementsData = dataProcessorUI.extractMeasurementsData(referenceFiles, DataProcessorUI.MEASUREMENT_REFERENCE);
			modelDataMap.put(referenceGroup, referenceMeasurementsData);
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

	public int getMeasurementDataSize(String type) {

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

	public Map<Integer, Map<String, ISeriesData>> getMeasurementData(String type) {

		boolean isReference = type.startsWith(DataProcessorUI.MEASUREMENT_REFERENCE);
		return (isReference) ? referenceMeasurementsData : sampleMeasurementsData;
	}
}
