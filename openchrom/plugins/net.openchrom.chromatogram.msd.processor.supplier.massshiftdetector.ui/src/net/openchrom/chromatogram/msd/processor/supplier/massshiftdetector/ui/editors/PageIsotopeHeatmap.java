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

import org.eclipse.chemclipse.support.ui.listener.INextListener;
import org.eclipse.chemclipse.support.ui.listener.IPreviousListener;
import org.eclipse.chemclipse.support.ui.listener.IProcessListener;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt.EnhancedIsotopeHeatmapEditor;

public class PageIsotopeHeatmap {

	private EditorProcessor editorProcessor;
	//
	private Composite control;
	private EnhancedIsotopeHeatmapEditor enhancedIsotopeHeatmapEditor;

	public PageIsotopeHeatmap(EditorProcessor editorProcessor, Composite container) {
		initialize(container);
		this.editorProcessor = editorProcessor;
	}

	public void initialize(Composite parent) {

		control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout());
		//
		enhancedIsotopeHeatmapEditor = new EnhancedIsotopeHeatmapEditor(control, SWT.NONE);
		enhancedIsotopeHeatmapEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		enhancedIsotopeHeatmapEditor.setLayout(new GridLayout(1, true));
		enhancedIsotopeHeatmapEditor.setBackground(Colors.WHITE);
		//
		enhancedIsotopeHeatmapEditor.addPreviousListener(new IPreviousListener() {

			@Override
			public void previousAction() {

				editorProcessor.focusPage(EditorProcessor.PAGE_INDEX_SETTINGS);
			}
		});
		//
		enhancedIsotopeHeatmapEditor.addNextListener(new INextListener() {

			@Override
			public void nextAction() {

				editorProcessor.focusPage(EditorProcessor.PAGE_INDEX_SHIFT_TABLE);
			}
		});
		//
		enhancedIsotopeHeatmapEditor.addProcessListener(new IProcessListener() {

			@Override
			public void processAction() {

				ProcessorData processorData = editorProcessor.getProcessorData();
				enhancedIsotopeHeatmapEditor.setInput(processorData);
			}
		});
	}

	public Composite getControl() {

		return control;
	}
}
