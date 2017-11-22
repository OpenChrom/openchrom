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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;

public class TraceCompareEditorUI extends Composite {

	private EditorProcessor editorProcessor;
	private TraceDataComparisonUI traceComparatorSample;
	private TraceDataComparisonUI traceComparatorValidation;

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(Object object) {

		if(object instanceof EditorProcessor) {
			//
			editorProcessor = (EditorProcessor)object;
			initializeData();
		}
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		/*
		 * Elements
		 */
		createTraceComparatorSample(composite);
		createTraceComparatorValidation(composite);
	}

	private void createTraceComparatorSample(Composite parent) {

		traceComparatorSample = new TraceDataComparisonUI(parent, SWT.BORDER);
		traceComparatorSample.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceComparatorSample.setBackground(Colors.WHITE);
	}

	private void createTraceComparatorValidation(Composite parent) {

		traceComparatorValidation = new TraceDataComparisonUI(parent, SWT.BORDER);
		traceComparatorValidation.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceComparatorValidation.setBackground(Colors.WHITE);
	}

	private void initializeData() {

		traceComparatorSample.setData(editorProcessor);
		traceComparatorSample.loadData("Sample");
		//
		traceComparatorValidation.setData(editorProcessor);
		traceComparatorValidation.loadData("Validation");
	}
}
