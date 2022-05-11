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
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.AbstractPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.core.IPeakQuantifier;
import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.StandardsReferencerSettings;
import net.openchrom.xxd.process.supplier.templates.support.RetentionIndexSupport;

@SuppressWarnings("rawtypes")
public class StandardsReferencer extends AbstractPeakQuantifier implements IPeakQuantifier {

	private static final Logger logger = Logger.getLogger(StandardsReferencer.class);

	@Override
	public IProcessingInfo<?> quantify(List<IPeak> peaks, IPeakQuantifierSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(peaks, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof StandardsReferencerSettings) {
				RetentionIndexMap retentionIndexMap = RetentionIndexSupport.getRetentionIndexMap(peaks);
				StandardsReferencerSettings standardsReferencerSettings = (StandardsReferencerSettings)settings;
				for(AssignerReference assignerReference : standardsReferencerSettings.getReferencerSettings()) {
					referencePeak(peaks, assignerReference, retentionIndexMap);
				}
			} else {
				processingInfo.addErrorMessage(StandardsReferencerSettings.DESCRIPTION, "The settings instance is wrong.");
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
		StandardsReferencerSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> quantify(List<IPeak> peaks, IProgressMonitor monitor) {

		StandardsReferencerSettings settings = getSettings();
		return quantify(peaks, settings, monitor);
	}

	private StandardsReferencerSettings getSettings() {

		StandardsReferencerSettings settings = new StandardsReferencerSettings();
		settings.setReferencerSettings(PreferenceSupplier.getSettings(PreferenceSupplier.P_STANDARDS_REFERENCER_LIST, PreferenceSupplier.DEF_STANDARDS_REFERENCER_LIST));
		return settings;
	}

	private void referencePeak(List<? extends IPeak> peaks, AssignerReference setting, RetentionIndexMap retentionIndexMap) {

		int startRetentionTime = setting.getRetentionTimeStart(retentionIndexMap);
		int stopRetentionTime = setting.getRetentionTimeStop(retentionIndexMap);
		String quantitationReference = setting.getInternalStandard();
		String identifierReference = setting.getIdentifier();
		//
		try {
			if(isValidRetentionTimeRange(startRetentionTime, stopRetentionTime)) {
				/*
				 * 10.52 | 10.63 | Toluene |
				 * 10.52 | 10.63 | Toluene | Styrene
				 */
				for(IPeak peak : peaks) {
					if(isPeakMatch(peak, startRetentionTime, stopRetentionTime)) {
						if("".equals(identifierReference)) {
							/*
							 * * 10.52 | 10.63 | Toluene |
							 * All peaks in the range of RT 10.52 to 10.63 will be quantified against Toluene.
							 */
							peak.addQuantitationReference(quantitationReference);
						} else {
							/*
							 * 10.52 | 10.63 | Toluene | Styrene
							 * All peaks in the range of RT 10.52 to 10.63 will be quantified against Toluene
							 * if they are at least identified as Styrene.
							 */
							if(isIdentifierMatch(peak, identifierReference)) {
								peak.addQuantitationReference(quantitationReference);
							}
						}
					}
				}
			} else {
				/*
				 * 0.0 | 0.0 | Toluene | Styrene
				 * All peaks will be quantified against Toluene
				 * if they are at least identified as Styrene.
				 */
				for(IPeak peak : peaks) {
					if(isIdentifierMatch(peak, identifierReference)) {
						peak.addQuantitationReference(quantitationReference);
					}
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}

	private boolean isIdentifierMatch(IPeak peak, String identifierReference) {

		Set<IIdentificationTarget> targets = peak.getTargets();
		for(IIdentificationTarget target : targets) {
			if(target.getLibraryInformation().getName().equals(identifierReference)) {
				return true;
			}
		}
		//
		return false;
	}

	private boolean isValidRetentionTimeRange(int startRetentionTime, int stopRetentionTime) {

		return startRetentionTime > 0 && startRetentionTime < stopRetentionTime;
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
			processingInfo.addErrorMessage(StandardsReferencerSettings.DESCRIPTION, "The peaks selection must not be null.");
		}
		if(settings == null) {
			processingInfo.addErrorMessage(StandardsReferencerSettings.DESCRIPTION, "The settings must not be null.");
		}
		return processingInfo;
	}
}
