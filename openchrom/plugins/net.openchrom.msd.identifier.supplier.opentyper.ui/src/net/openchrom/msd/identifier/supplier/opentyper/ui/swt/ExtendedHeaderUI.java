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
package net.openchrom.msd.identifier.supplier.opentyper.ui.swt;

import org.eclipse.chemclipse.msd.model.core.IVendorMassSpectrum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ExtendedHeaderUI extends Composite {

	private Text text;

	public ExtendedHeaderUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void updateMassSpectrum(IVendorMassSpectrum massSpectrum) {

		StringBuilder builder = new StringBuilder();
		if(massSpectrum != null) {
			addHeaderLine(builder, "Name", massSpectrum.getName());
			addHeaderLine(builder, "Type Description", massSpectrum.getMassSpectrumTypeDescription());
			addHeaderLine(builder, "File", massSpectrum.getFile().getName());
			addHeaderLine(builder, "Mass Spectrometer", "MS" + massSpectrum.getMassSpectrometer());
			addHeaderLine(builder, "Ions", Integer.toString(massSpectrum.getNumberOfIons()));
		}
		//
		text.setText(builder.toString());
	}

	private void addHeaderLine(StringBuilder builder, String key, String value) {

		builder.append(key);
		builder.append(": ");
		builder.append(value);
		builder.append("\n");
	}

	private void createControl() {

		setLayout(new FillLayout());
		text = new Text(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	}
}
