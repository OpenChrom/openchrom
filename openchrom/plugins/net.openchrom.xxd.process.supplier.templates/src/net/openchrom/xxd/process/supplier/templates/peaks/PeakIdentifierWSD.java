/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.wsd.identifier.peak.IPeakIdentifierWSD;
import org.eclipse.chemclipse.chromatogram.wsd.identifier.settings.IPeakIdentifierSettingsWSD;
import org.eclipse.chemclipse.model.identifier.IIdentificationResults;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class PeakIdentifierWSD<T> extends AbstractPeakIdentifier implements IPeakIdentifierWSD<IIdentificationResults> {

	@Override
	public IProcessingInfo<IIdentificationResults> identify(List<? extends IPeakWSD> peaks, IPeakIdentifierSettingsWSD settings, IProgressMonitor monitor) {

		if(settings == null) {
			settings = getSettings(PreferenceSupplier.P_PEAK_IDENTIFIER_LIST_WSD);
		}
		return applyIdentifier(peaks, settings, monitor);
	}
}
