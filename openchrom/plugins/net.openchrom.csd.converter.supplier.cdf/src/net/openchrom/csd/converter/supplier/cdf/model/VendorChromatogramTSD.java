/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see http://www.gnu.org/licenses.
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
