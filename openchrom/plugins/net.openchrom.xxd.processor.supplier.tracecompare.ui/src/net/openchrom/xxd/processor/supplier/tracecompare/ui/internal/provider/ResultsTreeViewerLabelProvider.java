/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.TrackStatistics;

public class ResultsTreeViewerLabelProvider extends LabelProvider {

	private DataProcessor dataProcessor = new DataProcessor();
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");

	@Override
	public Image getImage(Object element) {

		return null;
	}

	@Override
	public String getText(Object element) {

		String text;
		if(element instanceof IReferenceModel) {
			IReferenceModel referenceModel = (IReferenceModel)element;
			text = getReferenceModelData(referenceModel);
		} else if(element instanceof ITrackModel) {
			ITrackModel trackModel = (ITrackModel)element;
			text = trackModel.toString();
		} else {
			text = "n.a.";
		}
		return text;
	}

	private String getReferenceModelData(IReferenceModel referenceModel) {

		TrackStatistics trackStatistics = dataProcessor.getTrackStatistics(referenceModel);
		//
		StringBuilder builder = new StringBuilder();
		builder.append("Reference Group: " + referenceModel.getReferenceGroup());
		builder.append("\n");
		builder.append("\tReference Path: " + referenceModel.getReferencePath());
		builder.append("\n");
		builder.append("\tMatch Probability [%]: " + decimalFormat.format(trackStatistics.getMatchProbability()));
		builder.append("\n");
		builder.append("\tTracks: " + trackStatistics.getTracks());
		builder.append("\n");
		builder.append("\tEvaluated Tracks: " + trackStatistics.getEvaluated());
		builder.append("\n");
		builder.append("\tSkipped Tracks: " + trackStatistics.getSkipped());
		builder.append("\n");
		builder.append("\tMatched Tracks: " + trackStatistics.getMatched());
		return builder.toString();
	}

	public String getTrackModelData(ITrackModel trackModel) {

		double factor = trackModel.getScanVelocity() / 1000.0d;
		//
		StringBuilder builder = new StringBuilder();
		builder.append("Sample Track: " + trackModel.getSampleTrack());
		builder.append("\n");
		builder.append("\tReference Track: " + trackModel.getReferenceTrack());
		builder.append("\n");
		builder.append("\t\tScan Velocity [mm/s]: " + trackModel.getScanVelocity());
		builder.append("\n");
		builder.append("\t\tStart Retention Time [ms]: " + (int)trackModel.getStartRetentionTime());
		builder.append("\n");
		builder.append("\t\tStop Retention Time [ms]: " + (int)trackModel.getStopRetentionTime());
		builder.append("\n");
		builder.append("\t\tStart Intensity: " + decimalFormat.format(trackModel.getStartIntensity()));
		builder.append("\n");
		builder.append("\t\tStop Intensity: " + decimalFormat.format(trackModel.getStopIntensity()));
		builder.append("\n");
		builder.append("\t\tDistance Start [mm]: " + decimalFormat.format(trackModel.getStartRetentionTime() * factor));
		builder.append("\n");
		builder.append("\t\tDistance Stop [mm]: " + decimalFormat.format(trackModel.getStopRetentionTime() * factor));
		builder.append("\n");
		builder.append("\t\tSkipped: " + trackModel.isSkipped());
		builder.append("\n");
		builder.append("\t\tEvaluated: " + trackModel.isEvaluated());
		builder.append("\n");
		builder.append("\t\tMatched: " + trackModel.isMatched());
		builder.append("\n");
		builder.append("\t\tSnapshots: " + trackModel.getPathSnapshots());
		builder.append("\n");
		builder.append("\t\tNotes: " + trackModel.getNotes());
		return builder.toString();
	}
}
