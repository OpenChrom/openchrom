/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support.DataProcessorUI;

public class TraceCompareEditorUI extends Composite {

	private EditorProcessor editorProcessor;
	private TraceDataComparisonUI traceComparatorSample;
	private TraceDataComparisonUI traceComparatorValidation;
	//
	private boolean initialize = true;

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(Object object) {

		if(object instanceof EditorProcessor) {
			//
			editorProcessor = (EditorProcessor)object;
			if(initialize) {
				initializeData();
				initialize = false;
			}
		}
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		//
		boolean useValidation = PreferenceSupplier.isUseDataValidation();
		int numColumns = (useValidation) ? 2 : 1;
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(numColumns, true));
		/*
		 * Elements
		 */
		if(useValidation) {
			createTraceComparatorQualification(composite);
			createTraceComparatorValidation(composite);
		} else {
			createTraceComparatorQualification(composite);
		}
	}

	private void createTraceComparatorQualification(Composite parent) {

		traceComparatorSample = new TraceDataComparisonUI(parent, SWT.BORDER, DataProcessorUI.ANALYSIS_TYPE_QUALIFICATION);
		traceComparatorSample.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceComparatorSample.setBackground(Colors.WHITE);
	}

	private void createTraceComparatorValidation(Composite parent) {

		traceComparatorValidation = new TraceDataComparisonUI(parent, SWT.BORDER, DataProcessorUI.ANALYSIS_TYPE_VALIDATION);
		traceComparatorValidation.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceComparatorValidation.setBackground(Colors.WHITE);
	}

	private void initializeData() {

		traceComparatorSample.setData(editorProcessor);
		traceComparatorSample.loadSampleAndReferenceModelData();
		//
		if(traceComparatorValidation != null) {
			traceComparatorValidation.setData(editorProcessor);
			traceComparatorValidation.loadSampleAndReferenceModelData();
		}
	}
}
