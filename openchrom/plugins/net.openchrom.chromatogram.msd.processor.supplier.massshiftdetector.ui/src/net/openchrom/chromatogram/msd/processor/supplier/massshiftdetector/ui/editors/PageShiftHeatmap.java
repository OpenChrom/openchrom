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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editors;

import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt.EnhancedShiftHeatmapEditor;

public class PageShiftHeatmap {

	private EditorProcessor editorProcessor;
	//
	private Composite control;
	private EnhancedShiftHeatmapEditor enhancedShiftHeatmapEditor;

	public PageShiftHeatmap(EditorProcessor editorProcessor, Composite container) {
		initialize(container);
		this.editorProcessor = editorProcessor;
	}

	public void initialize(Composite parent) {

		control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout());
		//
		enhancedShiftHeatmapEditor = new EnhancedShiftHeatmapEditor(control, SWT.NONE);
		enhancedShiftHeatmapEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		enhancedShiftHeatmapEditor.setLayout(new GridLayout(1, true));
		enhancedShiftHeatmapEditor.setBackground(Colors.WHITE);
	}

	public Composite getControl() {

		return control;
	}
}
