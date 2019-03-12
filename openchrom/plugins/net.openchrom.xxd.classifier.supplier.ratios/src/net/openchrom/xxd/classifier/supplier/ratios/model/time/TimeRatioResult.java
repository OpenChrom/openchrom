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
package net.openchrom.xxd.classifier.supplier.ratios.model.time;

import org.eclipse.chemclipse.chromatogram.msd.classifier.result.AbstractChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.ResultStatus;

public class TimeRatioResult extends AbstractChromatogramClassifierResult implements IChromatogramClassifierResult {

	private TimeRatios ratios;

	public TimeRatioResult(ResultStatus resultStatus, String description, TimeRatios ratios) {
		super(resultStatus, description);
		this.ratios = ratios;
	}

	public TimeRatios getRatios() {

		return ratios;
	}
}