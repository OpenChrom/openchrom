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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class TraceDataComparisonUI extends Composite {

	private Button identificationButton;
	private TraceDataUI sampleDataUI;
	private TraceDataUI referenceDataUI;
	private Text commentsText;

	public TraceDataComparisonUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setTrace(String trace, String sample, String reference) {

		identificationButton.setText("Trace to match: " + trace + " (" + sample + " vs. " + reference + ")");
	}

	private void createControl() {

		setLayout(new GridLayout(1, true));
		//
		identificationButton = new Button(this, SWT.CHECK);
		identificationButton.setText("matched");
		identificationButton.setSelection(false);
		identificationButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		identificationButton.setBackground(Colors.YELLOW);
		//
		sampleDataUI = new TraceDataUI(this, SWT.NONE, true, false, false);
		sampleDataUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		referenceDataUI = new TraceDataUI(this, SWT.NONE, false, true, true);
		referenceDataUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Link both charts.
		 */
		sampleDataUI.addLinkedScrollableChart(referenceDataUI);
		referenceDataUI.addLinkedScrollableChart(sampleDataUI);
		//
		commentsText = new Text(this, SWT.BORDER | SWT.MULTI);
		commentsText.setText("");
		commentsText.setLayoutData(new GridData(GridData.FILL_BOTH));
	}
}
