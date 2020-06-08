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
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class PeakReviewControl extends Composite {

	private ReviewController controller = new ReviewController();

	public PeakReviewControl(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setInput(ProcessReviewSettings processSettings) {

		controller.setInput(processSettings);
	}

	private void createControl() {

		setLayout(new FillLayout());
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		//
		createChromatogramSection(sashForm);
		createDetailsSection(sashForm);
	}

	private void createChromatogramSection(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		//
		controller.createProcessReviewUI(sashForm);
		controller.createPeakDetectorChart(sashForm);
		//
		sashForm.setWeights(new int[]{350, 650});
	}

	private void createDetailsSection(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		//
		controller.createExtendedPeaksUI(sashForm);
		controller.createExtendedTargetsUI(sashForm);
		controller.createExtendedComparisonUI(sashForm);
	}
}
