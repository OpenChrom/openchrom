/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.support;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IPeaks;
import org.eclipse.chemclipse.msd.converter.io.IPeakReader;
import org.eclipse.chemclipse.msd.converter.peak.PeakConverterMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakModelMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.implementation.ChromatogramPeakMSD;
import org.eclipse.chemclipse.processing.core.DefaultProcessingResult;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingResult;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.IProcessSettings;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.ModelPeakOption;

public class PeakProcessorSupport {

	private static final Logger logger = Logger.getLogger(PeakProcessorSupport.class);
	//
	public static final String DETECTOR_DESCRIPTION = "AMDIS (Extern)";
	public static final String PEAK_CONVERTER_ID = "org.eclipse.chemclipse.msd.converter.supplier.amdis.peak.elu"; // backwards compatibility

	public void extractEluFileAndSetPeaks(IChromatogramSelectionMSD chromatogramSelection, File file, IProcessSettings processSettings, IProgressMonitor monitor) {

		IProcessingInfo<IPeaks<IPeakMSD>> processingInfo = PeakConverterMSD.convert(file, PEAK_CONVERTER_ID, monitor);
		IPeaks<IPeakMSD> peaks = processingInfo.getProcessingResult();
		insertPeaks(chromatogramSelection, peaks.getPeaks(), processSettings, monitor);
	}

	/**
	 * Extracts the given ELU file and sets the peaks.
	 *
	 * @param chromatogramSelection
	 * @param file
	 * @param minSignalToNoiseRatio
	 * @param monitor
	 * @return
	 */
	public static IProcessingResult<Void> insertPeaks(IChromatogramSelectionMSD chromatogramSelection, List<IPeakMSD> peaks, IProcessSettings processSettings, IProgressMonitor monitor) {

		DefaultProcessingResult<Void> result = new DefaultProcessingResult<>();
		IChromatogramMSD chromatogram = chromatogramSelection.getChromatogram();
		int startRetentionTime = chromatogramSelection.getStartRetentionTime();
		int stopRetentionTime = chromatogramSelection.getStopRetentionTime();
		ModelPeakOption modelPeakOption = processSettings.getModelPeakOption();
		String modelPeakMarker = "MP" + modelPeakOption.value();
		//
		for(IPeakMSD peak : peaks) {
			String header = peak.getTemporaryData();
			try {
				IPeakModelMSD peakModelMSD = peak.getPeakModel();
				//
				int startScan = peakModelMSD.getTemporarilyInfo(IPeakReader.TEMP_INFO_START_SCAN) instanceof Integer ? (int)peakModelMSD.getTemporarilyInfo(IPeakReader.TEMP_INFO_START_SCAN) : 0;
				int stopScan = peakModelMSD.getTemporarilyInfo(IPeakReader.TEMP_INFO_STOP_SCAN) instanceof Integer ? (int)peakModelMSD.getTemporarilyInfo(IPeakReader.TEMP_INFO_STOP_SCAN) : 0;
				int maxScan = peakModelMSD.getTemporarilyInfo(IPeakReader.TEMP_INFO_MAX_SCAN) instanceof Integer ? (int)peakModelMSD.getTemporarilyInfo(IPeakReader.TEMP_INFO_MAX_SCAN) : 0;
				/*
				 * There seems to be an offset of 1 scan.
				 * Why? No clue ...
				 */
				startScan++;
				stopScan++;
				maxScan++;
				/*
				 * Add only peaks above the given minSignalToNoiseRatio and within the
				 * chromatogram selection.
				 */
				IChromatogramPeakMSD chromatogramPeakMSD = new ChromatogramPeakMSD(peakModelMSD, chromatogram);
				chromatogramPeakMSD.setDetectorDescription(DETECTOR_DESCRIPTION);
				if(isValidPeak(chromatogramPeakMSD, startRetentionTime, stopRetentionTime, processSettings)) {
					/*
					 * Pre-check
					 */
					if(startScan > 0 && stopScan > startScan && maxScan > startScan) {
						List<Integer> retentionTimes = new ArrayList<>();
						for(int scan = startScan; scan <= stopScan; scan++) {
							retentionTimes.add(chromatogram.getScan(scan).getRetentionTime());
						}
						/*
						 * Adjust the peak retention times if possible.
						 */
						chromatogramPeakMSD.getPeakModel().replaceRetentionTimes(retentionTimes);
					}
					/*
					 * Post-check
					 */
					if(!ModelPeakOption.ALL.equals(modelPeakOption)) {
						if(header == null || !header.contains(modelPeakMarker)) {
							chromatogramPeakMSD = null;
						}
					}
					/*
					 * Add the peak
					 */
					if(chromatogramPeakMSD != null) {
						chromatogram.addPeak(chromatogramPeakMSD);
						logger.info("Add Peak (ELU): " + header);
					} else {
						String message = "Skip Peak (ELU): " + header;
						logger.info(message);
						result.addInfoMessage(PreferenceSupplier.IDENTIFIER, message);
					}
				} else {
					String message = "Invalid Peak (ELU): " + header;
					logger.info(message);
					result.addInfoMessage(PreferenceSupplier.IDENTIFIER, message);
				}
			} catch(Exception e) {
				logger.warn(e);
				result.addWarnMessage(PreferenceSupplier.IDENTIFIER, "Peak Error (ELU): " + header);
			}
		}
		return result;
	}

	private static boolean isValidPeak(IChromatogramPeakMSD peak, int startRetentionTime, int stopRetentionTime, IProcessSettings processSettings) {

		/*
		 * Null
		 */
		if(peak == null) {
			return false;
		}
		//
		IPeakModel peakModel = peak.getPeakModel();
		/*
		 * Chromatogram Selection Check
		 */
		if(peakModel.getStartRetentionTime() < startRetentionTime || peakModel.getStopRetentionTime() > stopRetentionTime) {
			return false;
		}
		/*
		 * Min S/N
		 */
		if(peak.getSignalToNoiseRatio() < processSettings.getMinSignalToNoiseRatio()) {
			return false;
		}
		/*
		 * Leading
		 */
		float leading = peakModel.getLeading();
		if(leading < processSettings.getMinLeading() || leading > processSettings.getMaxLeading()) {
			return false;
		}
		/*
		 * Tailing
		 */
		float tailing = peakModel.getTailing();
		if(tailing < processSettings.getMinTailing() || tailing > processSettings.getMaxTailing()) {
			return false;
		}
		/*
		 * Checks passed.
		 */
		return true;
	}
}
