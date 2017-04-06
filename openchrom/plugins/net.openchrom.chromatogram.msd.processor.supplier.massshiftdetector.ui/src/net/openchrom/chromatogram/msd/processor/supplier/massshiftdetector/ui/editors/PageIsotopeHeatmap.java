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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.listener.INextListener;
import org.eclipse.chemclipse.support.ui.listener.IPreviousListener;
import org.eclipse.chemclipse.support.ui.listener.IProcessListener;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.MassShiftDetectorRunnable;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt.EnhancedIsotopeHeatmapEditor;

public class PageIsotopeHeatmap {

	private static final Logger logger = Logger.getLogger(PageIsotopeHeatmap.class);
	//
	private EditorProcessor editorProcessor;
	//
	private Composite control;
	private EnhancedIsotopeHeatmapEditor enhancedIsotopeHeatmapEditor;

	public PageIsotopeHeatmap(Composite container) {
		initialize(container);
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

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

				editorProcessor.setActivePage(EditorProcessor.PAGE_INDEX_SETTINGS);
			}
		});
		//
		enhancedIsotopeHeatmapEditor.addNextListener(new INextListener() {

			@Override
			public void nextAction() {

				editorProcessor.setActivePage(EditorProcessor.PAGE_INDEX_SHIFT_TABLE);
			}
		});
		//
		enhancedIsotopeHeatmapEditor.addProcessListener(new IProcessListener() {

			@Override
			public void processAction() {

				/*
				 * Calculate the shifts.
				 */
				editorProcessor.setDirty(true);
				ProcessorData processorData = editorProcessor.getProcessorData();
				if(processorData != null && processorData.getMassShifts() == null) {
					Shell shell = Display.getCurrent().getActiveShell();
					MassShiftDetectorRunnable runnable = new MassShiftDetectorRunnable(processorData);
					ProgressMonitorDialog monitor = new ProgressMonitorDialog(shell);
					//
					try {
						monitor.run(true, true, runnable);
						processorData.setMassShifts(runnable.getMassShifts());
					} catch(InterruptedException e1) {
						logger.warn(e1);
					} catch(InvocationTargetException e1) {
						logger.warn(e1);
					}
				}
				//
				enhancedIsotopeHeatmapEditor.setInput(processorData);
			}
		});
	}

	public Composite getControl() {

		return control;
	}
}
