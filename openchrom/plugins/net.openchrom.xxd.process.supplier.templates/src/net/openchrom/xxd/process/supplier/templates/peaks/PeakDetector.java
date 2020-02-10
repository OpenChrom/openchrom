/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.IPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.IPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetector;
import org.eclipse.chemclipse.chromatogram.peak.detector.settings.IPeakDetectorSettings;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IPeakModelCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.csd.model.implementation.ChromatogramPeakCSD;
import org.eclipse.chemclipse.model.core.AbstractPeak;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.IRetentionTimeRange;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakModelMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons.IonMarkMode;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIons;
import org.eclipse.chemclipse.msd.model.detector.TemplatePeak;
import org.eclipse.chemclipse.msd.model.detector.TemplatePeakDetector;
import org.eclipse.chemclipse.msd.model.implementation.ChromatogramPeakMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.Activator;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

@SuppressWarnings("rawtypes")
public class PeakDetector extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	private static final PeakSupport PEAK_SUPPORT = new PeakSupport();
	private static final PeakDetectorListUtil LIST_UTIL = new PeakDetectorListUtil();

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_MSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_CSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	private PeakDetectorSettings getSettings(String preferenceKey) {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(PreferenceSupplier.getSettings(preferenceKey, ""));
		return settings;
	}

	private IProcessingInfo applyDetector(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		@SuppressWarnings("unchecked")
		IProcessingInfo<?> processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakDetectorSettings) {
				IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
				if(chromatogram instanceof IChromatogramCSD || chromatogram instanceof IChromatogramMSD) {
					PeakDetectorSettings peakDetectorSettings = (PeakDetectorSettings)settings;
					// collect all settings
					Map<PeakType, List<DetectorSettingTemplatePeak>> templates = new LinkedHashMap<>();
					for(DetectorSetting detectorSetting : peakDetectorSettings.getDetectorSettingsList()) {
						PeakType type = detectorSetting.getDetectorType();
						templates.computeIfAbsent(type, t -> new ArrayList<>()).add(new DetectorSettingTemplatePeak(chromatogram, detectorSetting));
					}
					// detect peaks
					Collection<TemplatePeakDetector<?>> detectors = Activator.getDetectors();
					List<Map<DetectorSettingTemplatePeak, IPeakModel>> peaks = new ArrayList<>();
					outer:
					for(Entry<PeakType, List<DetectorSettingTemplatePeak>> entry : templates.entrySet()) {
						PeakType peakType = entry.getKey();
						for(TemplatePeakDetector<?> templatePeakDetector : detectors) {
							if(templatePeakDetector.isDefaultFor(peakType)) {
								peaks.add(templatePeakDetector.detectPeaks(chromatogram, entry.getValue(), null));
								continue outer;
							}
						}
						processingInfo.addErrorMessage(PeakDetectorSettings.DESCRIPTION, "No detector present for type " + peakType.name());
					}
					// insert peaks into chromatogram
					for(Map<DetectorSettingTemplatePeak, IPeakModel> map : peaks) {
						for(Entry<DetectorSettingTemplatePeak, IPeakModel> entry : map.entrySet()) {
							DetectorSettingTemplatePeak setting = entry.getKey();
							IPeakModel model = entry.getValue();
							if(model == null) {
								continue;
							}
							AbstractPeak peak;
							if(chromatogram instanceof IChromatogramCSD) {
								IChromatogramCSD csd = (IChromatogramCSD)chromatogram;
								ChromatogramPeakCSD peakCSD = new ChromatogramPeakCSD((IPeakModelCSD)model, csd);
								csd.addPeak(peakCSD);
								peak = peakCSD;
							} else if(chromatogram instanceof IChromatogramMSD) {
								IChromatogramMSD msd = (IChromatogramMSD)chromatogram;
								ChromatogramPeakMSD peakMSD = new ChromatogramPeakMSD((IPeakModelMSD)model, msd);
								msd.addPeak(peakMSD);
								peak = peakMSD;
							} else {
								continue;
							}
							if(peakDetectorSettings.isUseCommentAsNames()) {
								peak.setName(setting.detectorSetting.getComment());
							}
						}
					}
				} else {
					processingInfo.addWarnMessage(PeakDetectorSettings.DESCRIPTION, "Only MSD + CSD Chromatograms are supported");
				}
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private static final class DetectorSettingTemplatePeak implements TemplatePeak {

		private int startScan;
		private int stopScan;
		private PeakType peakType;
		private boolean optimizeRange;
		private Set<Integer> traces;
		private DetectorSetting detectorSetting;

		public DetectorSettingTemplatePeak(IChromatogram<?> chromatogram, DetectorSetting detectorSetting) {

			this.detectorSetting = detectorSetting;
			IRetentionTimeRange retentionTimeRange = PEAK_SUPPORT.getRetentionTimeRange(chromatogram.getPeaks(), detectorSetting, detectorSetting.getReferenceIdentifier());
			startScan = chromatogram.getScanNumber(retentionTimeRange.getStartRetentionTime());
			stopScan = chromatogram.getScanNumber(retentionTimeRange.getStopRetentionTime());
			peakType = detectorSetting.getDetectorType();
			optimizeRange = detectorSetting.isOptimizeRange();
			traces = LIST_UTIL.extractTraces(detectorSetting.getTraces());
		}

		@Override
		public int getStartScan() {

			return startScan;
		}

		@Override
		public int getStopScan() {

			return stopScan;
		}

		@Override
		public IMarkedIons getIonTraces() {

			// currently only traces to include is supported but we might later support EXCLUDE also...
			return new MarkedIons(traces, IonMarkMode.INCLUDE);
		}

		@Override
		public PeakType gePeakType() {

			return peakType;
		}

		@Override
		public boolean isOptimizeRange() {

			return optimizeRange;
		}
	}
}