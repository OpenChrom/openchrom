/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.AbstractPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.IPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.quantitation.InternalStandard;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.AssignerSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.StandardsAssignerSettings;

public class StandardsAssigner extends AbstractPeakQuantifier implements IPeakQuantifier {

	private static final Logger logger = Logger.getLogger(StandardsAssigner.class);

	@Override
	public IProcessingInfo quantify(List<IPeak> peaks, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof StandardsAssignerSettings) {
				StandardsAssignerSettings standardsAssignerSettings = (StandardsAssignerSettings)settings;
				for(AssignerSetting assignerSetting : standardsAssignerSettings.getAssignerSettings()) {
					assignPeak(peaks, assignerSetting);
				}
			} else {
				processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo quantify(IPeak peak, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<IPeak>();
		peaks.add(peak);
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo quantify(IPeak peak, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<IPeak>();
		peaks.add(peak);
		StandardsAssignerSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo quantify(List<IPeak> peaks, IProgressMonitor monitor) {

		StandardsAssignerSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	private StandardsAssignerSettings getSettings() {

		StandardsAssignerSettings settings = new StandardsAssignerSettings();
		settings.setAssignerSettings(PreferenceSupplier.getSettings(PreferenceSupplier.P_STANDARDS_ASSIGNER_LIST, ""));
		return settings;
	}

	private void assignPeak(List<? extends IPeak> peaks, AssignerSetting assignerSetting) {

		int startRetentionTime = (int)(assignerSetting.getStartRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		int stopRetentionTime = (int)(assignerSetting.getStopRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		//
		try {
			if(startRetentionTime > 0 && startRetentionTime < stopRetentionTime) {
				for(IPeak peak : peaks) {
					if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
						if(peak.getIntegratedArea() > 0.0d) {
							String name = assignerSetting.getName();
							double concentration = assignerSetting.getConcentration();
							String concentrationUnit = assignerSetting.getConcentrationUnit();
							double responseFactor = assignerSetting.getResponseFactor();
							InternalStandard internalStandard = new InternalStandard(name, concentration, concentrationUnit, responseFactor);
							peak.addInternalStandard(internalStandard);
						} else {
							logger.warn("The peak area is 0.");
						}
					}
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
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

	private IProcessingInfo validate(List<IPeak> peaks, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		if(peaks == null) {
			processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The peaks selection must not be null.");
		}
		if(settings == null) {
			processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The settings must not be null.");
		}
		return processingInfo;
	}
}
