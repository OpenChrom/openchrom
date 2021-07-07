/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.model;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.tsd.model.core.AbstractChromatogramTSD;

public class VendorChromatogramTSD extends AbstractChromatogramTSD {

	private static final long serialVersionUID = -3494023840304908976L;

	@Override
	public String getName() {

		return extractNameFromFile("GCxGC");
	}

	@Override
	public String getLabelAxisX() {

		return "Retention Time Column 1 [min]";
	}

	@Override
	public String getLabelAxisY() {

		return "Retention Time Column 2 [sec]";
	}

	@Override
	public boolean isType2() {

		return true;
	}

	@Override
	public double getPeakIntegratedArea() {

		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void fireUpdate(IChromatogramSelection chromatogramSelection) {

	}
}
