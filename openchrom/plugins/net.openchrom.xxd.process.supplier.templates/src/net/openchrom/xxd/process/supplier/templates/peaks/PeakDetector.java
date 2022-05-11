/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 * Christoph Läubrich - Support different detector types
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.IPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.IPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetector;
import org.eclipse.chemclipse.chromatogram.peak.detector.settings.IPeakDetectorSettings;
import org.eclipse.chemclipse.chromatogram.wsd.peak.detector.core.IPeakDetectorWSD;
import org.eclipse.chemclipse.chromatogram.wsd.peak.detector.settings.IPeakDetectorSettingsWSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.RetentionTimeRange;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class PeakDetector<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetector<P, C, R> implements IPeakDetectorMSD<P, C, R>, IPeakDetectorCSD<P, C, R>, IPeakDetectorWSD<P, C, R> {

	private PeakDetectorListUtil listUtil = new PeakDetectorListUtil();
	private PeakSupport peakSupport = new PeakSupport();

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_MSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_CSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionWSD chromatogramSelection, IPeakDetectorSettingsWSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionWSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_WSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	private PeakDetectorSettings getSettings(String preferenceKey) {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(PreferenceSupplier.getSettings(preferenceKey, ""));
		return settings;
	}

	private IProcessingInfo<R> applyDetector(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<R> processingInfo = super.validate(chromatogramSelection, settings, new NullProgressMonitor());
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakDetectorSettings) {
				IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
				RetentionIndexMap retentionIndexMap = new RetentionIndexMap(chromatogram);
				PeakDetectorSettings peakDetectorSettings = (PeakDetectorSettings)settings;
				List<DetectorSetting> detectorSettings = peakDetectorSettings.getDetectorSettingsList();
				SubMonitor subMonitor = SubMonitor.convert(monitor, detectorSettings.size());
				//
				for(DetectorSetting detectorSetting : detectorSettings) {
					setPeakBySettings(chromatogram, detectorSetting, retentionIndexMap);
					subMonitor.worked(1);
				}
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DETECTOR_DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void setPeakBySettings(IChromatogram<? extends IPeak> chromatogram, DetectorSetting detectorSetting, RetentionIndexMap retentionIndexMap) {

		RetentionTimeRange retentionTimeRange = peakSupport.getRetentionTimeRange(chromatogram.getPeaks(), detectorSetting, detectorSetting.getReferenceIdentifier(), retentionIndexMap);
		setPeakByRetentionTimeRange(chromatogram, retentionTimeRange, detectorSetting);
	}

	private void setPeakByRetentionTimeRange(IChromatogram<? extends IPeak> chromatogram, RetentionTimeRange retentionTimeRange, DetectorSetting detectorSetting) {

		int startScan = PeakSupport.getStartScan(chromatogram, retentionTimeRange.getStartRetentionTime());
		int stopScan = PeakSupport.getStopScan(chromatogram, retentionTimeRange.getStopRetentionTime());
		Set<Integer> traces = listUtil.extractTraces(detectorSetting.getTraces());
		IPeak peak = peakSupport.extractPeakByScanRange(chromatogram, startScan, stopScan, detectorSetting.isIncludeBackground(), detectorSetting.isOptimizeRange(), traces);
		if(peak != null) {
			/*
			 * Add an identification on demand.
			 */
			String name = detectorSetting.getName();
			if(name != null && !name.isEmpty()) {
				IIdentificationTarget identificationTarget = IIdentificationTarget.createDefaultTarget(name, "", PeakDetectorSettings.DETECTOR_DESCRIPTION);
				if(identificationTarget != null) {
					peak.getTargets().add(identificationTarget);
				}
			}
			/*
			 * Add the peak.
			 */
			addPeak(chromatogram, peak);
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void addPeak(IChromatogram chromatogram, IPeak peak) {

		chromatogram.addPeak(peak);
	}
}