/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.quant;

import org.eclipse.chemclipse.chromatogram.msd.classifier.result.AbstractChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.ResultStatus;

public class QuantRatioResult extends AbstractChromatogramClassifierResult implements IChromatogramClassifierResult {

	private QuantRatios ratios;

	public QuantRatioResult(ResultStatus resultStatus, String description, QuantRatios ratios) {
		super(resultStatus, description);
		this.ratios = ratios;
	}

	public QuantRatios getRatios() {

		return ratios;
	}
}