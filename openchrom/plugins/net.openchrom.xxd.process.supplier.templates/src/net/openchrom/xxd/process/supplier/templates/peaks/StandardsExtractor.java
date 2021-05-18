/*******************************************************************************
 * Copyright (c) 2019, 2021 Lablicate GmbH.
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.AbstractPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.IPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.quantitation.InternalStandard;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramPeakWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.StandardsAssignerSettings;
import net.openchrom.xxd.process.supplier.templates.settings.StandardsExtractorSettings;

@SuppressWarnings("rawtypes")
public class StandardsExtractor extends AbstractPeakQuantifier implements IPeakQuantifier {

	private static final Logger logger = Logger.getLogger(StandardsExtractor.class);
	//
	private static final Pattern ISTD_PATTERN = Pattern.compile("(IS:)(\\d+)(:)(\\d+\\.?\\d{0,5})");

	/*
	 * This class is only used internally to extract
	 * the ISTD from the header misc info.
	 */
	private class Standard {

		private String referenceId = "";
		private double concentration = 0.0d;

		public Standard(String referenceId, double concentration) {

			this.referenceId = referenceId;
			this.concentration = concentration;
		}

		public String getReferenceId() {

			return referenceId;
		}

		public double getConcentration() {

			return concentration;
		}
	}

	@Override
	public IProcessingInfo<?> quantify(List<IPeak> peaks, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof StandardsExtractorSettings) {
				StandardsExtractorSettings extractorSettings = (StandardsExtractorSettings)settings;
				assignPeaks(peaks, extractorSettings);
			} else {
				processingInfo.addErrorMessage(StandardsAssignerSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<?> quantify(IPeak peak, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<IPeak>();
		peaks.add(peak);
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> quantify(IPeak peak, IProgressMonitor monitor) {

		List<IPeak> peaks = new ArrayList<IPeak>();
		peaks.add(peak);
		StandardsExtractorSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> quantify(List<IPeak> peaks, IProgressMonitor monitor) {

		StandardsExtractorSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	private StandardsExtractorSettings getSettings() {

		StandardsExtractorSettings settings = new StandardsExtractorSettings();
		settings.setConcentrationUnit(PreferenceSupplier.getStandardsExtractorConcentrationUnit());
		return settings;
	}

	private void assignPeaks(List<? extends IPeak> peaks, StandardsExtractorSettings extractorSettings) {

		try {
			IChromatogram chromatogram = getChromatogram(peaks);
			if(chromatogram != null) {
				Standard standard = extractReferenceId(chromatogram.getMiscInfo());
				if(isValidStandard(standard)) {
					for(IPeak peak : peaks) {
						IIdentificationTarget identificationTarget = getMatchedTarget(peak, standard.getReferenceId());
						if(identificationTarget != null) {
							if(peak.getIntegratedArea() > 0.0d) {
								String name = identificationTarget.getLibraryInformation().getName();
								double concentration = standard.getConcentration();
								String concentrationUnit = extractorSettings.getConcentrationUnit();
								double responseFactor = 1.0d;
								InternalStandard internalStandard = new InternalStandard(name, concentration, concentrationUnit, responseFactor);
								peak.addInternalStandard(internalStandard);
							} else {
								logger.warn("The peak area is 0.");
							}
						}
					}
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}

	private boolean isValidStandard(Standard standard) {

		return standard != null && !"".equals(standard.getReferenceId()) && standard.getConcentration() > 0.0d;
	}

	private Standard extractReferenceId(String miscInfo) {

		if(miscInfo != null) {
			Matcher matcher = ISTD_PATTERN.matcher(miscInfo.trim());
			while(matcher.find()) {
				try {
					String referenceId = matcher.group(2);
					double concentration = Double.parseDouble(matcher.group(4));
					return new Standard(referenceId, concentration);
				} catch(NumberFormatException e) {
					logger.warn(e);
				}
			}
		}
		return null;
	}

	private IChromatogram getChromatogram(List<? extends IPeak> peaks) {

		for(IPeak peak : peaks) {
			if(peak instanceof IChromatogramPeakCSD) {
				IChromatogramPeakCSD chromatogramPeakCSD = (IChromatogramPeakCSD)peak;
				if(chromatogramPeakCSD.getChromatogram() != null) {
					return chromatogramPeakCSD.getChromatogram();
				}
			} else if(peak instanceof IChromatogramPeakMSD) {
				IChromatogramPeakMSD chromatogramPeakMSD = (IChromatogramPeakMSD)peak;
				if(chromatogramPeakMSD.getChromatogram() != null) {
					return chromatogramPeakMSD.getChromatogram();
				}
			} else if(peak instanceof IChromatogramPeakWSD) {
				IChromatogramPeakWSD chromatogramPeakWSD = (IChromatogramPeakWSD)peak;
				if(chromatogramPeakWSD.getChromatogram() != null) {
					return chromatogramPeakWSD.getChromatogram();
				}
			}
		}
		return null;
	}

	private IIdentificationTarget getMatchedTarget(IPeak peak, String referenceIdentifier) {

		for(IIdentificationTarget identificationTarget : peak.getTargets()) {
			if(identificationTarget.getLibraryInformation().getReferenceIdentifier().equals(referenceIdentifier)) {
				return identificationTarget;
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
