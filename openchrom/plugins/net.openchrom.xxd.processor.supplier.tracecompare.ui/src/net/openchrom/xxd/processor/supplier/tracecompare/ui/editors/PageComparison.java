/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.swt.TraceCompareEditorUI;

public class PageComparison {

	private EditorProcessor editorProcessor;
	private Composite control;
	private TraceCompareEditorUI traceCompareEditorUI;

	public PageComparison(Composite container) {
		initialize(container);
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
	}

	public void setFocus() {

	}

	public void initialize(Composite parent) {

		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());
		//
		traceCompareEditorUI = new TraceCompareEditorUI(control, SWT.NONE);
		traceCompareEditorUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceCompareEditorUI.setLayout(new GridLayout(1, true));
	}

	public Composite getControl() {

		return control;
	}
}
