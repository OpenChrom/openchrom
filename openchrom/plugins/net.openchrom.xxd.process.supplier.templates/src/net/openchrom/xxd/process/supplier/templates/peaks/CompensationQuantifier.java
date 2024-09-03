/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.xxd.quantitation.core.AbstractPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.xxd.quantitation.core.IPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.xxd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.implementation.QuantitationEntry;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.CompensationQuantifierSettings;
import net.openchrom.xxd.process.supplier.templates.settings.StandardsAssignerSettings;

public class CompensationQuantifier extends AbstractPeakQuantifier implements IPeakQuantifier {

	private static final String LABEL_ADJUSTED = " [adjusted]";

	@Override
	public IProcessingInfo<?> quantify(List<IPeak> peaks, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<?> processingInfo = validate(peaks, settings);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof CompensationQuantifierSettings compensationQuantifierSettings) {
				for(CompensationSetting compensationSetting : compensationQuantifierSettings.getCompensationSettingsList()) {
					compensateQuantification(peaks, compensationSetting);
				}
			} else {
				processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<?> quantify(IPeak peak, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<>();
		peaks.add(peak);
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> quantify(IPeak peak, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<>();
		peaks.add(peak);
		CompensationQuantifierSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> quantify(List<IPeak> peaks, IProgressMonitor monitor) {

		CompensationQuantifierSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	private CompensationQuantifierSettings getSettings() {

		CompensationQuantifierSettings settings = new CompensationQuantifierSettings();
		settings.setCompensationSettings(PreferenceSupplier.getSettings(PreferenceSupplier.P_COMPENSATION_QUANTIFIER_LIST, PreferenceSupplier.DEF_COMPENSATION_QUANTIFIER_LIST));
		return settings;
	}

	private void compensateQuantification(List<? extends IPeak> peaks, CompensationSetting compensationSetting) {

		double concentration = getMeasuredConcentration(peaks, compensationSetting);
		double expectedConcentration = compensationSetting.getExpectedConcentration();
		boolean adjustQuantitationEntry = compensationSetting.isAdjustQuantitationEntry();
		//
		if(concentration > 0 && expectedConcentration > 0) {
			/*
			 * Calculate the factor and create an adjusted quantitation entry.
			 */
			double compensationFactor = 100.0d / concentration * expectedConcentration / 100.0d;
			for(IPeak peak : peaks) {
				//
				List<IQuantitationEntry> quantitationEntriesAdd = new ArrayList<>();
				List<IQuantitationEntry> quantitationEntriesDelete = (adjustQuantitationEntry) ? new ArrayList<>() : null;
				//
				String name = compensationSetting.getName();
				String targetUnit = compensationSetting.getTargetUnit();
				//
				for(IQuantitationEntry quantitationEntry : getQuantitationEntries(peak, name, targetUnit)) {
					quantitationEntriesAdd.add(createAdjustedQuantitationEntry(compensationSetting, compensationFactor, quantitationEntry));
					if(adjustQuantitationEntry) {
						quantitationEntriesDelete.add(quantitationEntry);
					}
				}
				//
				if(quantitationEntriesDelete != null) {
					peak.removeQuantitationEntries(quantitationEntriesDelete);
				}
				//
				peak.addAllQuantitationEntries(quantitationEntriesAdd);
			}
		}
	}

	private IQuantitationEntry createAdjustedQuantitationEntry(CompensationSetting compensationSetting, double compensationFactor, IQuantitationEntry quantitationEntry) {

		double adjustedConcentration = quantitationEntry.getConcentration() * compensationFactor;
		String name = compensationSetting.isAdjustQuantitationEntry() ? compensationSetting.getName() : compensationSetting.getName() + LABEL_ADJUSTED;
		String group = quantitationEntry.getGroup();
		String unit = quantitationEntry.getConcentrationUnit();
		/*
		 * Keep the original unit.
		 */
		IQuantitationEntry adjustedQuantitationEntry = new QuantitationEntry(name, group, adjustedConcentration, unit, quantitationEntry.getArea());
		adjustedQuantitationEntry.setChemicalClass(quantitationEntry.getChemicalClass());
		adjustedQuantitationEntry.setCalibrationMethod(quantitationEntry.getCalibrationMethod());
		adjustedQuantitationEntry.setUsedCrossZero(quantitationEntry.getUsedCrossZero());
		adjustedQuantitationEntry.setDescription(quantitationEntry.getDescription());
		adjustedQuantitationEntry.setSignal(quantitationEntry.getSignal());
		/*
		 * Add the adjustment information.
		 */
		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();
		StringBuilder builder = new StringBuilder();
		builder.append("Adjusted with factor ");
		builder.append(decimalFormat.format(compensationFactor));
		builder.append(" based on ");
		builder.append(compensationSetting.getInternalStandard());
		builder.append(" [");
		builder.append(compensationSetting.getConcentrationUnit());
		builder.append("]");
		adjustedQuantitationEntry.appendDescription(builder.toString());
		//
		return adjustedQuantitationEntry;
	}

	private double getMeasuredConcentration(List<? extends IPeak> peaks, CompensationSetting compensationSetting) {

		double concentration = 0.0d;
		exitloop:
		for(IPeak peak : peaks) {
			if(isIdentifierMatch(peak, compensationSetting.getInternalStandard())) {
				String internalStandard = compensationSetting.getInternalStandard();
				String concentrationUnit = compensationSetting.getConcentrationUnit();
				List<IQuantitationEntry> quantitationEntries = getQuantitationEntries(peak, internalStandard, concentrationUnit);
				if(!quantitationEntries.isEmpty()) {
					for(IQuantitationEntry quantitationEntry : quantitationEntries) {
						concentration = quantitationEntry.getConcentration();
					}
					concentration /= quantitationEntries.size();
					break exitloop;
				}
			}
		}
		return concentration;
	}

	private boolean isIdentifierMatch(IPeak peak, String name) {

		Set<IIdentificationTarget> targets = peak.getTargets();
		for(IIdentificationTarget target : targets) {
			if(target.getLibraryInformation().getName().equals(name)) {
				return true;
			}
		}
		//
		return false;
	}

	private List<IQuantitationEntry> getQuantitationEntries(IPeak peak, String name, String unit) {

		List<IQuantitationEntry> quantitationEntries = new ArrayList<>();
		//
		for(IQuantitationEntry quantitationEntry : peak.getQuantitationEntries()) {
			if(quantitationEntry.getName().equals(name)) {
				if(unit.isEmpty() || quantitationEntry.getConcentrationUnit().equals(unit)) {
					quantitationEntries.add(quantitationEntry);
				}
			}
		}
		//
		return quantitationEntries;
	}

	private IProcessingInfo<?> validate(List<IPeak> peaks, IPeakQuantifierSettings settings) {

		IProcessingInfo<?> processingInfo = new ProcessingInfo<>();
		if(peaks == null) {
			processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The peaks selection must not be null.");
		}
		if(settings == null) {
			processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The settings must not be null.");
		}
		return processingInfo;
	}
}
