/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakProcessSettings;

public class TemplatePeakDetectorUI extends Composite {

	private PeakDetectorControl peakDetectorControl;
	private PeakDetectorChart peakDetectorChart;

	public TemplatePeakDetectorUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setInput(PeakProcessSettings peakProcessSettings) {

		peakDetectorControl.setInput(peakProcessSettings);
	}

	private void createControl() {

		setLayout(new FillLayout());
		//
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginLeft = 0;
		gridLayout.marginRight = 0;
		composite.setLayout(gridLayout);
		//
		peakDetectorControl = createPeakDetectorControl(composite);
		peakDetectorChart = createPeakDetectorChart(composite);
		/*
		 * Link control and chart.
		 */
		peakDetectorControl.setPeakDetectorChart(peakDetectorChart);
	}

	private PeakDetectorControl createPeakDetectorControl(Composite parent) {

		PeakDetectorControl control = new PeakDetectorControl(parent, SWT.NONE);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return control;
	}

	private PeakDetectorChart createPeakDetectorChart(Composite parent) {

		PeakDetectorChart chart = new PeakDetectorChart(parent, SWT.BORDER);
		chart.setLayoutData(new GridData(GridData.FILL_BOTH));
		return chart;
	}
}
