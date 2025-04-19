/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.core;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.core.AbstractChromatogramClassifier;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.types.DataType;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;

public abstract class AbstractRatioClassifier extends AbstractChromatogramClassifier {

	protected AbstractRatioClassifier() {

		super(DataType.MSD);
	}

	public boolean isPeakMatch(IPeak peak, IPeakRatio peakRatio) {

		if(peak != null) {
			for(IIdentificationTarget identificationTarget : peak.getTargets()) {
				if(identificationTarget.getLibraryInformation().getName().equals(peakRatio.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}