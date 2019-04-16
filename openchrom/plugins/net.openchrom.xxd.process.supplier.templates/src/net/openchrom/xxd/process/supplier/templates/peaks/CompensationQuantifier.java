/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.AbstractPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.IPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.implementation.QuantitationEntry;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.CompensationQuantifierSettings;
import net.openchrom.xxd.process.supplier.templates.settings.StandardsAssignerSettings;

@SuppressWarnings("rawtypes")
public class CompensationQuantifier extends AbstractPeakQuantifier implements IPeakQuantifier {

	private static final String LABEL_ADJUSTED = " [adjusted]";

	@Override
	public IProcessingInfo quantify(List<IPeak> peaks, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof CompensationQuantifierSettings) {
				CompensationQuantifierSettings compensationQuantifierSettings = (CompensationQuantifierSettings)settings;
				for(CompensationSetting compensationSetting : compensationQuantifierSettings.getCompensationSettings()) {
					compensateQuantification(peaks, compensationSetting);
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
		CompensationQuantifierSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo quantify(List<IPeak> peaks, IProgressMonitor monitor) {

		CompensationQuantifierSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	private CompensationQuantifierSettings getSettings() {

		CompensationQuantifierSettings settings = new CompensationQuantifierSettings();
		settings.seCompensationSettings(PreferenceSupplier.getSettings(PreferenceSupplier.P_COMPENSATION_QUANTIFIER_LIST, PreferenceSupplier.DEF_COMPENSATION_QUANTIFIER_LIST));
		return settings;
	}

	private void compensateQuantification(List<? extends IPeak> peaks, CompensationSetting compensationSetting) {

		double concentration = getMeasuredConcentration(peaks, compensationSetting);
		double expectedConcentration = compensationSetting.getExpectedConcentration();
		if(concentration > 0 && expectedConcentration > 0) {
			/*
			 * Calculate the factor and create an adjusted quantitation entry.
			 */
			double compensationFactor = 100.0d / concentration * expectedConcentration / 100.0d;
			for(IPeak peak : peaks) {
				IQuantitationEntry quantitationEntry = getQuantitationEntry(peak, compensationSetting.getName(), compensationSetting.getConcentrationUnit());
				if(quantitationEntry != null) {
					double adjustedConcentration = quantitationEntry.getConcentration() * compensationFactor;
					peak.addQuantitationEntry(createAdjustedQuantitationEntry(compensationSetting, adjustedConcentration, quantitationEntry.getArea()));
				}
			}
		}
	}

	private IQuantitationEntry createAdjustedQuantitationEntry(CompensationSetting compensationSetting, double adjustedConcentration, double area) {

		String name = compensationSetting.getName() + LABEL_ADJUSTED;
		return new QuantitationEntry(name, adjustedConcentration, compensationSetting.getConcentrationUnit(), area);
	}

	private double getMeasuredConcentration(List<? extends IPeak> peaks, CompensationSetting compensationSetting) {

		double concentration = 0.0d;
		for(IPeak peak : peaks) {
			if(isIdentifierMatch(peak, compensationSetting)) {
				IQuantitationEntry quantitationEntry = getQuantitationEntry(peak, compensationSetting.getInternalStandard(), compensationSetting.getConcentrationUnit());
				if(quantitationEntry != null) {
					concentration = quantitationEntry.getConcentration();
				}
			}
		}
		return concentration;
	}

	private boolean isIdentifierMatch(IPeak peak, CompensationSetting compensationSetting) {

		Set<IIdentificationTarget> targets = peak.getTargets();
		for(IIdentificationTarget target : targets) {
			if(target.getLibraryInformation().getName().equals(compensationSetting.getName())) {
				return true;
			}
		}
		//
		return false;
	}

	private IQuantitationEntry getQuantitationEntry(IPeak peak, String name, String concentrationUnit) {

		for(IQuantitationEntry quantitationEntry : peak.getQuantitationEntries()) {
			if(quantitationEntry.getName().equals(name)) {
				if(quantitationEntry.getConcentrationUnit().equals(concentrationUnit)) {
					return quantitationEntry;
				}
			}
		}
		return null;
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
