/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Table;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class IonMeasurementListContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		if(inputElement instanceof ICalibratedVendorMassSpectrum massSpectrum) {
			return massSpectrum.getIonMeasurements().toArray();
		} else if(inputElement instanceof ICalibratedVendorLibraryMassSpectrum massSpectrum) {
			return massSpectrum.getIons().toArray();
		} else {
			return null;
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		Object ob;
		String column1Header;
		if(newInput instanceof CalibratedVendorMassSpectrum) {
			column1Header = "signal";
		} else {
			column1Header = "abundance";
		}
		if(newInput instanceof CalibratedVendorLibraryMassSpectrum temp) {
			String signalUnits = temp.getSignalUnits();
			if((null != signalUnits) && (0 < signalUnits.length())) {
				column1Header = column1Header + ", " + signalUnits;
			}
		}
		ob = viewer.getControl();
		if(ob instanceof Table tab) {
			tab.getColumn(1).setText(column1Header);
		}
	}
}
