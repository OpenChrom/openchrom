/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - paint comment on chart
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;

public class PeakDetectorControl extends Composite {

	private DetectorController controller = new DetectorController();

	public PeakDetectorControl(Composite parent, int style) {

		super(parent, style);
		createControl();
		setData("org.eclipse.e4.ui.css.CssClassName", "PeakDetectorControl");
	}

	public void setInput(ProcessDetectorSettings processSettings) {

		controller.setInput(processSettings);
	}

	private void createControl() {

		setLayout(new FillLayout());
		create(this);
	}

	private void create(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		//
		createListSection(sashForm);
		controller.createPeakDetectorChart(sashForm);
		//
		sashForm.setWeights(350, 650);
	}

	private void createListSection(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		//
		controller.createExtendedDetectorUI(sashForm);
		controller.createExtendedPeaksUI(sashForm);
		//
		sashForm.setWeights(700, 300);
	}
}