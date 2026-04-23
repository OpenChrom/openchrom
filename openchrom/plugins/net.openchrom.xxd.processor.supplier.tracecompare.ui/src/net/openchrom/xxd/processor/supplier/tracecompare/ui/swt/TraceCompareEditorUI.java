/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
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

	private boolean initialize = true;

	public TraceCompareEditorUI(Composite parent, int style) {

		super(parent, style);
		initialize();
	}

	public void update(Object object) {

		if(object instanceof EditorProcessor editorProcessor) {

			this.editorProcessor = editorProcessor;
			if(initialize) {
				initializeData();
				initialize = false;
			}
		}
	}

	private void initialize() {

		setLayout(new FillLayout());

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

		if(traceComparatorValidation != null) {
			traceComparatorValidation.setData(editorProcessor);
			traceComparatorValidation.loadSampleAndReferenceModelData();
		}
	}
}
