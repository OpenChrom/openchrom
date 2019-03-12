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
package net.openchrom.xxd.classifier.supplier.ratios.model.trace;

import org.eclipse.chemclipse.chromatogram.msd.classifier.result.AbstractChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.ResultStatus;

public class TraceRatioResult extends AbstractChromatogramClassifierResult implements IChromatogramClassifierResult {

	private TraceRatios ratios;

	public TraceRatioResult(ResultStatus resultStatus, String description, TraceRatios ratios) {
		super(resultStatus, description);
		this.ratios = ratios;
	}

	public TraceRatios getRatios() {

		return ratios;
	}
}