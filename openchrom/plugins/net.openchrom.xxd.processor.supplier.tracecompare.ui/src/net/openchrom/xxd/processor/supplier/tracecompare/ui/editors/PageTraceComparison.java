/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.editors;

import org.eclipse.chemclipse.support.ui.listener.INextListener;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.swt.EnhancedTraceCompareEditor;

public class PageTraceComparison {

	private EditorProcessor editorProcessor;
	private Composite control;
	private EnhancedTraceCompareEditor enhancedTraceCompareEditor;

	public PageTraceComparison(Composite container) {
		initialize(container);
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		enhancedTraceCompareEditor.setEditorProcessor(editorProcessor);
	}

	public void setFocus() {

	}

	public void initialize(Composite parent) {

		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());
		//
		enhancedTraceCompareEditor = new EnhancedTraceCompareEditor(control, SWT.NONE);
		enhancedTraceCompareEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		enhancedTraceCompareEditor.setLayout(new GridLayout(1, true));
		enhancedTraceCompareEditor.setBackground(Colors.WHITE);
		//
		enhancedTraceCompareEditor.addNextListener(new INextListener() {

			@Override
			public void nextAction() {

				editorProcessor.setActivePage(EditorProcessor.PAGE_INDEX_RESULTS);
			}
		});
	}

	public Composite getControl() {

		return control;
	}
}
