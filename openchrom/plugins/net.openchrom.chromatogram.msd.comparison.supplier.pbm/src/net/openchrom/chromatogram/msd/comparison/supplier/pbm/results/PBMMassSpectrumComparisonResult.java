/*******************************************************************************
 * Copyright (c) 2008, 2016 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.results;

import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.AbstractMassSpectrumComparisonResult;

public class PBMMassSpectrumComparisonResult extends AbstractMassSpectrumComparisonResult {

	/**
	 * Renew the UUID on change.
	 */
	private static final long serialVersionUID = -7219390056175582476L;

	public PBMMassSpectrumComparisonResult(float matchFactor, float reverseMatchFactor) {
		super(matchFactor, reverseMatchFactor);
	}
}
