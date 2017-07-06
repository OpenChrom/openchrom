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

import org.eclipse.chemclipse.support.ui.listener.IPreviousListener;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.swt.EnhancedResultsEditor;

public class PageResults {

	private EditorProcessor editorProcessor;
	private Composite control;
	private EnhancedResultsEditor enhancedResultsEditor;

	public PageResults(Composite container) {
		initialize(container);
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		enhancedResultsEditor.setEditorProcessor(editorProcessor);
	}

	public void initialize(Composite parent) {

		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());
		//
		enhancedResultsEditor = new EnhancedResultsEditor(control, SWT.NONE);
		enhancedResultsEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		enhancedResultsEditor.setLayout(new GridLayout(1, true));
		enhancedResultsEditor.setBackground(Colors.WHITE);
		//
		enhancedResultsEditor.addPreviousListener(new IPreviousListener() {

			@Override
			public void previousAction() {

				editorProcessor.setActivePage(EditorProcessor.PAGE_INDEX_TRACE_COMPARISON);
			}
		});
	}

	public Composite getControl() {

		return control;
	}
}
