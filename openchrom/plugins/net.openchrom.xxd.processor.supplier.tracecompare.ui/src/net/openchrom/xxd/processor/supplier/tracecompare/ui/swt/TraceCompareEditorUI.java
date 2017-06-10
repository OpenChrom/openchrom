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

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TraceCompareEditorUI extends Composite {

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	private void createControl() {

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Sample
		 */
		Text textSamplePath = new Text(composite, SWT.BORDER);
		textSamplePath.setLayoutData(getGridData(2, GridData.FILL_HORIZONTAL));
		//
		Button buttonSample = new Button(composite, SWT.PUSH);
		buttonSample.setText("Select");
		buttonSample.setLayoutData(getGridDataButton());
		buttonSample.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CHROMATOGRAM, IApplicationImage.SIZE_16x16));
		/*
		 * DropDown References
		 */
		Combo comboReferences = new Combo(composite, SWT.READ_ONLY);
		comboReferences.setItems(new String[]{"Reference A_2017002", "Reference A_2016004", "Reference B_2017002", "Reference C_2017002"});
		comboReferences.setLayoutData(getGridData(2, GridData.FILL_HORIZONTAL));
		/*
		 * Settings
		 */
		Button buttonSettings = new Button(composite, SWT.PUSH);
		buttonSettings.setText("Settings");
		buttonSettings.setLayoutData(getGridDataButton());
		buttonSettings.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		/*
		 * Identification
		 */
		Label label = new Label(composite, SWT.NONE);
		label.setText("General Notes:");
		//
		Text textGeneralNotes = new Text(composite, SWT.BORDER);
		textGeneralNotes.setLayoutData(getGridData(1, GridData.FILL_HORIZONTAL));
		//
		Button buttonSave = new Button(composite, SWT.PUSH);
		buttonSave.setText("Save");
		buttonSave.setLayoutData(getGridDataButton());
		buttonSave.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SAVE, IApplicationImage.SIZE_16x16));
		/*
		 * Traces
		 */
		Composite compositeTraces = new Composite(composite, SWT.NONE);
		compositeTraces.setLayoutData(getGridData(2, GridData.FILL_HORIZONTAL));
		compositeTraces.setLayout(new FillLayout());
		//
		Button checkBoxTotalSignal = new Button(compositeTraces, SWT.CHECK);
		checkBoxTotalSignal.setText("Total Signal");
		checkBoxTotalSignal.setSelection(true);
		//
		for(int i = 200; i <= 208; i++) {
			Button checkBoxSelectedSignal = new Button(compositeTraces, SWT.CHECK);
			checkBoxSelectedSignal.setText(i + " nm");
			checkBoxSelectedSignal.setSelection(true);
		}
		//
		Button buttonTraces = new Button(composite, SWT.PUSH);
		buttonTraces.setText("Traces");
		buttonTraces.setLayoutData(getGridDataButton());
		buttonTraces.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		/*
		 * Comparison
		 */
		Composite compositeTraceData = new Composite(composite, SWT.BORDER);
		compositeTraceData.setLayoutData(getGridData(3, GridData.FILL_BOTH));
		compositeTraceData.setLayout(new FillLayout());
		//
		String sample = "M_20170609";
		String reference = "B_2017002";
		//
		TraceDataComparisonUI traceDataTotalSignal = new TraceDataComparisonUI(compositeTraceData, SWT.NONE);
		traceDataTotalSignal.setTrace("Total Signal", sample, reference);
		//
		for(int i = 203; i <= 203; i++) {
			TraceDataComparisonUI traceDataSelectedSignal = new TraceDataComparisonUI(compositeTraceData, SWT.NONE);
			traceDataSelectedSignal.setTrace(i + " nm", sample, reference);
		}
	}

	private GridData getGridData(int horizontalSpan, int style) {

		GridData gridData = new GridData(style);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	private GridData getGridDataButton() {

		GridData gridData = new GridData();
		gridData.widthHint = 120;
		return gridData;
	}
}
