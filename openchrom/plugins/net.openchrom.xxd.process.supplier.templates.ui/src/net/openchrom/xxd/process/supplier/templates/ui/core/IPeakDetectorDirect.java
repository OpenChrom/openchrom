/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.core;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;

public interface IPeakDetectorDirect {

	default int getStartRetentionTime(IChromatogramSelection<?, ?> chromatogramSelection, int offset) {

		IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
		if(chromatogramSelection.getStartRetentionTime() == chromatogram.getStartRetentionTime()) {
			return chromatogram.getStartRetentionTime() + offset;
		} else {
			return chromatogramSelection.getStartRetentionTime();
		}
	}

	default int getStopRetentionTime(IChromatogramSelection<?, ?> chromatogramSelection, int offset) {

		IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
		if(chromatogramSelection.getStopRetentionTime() == chromatogram.getStopRetentionTime()) {
			return chromatogram.getStopRetentionTime() - offset;
		} else {
			return chromatogramSelection.getStopRetentionTime();
		}
	}
}
