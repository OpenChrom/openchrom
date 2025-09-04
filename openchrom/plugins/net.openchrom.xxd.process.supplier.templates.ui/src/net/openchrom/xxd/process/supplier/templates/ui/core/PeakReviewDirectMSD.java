/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.core;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.identifier.peak.IPeakIdentifierMSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.model.identifier.IPeakIdentificationResults;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class PeakReviewDirectMSD extends AbstractPeakReview implements IPeakIdentifierMSD {

	@Override
	public IProcessingInfo<IPeakIdentificationResults> identify(List<? extends IPeakMSD> peaks, IPeakIdentifierSettingsMSD peakIdentifierSettings, IProgressMonitor monitor) {

		/*
		 * Settings are applied before running the process
		 * and set to null on purpose, to allow a dynamic review.
		 */
		applyPeakReviewDirectSettings(peakIdentifierSettings);
		return runProcess(peaks, null, "PeakReviewDirectMSD", monitor);
	}
}