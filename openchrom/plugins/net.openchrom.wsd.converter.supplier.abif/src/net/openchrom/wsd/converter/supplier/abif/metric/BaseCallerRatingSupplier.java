/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.metric;

import java.util.OptionalDouble;

import org.eclipse.chemclipse.model.identifier.AbstractComparisonRatingSupplier;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;

public class BaseCallerRatingSupplier extends AbstractComparisonRatingSupplier {

	private static final long serialVersionUID = 3268514022437007286L;

	@Override
	public float getScore() {

		IComparisonResult comparisonResult = getComparisonResult();
		OptionalDouble phred = comparisonResult.getMetric(BaseCallerMetrics.PHRED_QUALITY_SCORE);
		if(phred.isEmpty()) {
			return Float.NaN;
		}
		double q = phred.getAsDouble();
		if(q < 20) {
			return 10.0f; // bad
		} else if(q < 30) {
			return 70.0f; // average
		} else if(q < 62) {
			return 80.0f; // good
		} else {
			return 100.0f; // very good
		}
	}
}
