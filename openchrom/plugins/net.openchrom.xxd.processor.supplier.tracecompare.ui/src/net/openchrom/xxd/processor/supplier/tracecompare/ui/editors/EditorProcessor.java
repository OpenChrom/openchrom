/*******************************************************************************
 * Copyright (c) 2017, 2023 Lablicate GmbH.
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

import java.io.File;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import jakarta.xml.bind.JAXBException;
import net.openchrom.xxd.processor.supplier.tracecompare.io.ProcessorModelReader;
import net.openchrom.xxd.processor.supplier.tracecompare.io.ProcessorModelWriter;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;

public class EditorProcessor extends MultiPageEditorPart {

	private static final Logger logger = Logger.getLogger(EditorProcessor.class);
	//
	private File file; // This report file.
	//
	public static final int PAGE_INDEX_TRACE_COMPARISON = 0;
	public static final int PAGE_INDEX_RESULTS = 1;
	//
	private PageTraceComparison pageTraceComparison;
	private PageResults pageResults;
	//
	private IProcessorModel processorModel;
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
					case PAGE_INDEX_TRACE_COMPARISON:
						pageTraceComparison.setEditorProcessor(editorProcessor);
						break;
					case PAGE_INDEX_RESULTS:
						pageResults.setEditorProcessor(editorProcessor);
						break;
				}
			}
		});
	}

	@Override
	protected void createPages() {

		pageTraceComparison = new PageTraceComparison(getContainer());
		int pageIndexCompare = addPage(pageTraceComparison.getControl());
		setPageText(pageIndexCompare, "Trace Compare");
		//
		pageResults = new PageResults(getContainer());
		int pageIndexResults = addPage(pageResults.getControl());
		setPageText(pageIndexResults, "Results");
	}

	@Override
	public void setActivePage(int pageIndex) {

		super.setActivePage(pageIndex);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		try {
			ProcessorModelWriter processorModelWriter = new ProcessorModelWriter();
			processorModelWriter.write(file, processorModel, monitor);
			setDirty(false);
		} catch(JAXBException e) {
			logger.warn(e);
		}
	}

	@Override
	public void doSaveAs() {

		// Shell shell = Display.getDefault().getActiveShell();
		// FileDialog fileDialog = ExtendedFileDialog.create(shell, SWT.SAVE);
		// fileDialog.setOverwrite(true);
		// fileDialog.setText("Save results as *.csv file.");
		// fileDialog.setFilterExtensions(new String[]{"*.csv"});
		// fileDialog.setFilterNames(new String[]{"Trace Compare (*.csv)"});
		// String pathname = fileDialog.open();
		// if(pathname != null) {
		// File file = new File(pathname);
		// CSVExportWriter csvExportWriter = new CSVExportWriter();
		// try {
		// csvExportWriter.write(file, processorData, new NullProgressMonitor());
		// MessageDialog.openInformation(shell, "Trace Compare", "The results have been exported successfully.");
		// } catch(FileNotFoundException e) {
		// logger.warn(e);
		// MessageDialog.openInformation(shell, "Trace Compare", "Something has gone wrong to export the results.");
		// }
		// }
	}

	@Override
	public boolean isSaveAsAllowed() {

		return true;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {

		super.init(site, input);
		String fileName = input.getName();
		fileName = fileName.substring(0, fileName.length() - 4);
		setPartName(fileName);
		if(input instanceof IFileEditorInput fileEditorInput) {
			try {
				file = fileEditorInput.getFile().getLocation().toFile();
				ProcessorModelReader processorModelReader = new ProcessorModelReader();
				processorModel = processorModelReader.read(file, new NullProgressMonitor());
				logger.info(processorModel);
			} catch(JAXBException e) {
				logger.warn(e);
			}
		} else {
			throw new PartInitException("The file could't be loaded.");
		}
	}

	@Override
	public void setFocus() {

		pageTraceComparison.setEditorProcessor(this);
		pageTraceComparison.setFocus();
	}

	public IProcessorModel getProcessorModel() {

		return processorModel;
	}

	@Override
	public void dispose() {

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
