/*******************************************************************************
 * Copyright (c) 2019, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.model.qual;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.AbstractChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.ResultStatus;

public class QualRatioResult extends AbstractChromatogramClassifierResult {

	private QualRatios qualRatios;

	public QualRatioResult(ResultStatus resultStatus, String description, QualRatios qualRatios) {

		super(resultStatus, description);
		this.qualRatios = qualRatios;
	}

	public QualRatios getQualRatios() {

		return qualRatios;
	}
}