/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.core;

import org.eclipse.chemclipse.chromatogram.msd.classifier.core.AbstractChromatogramClassifier;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.types.DataType;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;

public abstract class AbstractRatioClassifier extends AbstractChromatogramClassifier {

	public AbstractRatioClassifier() {

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