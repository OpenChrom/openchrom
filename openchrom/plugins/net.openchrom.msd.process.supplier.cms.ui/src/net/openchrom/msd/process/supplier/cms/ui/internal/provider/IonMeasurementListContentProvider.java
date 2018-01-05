/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
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

		if(inputElement instanceof ICalibratedVendorMassSpectrum) {
			ICalibratedVendorMassSpectrum massSpectrum = (ICalibratedVendorMassSpectrum)inputElement;
			return massSpectrum.getIonMeasurements().toArray();
		} else if(inputElement instanceof ICalibratedVendorLibraryMassSpectrum) {
			ICalibratedVendorLibraryMassSpectrum massSpectrum = (ICalibratedVendorLibraryMassSpectrum)inputElement;
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
		if(newInput instanceof CalibratedVendorLibraryMassSpectrum) {
			CalibratedVendorLibraryMassSpectrum temp = (CalibratedVendorLibraryMassSpectrum)newInput;
			String signalUnits = temp.getSignalUnits();
			if((null != signalUnits) && (0 < signalUnits.length())) {
				column1Header = column1Header + ", " + signalUnits;
			}
		}
		ob = viewer.getControl();
		if(ob instanceof Table) {
			Table tab = (Table)ob;
			tab.getColumn(1).setText(column1Header);
		}
	}
}
