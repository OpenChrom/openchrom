/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;

public class ExtendedMeasurementUI extends Composite {

	private IonMeasurementListUI ionMeasurementListUI;

	public ExtendedMeasurementUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void update(ICalibratedVendorLibraryMassSpectrum spectrum) {

		ionMeasurementListUI.update(spectrum);
	}

	private void createControl() {

		setLayout(new FillLayout());
		ionMeasurementListUI = new IonMeasurementListUI(this, SWT.NONE);
	}
}
