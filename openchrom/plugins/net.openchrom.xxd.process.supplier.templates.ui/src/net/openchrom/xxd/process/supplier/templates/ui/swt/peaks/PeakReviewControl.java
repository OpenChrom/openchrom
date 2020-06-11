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

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class PeakReviewControl extends Composite {

	private ReviewController controller = new ReviewController(this);
	//
	private SashForm sashFormMain;
	private SashForm sashFormDetails;
	private boolean showComparisonUI = false;

	public PeakReviewControl(Composite parent, int style) {

		super(parent, style);
		sashFormMain = createControl();
		updateWidgets();
	}

	public void setInput(ProcessReviewSettings processSettings) {

		controller.setInput(processSettings);
		if(processSettings != null) {
			showComparisonUI = processSettings.getChromatogram() instanceof IChromatogramMSD;
		}
		updateWidgets();
	}

	public void updateWidgets() {

		/*
		 * Details
		 */
		if(PreferenceSupplier.isShowReviewDetails()) {
			sashFormMain.setWeights(new int[]{600, 400});
		} else {
			sashFormMain.setWeights(new int[]{1000, 0});
		}
		/*
		 * Show Comparison
		 */
		if(showComparisonUI) {
			sashFormDetails.setWeights(new int[]{500, 500});
		} else {
			sashFormDetails.setWeights(new int[]{1000, 0});
		}
	}

	private SashForm createControl() {

		setLayout(new FillLayout());
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		//
		createNavigateSection(sashForm);
		sashFormDetails = createDetailsSection(sashForm);
		//
		return sashForm;
	}

	private SashForm createNavigateSection(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		//
		createReviewSection(sashForm);
		controller.createPeakDetectorChart(sashForm);
		//
		sashForm.setWeights(new int[]{350, 650});
		//
		return sashForm;
	}

	private void createReviewSection(Composite composite) {

		SashForm sashForm = new SashForm(composite, SWT.VERTICAL);
		//
		controller.createProcessReviewUI(sashForm);
		controller.createExtendedPeaksUI(sashForm);
	}

	private SashForm createDetailsSection(Composite parent) {

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		//
		controller.createExtendedTargetsUI(sashForm);
		controller.createExtendedComparisonUI(sashForm);
		//
		return sashForm;
	}
}
