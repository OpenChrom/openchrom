/*******************************************************************************
 * Copyright (c) 2017, 2023 Lablicate GmbH.
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

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.files.ExtendedFileDialog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io.CSVExportWriter;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io.ProcessorModelReader;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io.ProcessorModelWriter;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

import jakarta.xml.bind.JAXBException;

public class EditorProcessor extends MultiPageEditorPart {

	private static final Logger logger = Logger.getLogger(EditorProcessor.class);
	//
	private File file; // This report file.
	//
	public static final int PAGE_INDEX_SETTINGS = 0;
	public static final int PAGE_INDEX_SHIFT_HEATMAP = 1;
	public static final int PAGE_INDEX_SHIFT_TABLE = 2;
	//
	private PageSettings pageSettings;
	private PageIsotopeHeatmap pageIsotopeHeatmap;
	private PageScanMarkerTable pageScanMarkerTable;
	//
	private ProcessorData processorData;
	//
	private boolean isDirty = false;

	public EditorProcessor() {

		/*
		 * Update the pages.
		 */
		final EditorProcessor editorProcessor = this;
		addPageChangedListener(new IPageChangedListener() {

			@Override
			public void pageChanged(PageChangedEvent event) {

				switch(getActivePage()) {
					case PAGE_INDEX_SETTINGS:
						pageSettings.setEditorProcessor(editorProcessor);
						break;
					case PAGE_INDEX_SHIFT_HEATMAP:
						pageIsotopeHeatmap.setEditorProcessor(editorProcessor);
						break;
					case PAGE_INDEX_SHIFT_TABLE:
						pageScanMarkerTable.setEditorProcessor(editorProcessor);
						break;
				}
			}
		});
	}

	@Override
	protected void createPages() {

		pageSettings = new PageSettings(getContainer());
		addPage(pageSettings.getControl());
		setPageText(PAGE_INDEX_SETTINGS, "Settings");
		//
		pageIsotopeHeatmap = new PageIsotopeHeatmap(getContainer());
		addPage(pageIsotopeHeatmap.getControl());
		setPageText(PAGE_INDEX_SHIFT_HEATMAP, "Shift Heatmap");
		//
		pageScanMarkerTable = new PageScanMarkerTable(getContainer());
		addPage(pageScanMarkerTable.getControl());
		setPageText(PAGE_INDEX_SHIFT_TABLE, "Shift Table");
	}

	@Override
	public void setActivePage(int pageIndex) {

		super.setActivePage(pageIndex);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		try {
			ProcessorModelWriter processorModelWriter = new ProcessorModelWriter();
			processorModelWriter.write(file, processorData.getProcessorModel(), monitor);
			setDirty(false);
		} catch(JAXBException e) {
			logger.warn(e);
		}
	}

	@Override
	public void doSaveAs() {

		Shell shell = Display.getDefault().getActiveShell();
		FileDialog fileDialog = ExtendedFileDialog.create(shell, SWT.SAVE);
		fileDialog.setOverwrite(true);
		fileDialog.setText("Save results as *.csv file.");
		fileDialog.setFilterExtensions(new String[]{"*.csv"});
		fileDialog.setFilterNames(new String[]{"Mass Shift Report (*.csv)"});
		String pathname = fileDialog.open();
		if(pathname != null) {
			File file = new File(pathname);
			CSVExportWriter csvExportWriter = new CSVExportWriter();
			try {
				csvExportWriter.write(file, processorData);
				MessageDialog.openInformation(shell, "Mass Shift Export", "The results have been exported successfully.");
			} catch(FileNotFoundException e) {
				logger.warn(e);
				MessageDialog.openInformation(shell, "Mass Shift Export", "Something has gone wrong to export the results.");
			}
		}
	}

	@Override
	public boolean isSaveAsAllowed() {

		return true;
	}

	@Override
	public void setFocus() {

		pageSettings.setEditorProcessor(this);
		pageSettings.setFocus();
	}

	public ProcessorData getProcessorData() {

		return processorData;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {

		super.init(site, input);
		String fileName = input.getName();
		fileName = fileName.substring(0, fileName.length() - 4);
		setPartName(fileName);
		if(input instanceof IFileEditorInput) {
			//
			//
			try {
				IFileEditorInput fileEditorInput = (IFileEditorInput)input;
				file = fileEditorInput.getFile().getLocation().toFile();
				ProcessorModelReader processorModelReader = new ProcessorModelReader();
				processorData = new ProcessorData();
				processorData.setProcessorModel(processorModelReader.read(file, new NullProgressMonitor()));
				//
			} catch(JAXBException e) {
				logger.warn(e);
			}
		} else {
			throw new PartInitException("The file could't be loaded.");
		}
	}

	@Override
	public void dispose() {

		pageSettings.dispose();
		super.dispose();
	}

	@Override
	public boolean isDirty() {

		return isDirty;
	}

	public void setDirty(boolean isDirty) {

		this.isDirty = isDirty;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
