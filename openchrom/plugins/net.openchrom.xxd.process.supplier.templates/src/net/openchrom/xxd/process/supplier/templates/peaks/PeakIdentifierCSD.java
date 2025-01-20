/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
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
