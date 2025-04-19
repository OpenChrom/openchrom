/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.xxd.integrator.core.peaks.AbstractPeakIntegrator;
import org.eclipse.chemclipse.chromatogram.xxd.integrator.core.peaks.PeakIntegrator;
import org.eclipse.chemclipse.chromatogram.xxd.integrator.core.settings.peaks.IPeakIntegrationSettings;
import org.eclipse.chemclipse.chromatogram.xxd.integrator.result.IPeakIntegrationResults;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakIntegrationSettings;
import net.openchrom.xxd.process.supplier.templates.support.RetentionIndexSupport;

public class TemplateIntegrator extends AbstractPeakIntegrator {

	@Override
	public IProcessingInfo<IPeakIntegrationResults> integrate(List<? extends IPeak> peaks, IPeakIntegrationSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<IPeakIntegrationResults> processingInfo = super.validate(peaks, settings);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakIntegrationSettings peakIntegrationSettings) {
				RetentionIndexMap retentionIndexMap = RetentionIndexSupport.getRetentionIndexMap(peaks);
				List<IntegratorSetting> integratorSettings = peakIntegrationSettings.getIntegrationSettingsList();
				for(IntegratorSetting setting : integratorSettings) {
					/*
					 * Bounds
					 */
					int startRetentionTime = setting.getRetentionTimeStart(retentionIndexMap);
					int stopRetentionTime = setting.getRetentionTimeStop(retentionIndexMap);
					List<IPeak> peaksToIntegrate = new ArrayList<>();
					for(IPeak peak : peaks) {
						//
						if(startRetentionTime == 0 && stopRetentionTime == 0) {
							/*
							 * Identifier is matched
							 * 0.0 | 0.0 | Styrene | Max
							 * 0.0 | 0.0 | Benzene | Trapezoid
							 */
							String identifier = setting.getIdentifier();
							if(isIdentifierMatch(peak, identifier)) {
								peaksToIntegrate.add(peak);
							}
						} else {
							/*
							 * Time range is matched
							 * 4.1 | 5.9 | | Trapezoid
							 * 6.4 | 7.4 | | Max
							 */
							if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
								peaksToIntegrate.add(peak);
							}
						}
					}
					//
					integratePeaks(peaksToIntegrate, setting.getIntegrator());
				}
				//
				// IPeakIntegrationResults peakIntegrationResults = new PeakIntegrationResults();
				// peakIntegrationResults.add(peakIntegrationResult);
				// processingInfo.setProcessingResult(peakIntegrationResults);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IPeakIntegrationResults> integrate(IPeak peak, IPeakIntegrationSettings peakIntegrationSettings, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<>();
		peaks.add(peak);
		return integrate(peaks, peakIntegrationSettings, monitor);
	}

	@Override
	public IProcessingInfo<IPeakIntegrationResults> integrate(IPeak peak, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<>();
		peaks.add(peak);
		return integrate(peaks, getSettings(), monitor);
	}

	@Override
	public IProcessingInfo<IPeakIntegrationResults> integrate(List<? extends IPeak> peaks, IProgressMonitor monitor) {

		return integrate(peaks, getSettings(), monitor);
	}

	@Override
	public IProcessingInfo<IPeakIntegrationResults> integrate(IChromatogramSelection chromatogramSelection, IPeakIntegrationSettings peakIntegrationSettings, IProgressMonitor monitor) {

		List<? extends IPeak> peaks = chromatogramSelection.getChromatogram().getPeaks(chromatogramSelection);
		return integrate(peaks, peakIntegrationSettings, monitor);
	}

	@Override
	public IProcessingInfo<IPeakIntegrationResults> integrate(IChromatogramSelection chromatogramSelection, IProgressMonitor monitor) {

		List<? extends IPeak> peaks = chromatogramSelection.getChromatogram().getPeaks(chromatogramSelection);
		return integrate(peaks, getSettings(), monitor);
	}

	private PeakIntegrationSettings getSettings() {

		PeakIntegrationSettings settings = new PeakIntegrationSettings();
		settings.setIntegrationSettings(PreferenceSupplier.getSettings(PreferenceSupplier.P_PEAK_INTEGRATOR_LIST, ""));
		return settings;
	}

	private boolean isPeakMatch(IPeak peak, int startRetentionTime, int stopRetentionTime) {

		IPeakModel peakModel = peak.getPeakModel();
		int retentionTime = peakModel.getRetentionTimeAtPeakMaximum();
		if(retentionTime >= startRetentionTime && retentionTime <= stopRetentionTime) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isIdentifierMatch(IPeak peak, String identifier) {

		Set<IIdentificationTarget> targets = peak.getTargets();
		for(IIdentificationTarget target : targets) {
			if(target.getLibraryInformation().getName().equals(identifier)) {
				return true;
			}
		}
		//
		return false;
	}

	private void integratePeaks(List<IPeak> peaks, String integrator) {

		if(!peaks.isEmpty()) {
			String integratorId;
			switch(integrator) {
				case IntegratorSetting.INTEGRATOR_NAME_MAX:
					integratorId = IntegratorSetting.INTEGRATOR_ID_MAX;
					break;
				case IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID:
					integratorId = IntegratorSetting.INTEGRATOR_ID_TRAPEZOID;
					break;
				default:
					integratorId = "";
					break;
			}
			//
			if(!"".equals(integratorId)) {
				PeakIntegrator.integrate(peaks, integratorId, new NullProgressMonitor());
			}
		}
	}
}
