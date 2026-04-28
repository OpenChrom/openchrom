/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.cdf.model;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.tsd.model.core.AbstractChromatogramTSD;
import org.eclipse.chemclipse.tsd.model.core.TypeTSD;

public class VendorChromatogramTSD extends AbstractChromatogramTSD {

	private static final long serialVersionUID = -3494023840304908976L;

	@Override
	public String getLabelAxisX() {

		return "Retention Time Column 1 [min]";
	}

	@Override
	public String getLabelAxisY() {

		return "Column 2 [scans]";
	}

	@Override
	public TypeTSD getTypeTSD() {

		return TypeTSD.GCxGC_MS;
	}

	@Override
	public double getPeakIntegratedArea() {

		return 0;
	}

	@Override
	public void fireUpdate(IChromatogramSelection chromatogramSelection) {

	}
}
