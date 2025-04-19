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

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;

public interface IPeakDetectorDirect {

	default int getStartRetentionTime(IChromatogramSelection chromatogramSelection, int offset) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();
		if(chromatogramSelection.getStartRetentionTime() == chromatogram.getStartRetentionTime()) {
			return chromatogram.getStartRetentionTime() + offset;
		} else {
			return chromatogramSelection.getStartRetentionTime();
		}
	}

	default int getStopRetentionTime(IChromatogramSelection chromatogramSelection, int offset) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();
		if(chromatogramSelection.getStopRetentionTime() == chromatogram.getStopRetentionTime()) {
			return chromatogram.getStopRetentionTime() - offset;
		} else {
			return chromatogramSelection.getStopRetentionTime();
		}
	}
}
