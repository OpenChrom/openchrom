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
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class IonMeasurementChartUI extends Composite {

	private IonMeasurementSpectrumUI ionMeasurementSpectrumUI;

	public IonMeasurementChartUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {

		setLayout(new FillLayout());
		ionMeasurementSpectrumUI = new IonMeasurementSpectrumUI(this, SWT.NONE);
	}

	public void update(ICalibratedVendorMassSpectrum massSpectrum) {

		if(massSpectrum != null) {
			ionMeasurementSpectrumUI.update(massSpectrum, true);
		}
	}
}
