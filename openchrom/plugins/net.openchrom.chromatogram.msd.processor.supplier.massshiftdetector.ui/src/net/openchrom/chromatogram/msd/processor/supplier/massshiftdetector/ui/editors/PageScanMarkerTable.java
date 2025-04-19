/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editors;

import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt.EnhancedScanMarkerEditor;

public class PageScanMarkerTable {

	private EditorProcessor editorProcessor;
	//
	private Composite control;
	private EnhancedScanMarkerEditor enhancedScanMarkerEditor;

	public PageScanMarkerTable(Composite container) {
		initialize(container);
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		enhancedScanMarkerEditor.setEditorProcessor(this.editorProcessor);
	}

	public void initialize(Composite parent) {

		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());
		//
		enhancedScanMarkerEditor = new EnhancedScanMarkerEditor(control, SWT.NONE);
		enhancedScanMarkerEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		enhancedScanMarkerEditor.setLayout(new GridLayout(1, true));
		enhancedScanMarkerEditor.setBackground(Colors.WHITE);
	}

	public Composite getControl() {

		return control;
	}
}
