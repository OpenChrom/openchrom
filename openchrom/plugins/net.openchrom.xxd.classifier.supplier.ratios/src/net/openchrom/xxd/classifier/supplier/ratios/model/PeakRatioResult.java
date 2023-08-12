/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.AbstractChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.ResultStatus;

public class PeakRatioResult extends AbstractChromatogramClassifierResult implements IChromatogramClassifierResult {

	private IPeakRatios<? extends IPeakRatio> peakRatios;

	public PeakRatioResult(ResultStatus resultStatus, String description, IPeakRatios<? extends IPeakRatio> peakRatios) {

		super(resultStatus, description);
		this.peakRatios = peakRatios;
	}

	public IPeakRatios<? extends IPeakRatio> getPeakRatios() {

		return peakRatios;
	}
}