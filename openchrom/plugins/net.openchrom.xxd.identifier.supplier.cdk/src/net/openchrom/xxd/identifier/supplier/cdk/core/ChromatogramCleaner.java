/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.chromatogram.AbstractChromatogramIdentifier;
import org.eclipse.chemclipse.chromatogram.xxd.identifier.settings.IChromatogramIdentifierSettings;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.ITargetSupplier;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import net.openchrom.xxd.identifier.supplier.cdk.settings.CleanerSettings;

public class ChromatogramCleaner extends AbstractChromatogramIdentifier {

	@Override
	public IProcessingInfo<?> identify(IChromatogramSelection<?, ?> chromatogramSelection, IChromatogramIdentifierSettings chromatogramIdentifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<?> processingInfo = validate(chromatogramSelection, chromatogramIdentifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramIdentifierSettings instanceof CleanerSettings cleanerSettings) {
				/*
				 * Settings
				 */
				IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
				int startScan = chromatogram.getScanNumber(chromatogramSelection.getStartRetentionTime());
				int stopScan = chromatogram.getScanNumber(chromatogramSelection.getStopRetentionTime());
				/*
				 * Scans
				 */
				if(cleanerSettings.isDeleteScanTargets()) {
					for(int scan = startScan; scan <= stopScan; scan++) {
						IScan supplierScan = chromatogram.getScan(scan);
						if(!supplierScan.getTargets().isEmpty()) {
							cleanTargets(supplierScan);
						}
					}
				}
				/*
				 * Peaks
				 */
				if(cleanerSettings.isDeletePeakTargets()) {
					List<IPeak> peaks = new ArrayList<>();
					for(IPeak peak : chromatogramSelection.getChromatogram().getPeaks(chromatogramSelection)) {
						peaks.add(peak);
					}
					cleanPeaks(peaks);
				}
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<?> identify(IChromatogramSelection<?, ?> chromatogramSelection, IProgressMonitor monitor) {

		CleanerSettings settings = PreferenceSupplier.getCleanerSettings();
		return identify(chromatogramSelection, settings, monitor);
	}

	private void cleanPeaks(List<IPeak> peaks) {

		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			IScan scan = peakModel.getPeakMaximum();
			if(!scan.getTargets().isEmpty()) {
				cleanTargets(scan);
			}
		}
	}

	private void cleanTargets(ITargetSupplier targetSupplier) {

		List<IIdentificationTarget> removeTargets = new ArrayList<>();
		for(IIdentificationTarget identificationTarget : targetSupplier.getTargets()) {
			ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
			String smiles = libraryInformation.getSmiles();
			if(smiles == null || smiles.isEmpty()) {
				removeTargets.add(identificationTarget);
			}
		}
		targetSupplier.getTargets().removeAll(removeTargets);
	}
}
