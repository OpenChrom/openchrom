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
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class TraceCompareEditorUI extends Composite {

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	private void createControl() {

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Sample
		 */
		Text textSamplePath = new Text(composite, SWT.BORDER);
		textSamplePath.setLayoutData(getGridData(1, GridData.FILL_HORIZONTAL));
		//
		Button buttonSample = new Button(composite, SWT.PUSH);
		buttonSample.setText("Select");
		buttonSample.setLayoutData(getGridDataButton(-1));
		buttonSample.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CHROMATOGRAM, IApplicationImage.SIZE_16x16));
		/*
		 * DropDown References
		 */
		Combo comboReferences = new Combo(composite, SWT.READ_ONLY);
		comboReferences.setItems(new String[]{"Reference A_2017002", "Reference A_2016004", "Reference B_2017002", "Reference C_2017002"});
		comboReferences.setLayoutData(getGridData(1, GridData.FILL_HORIZONTAL));
		/*
		 * Settings
		 */
		Button buttonSettings = new Button(composite, SWT.PUSH);
		buttonSettings.setText("Settings");
		buttonSettings.setLayoutData(getGridDataButton(-1));
		buttonSettings.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		/*
		 * Comparison
		 */
		Composite compositeMain = new Composite(composite, SWT.NONE);
		compositeMain.setLayoutData(getGridData(2, GridData.FILL_BOTH));
		compositeMain.setLayout(new GridLayout(2, false));
		//
		Composite compositeTraceOverview = new Composite(compositeMain, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.widthHint = 200;
		compositeTraceOverview.setLayoutData(gridData);
		compositeTraceOverview.setLayout(new GridLayout(1, true));
		//
		Button buttonTraces = new Button(compositeTraceOverview, SWT.PUSH);
		buttonTraces.setText("Traces");
		buttonTraces.setLayoutData(getGridDataButton(GridData.FILL_HORIZONTAL));
		buttonTraces.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		//
		for(int i = 203; i <= 210; i++) {
			Button buttonTrace = new Button(compositeTraceOverview, SWT.PUSH);
			buttonTrace.setText(i + " nm");
			buttonTrace.setLayoutData(getGridDataButton(GridData.FILL_HORIZONTAL));
		}
		//
		Button buttonSave = new Button(compositeTraceOverview, SWT.PUSH);
		buttonSave.setText("Save");
		buttonSave.setLayoutData(getGridDataButton(GridData.FILL_HORIZONTAL));
		buttonSave.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SAVE, IApplicationImage.SIZE_16x16));
		//
		Button buttonNext = new Button(compositeTraceOverview, SWT.PUSH);
		buttonNext.setText("Next");
		buttonNext.setLayoutData(getGridDataButton(GridData.FILL_HORIZONTAL));
		buttonNext.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_NEXT_YELLOW, IApplicationImage.SIZE_16x16));
		//
		ScrolledComposite scrolledComposite = new ScrolledComposite(compositeMain, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayout(new GridLayout(1, true));
		scrolledComposite.setLayoutData(getGridData(1, GridData.FILL_BOTH));
		Composite compositeTraceData = new Composite(scrolledComposite, SWT.BORDER);
		compositeTraceData.setLayoutData(getGridData(3, GridData.FILL_BOTH));
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.fill = true;
		rowLayout.wrap = false;
		compositeTraceData.setLayout(rowLayout);
		//
		String sample = "M_20170609";
		String reference = "B_2017002";
		int height = Display.getDefault().getClientArea().height - 475; // The default height.
		//
		for(int i = 203; i <= 210; i++) {
			TraceDataComparisonUI traceDataSelectedSignal = new TraceDataComparisonUI(compositeTraceData, SWT.BORDER);
			traceDataSelectedSignal.setBackground(Colors.WHITE);
			traceDataSelectedSignal.setLayoutData(new RowData(SWT.DEFAULT, height));
			traceDataSelectedSignal.setTrace(i + " nm", sample, reference);
		}
		/*
		 * Notes
		 */
		Text textGeneralNotes = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textGeneralNotes.setText("General notes ...");
		GridData gridDataNotes = getGridData(2, GridData.FILL_HORIZONTAL);
		gridDataNotes.heightHint = 150;
		textGeneralNotes.setLayoutData(gridDataNotes);
		//
		compositeTraceData.pack(true);
		scrolledComposite.setMinSize(compositeTraceData.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.setContent(compositeTraceData);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setAlwaysShowScrollBars(false);
	}

	private GridData getGridData(int horizontalSpan, int style) {

		GridData gridData = new GridData(style);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	private GridData getGridDataButton(int style) {

		GridData gridData;
		if(style == -1) {
			gridData = new GridData();
		} else {
			gridData = new GridData(style);
		}
		gridData.widthHint = 120;
		return gridData;
	}
}
