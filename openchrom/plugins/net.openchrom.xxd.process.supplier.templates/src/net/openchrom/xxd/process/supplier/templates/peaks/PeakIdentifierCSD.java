/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.identifier.peak.IPeakIdentifierCSD;
import org.eclipse.chemclipse.chromatogram.csd.identifier.settings.IPeakIdentifierSettingsCSD;
import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.model.identifier.IPeakIdentificationResults;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.support.literature.LiteratureReference;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.support.RetentionIndexSupport;

public class PeakIdentifierCSD extends AbstractPeakIdentifier implements IPeakIdentifierCSD {

	@Override
	public IProcessingInfo<IPeakIdentificationResults> identify(List<? extends IPeakCSD> peaks, IPeakIdentifierSettingsCSD settings, IProgressMonitor monitor) {

		RetentionIndexMap retentionIndexMap = RetentionIndexSupport.getRetentionIndexMap(peaks);
		return applyIdentifier(peaks, settings, retentionIndexMap, monitor);
	}

	@Override
	public List<LiteratureReference> getLiteratureReferences() {

		return null;
	}
}
