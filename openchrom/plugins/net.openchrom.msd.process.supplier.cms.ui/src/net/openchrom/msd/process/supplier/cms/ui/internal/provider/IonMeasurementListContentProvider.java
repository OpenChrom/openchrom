/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
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

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class IonMeasurementListContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		if(inputElement instanceof ICalibratedVendorMassSpectrum) {
			ICalibratedVendorMassSpectrum massSpectrum = (ICalibratedVendorMassSpectrum)inputElement;
			return massSpectrum.getIonMeasurements().toArray();
		} else {
			return null;
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
}
