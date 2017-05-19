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

import org.eclipse.eavp.service.swtchart.core.BaseChart;
import org.eclipse.eavp.service.swtchart.core.ICustomSelectionHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.swtchart.IAxis;

public class TraceDataComparisonUI extends Composite {

	private TraceDataUI sampleDataUI;
	private TraceDataUI referenceDataUI;
	private Label label;
	private Button button;

	public TraceDataComparisonUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setTrace(String trace) {

		label.setText(trace);
	}

	private void createControl() {

		setLayout(new GridLayout(1, true));
		//
		label = new Label(this, SWT.NONE);
		label.setText("");
		label.setLayoutData(getGridData());
		//
		button = new Button(this, SWT.CHECK);
		button.setText("matched");
		button.setSelection(false);
		button.setLayoutData(getGridData());
		//
		sampleDataUI = new TraceDataUI(this, SWT.NONE);
		sampleDataUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		BaseChart baseChartSample = sampleDataUI.getBaseChart();
		//
		referenceDataUI = new TraceDataUI(this, SWT.NONE);
		referenceDataUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		BaseChart baseChartReference = referenceDataUI.getBaseChart();
		//
		baseChartSample.addCustomSelectionHandler(new ICustomSelectionHandler() {

			@Override
			public void handleUserSelection(Event event) {

				setSelection(baseChartSample, baseChartReference);
			}
		});
		//
		baseChartReference.addCustomSelectionHandler(new ICustomSelectionHandler() {

			@Override
			public void handleUserSelection(Event event) {

				setSelection(baseChartReference, baseChartSample);
			}
		});
	}

	private GridData getGridData() {

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.CENTER;
		return gridData;
	}

	private void setSelection(BaseChart baseChartMaster, BaseChart baseChartListener) {

		IAxis xAxisMaster = baseChartMaster.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis yAxisMaster = baseChartMaster.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		IAxis xAxisListener = baseChartListener.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis yAxisListener = baseChartListener.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		//
		if(xAxisMaster != null && yAxisMaster != null && xAxisListener != null && yAxisListener != null) {
			//
			xAxisListener.setRange(xAxisMaster.getRange());
			yAxisListener.setRange(yAxisMaster.getRange());
			baseChartListener.redraw();
		}
	}
}
