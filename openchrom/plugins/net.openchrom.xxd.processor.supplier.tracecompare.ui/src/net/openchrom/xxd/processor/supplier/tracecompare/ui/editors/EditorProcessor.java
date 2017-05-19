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

import java.io.File;

import javax.xml.bind.JAXBException;

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

import net.openchrom.xxd.processor.supplier.tracecompare.io.ProcessorModelReader;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;

public class EditorProcessor extends MultiPageEditorPart {

	private static final Logger logger = Logger.getLogger(EditorProcessor.class);
	private PageProcessor pageProcessor;
	private boolean isDirty = false;
	//
	public static final int PAGE_INDEX_PROCESSOR = 0;

	public EditorProcessor() {
		/*
		 * Update the pages.
		 */
		final EditorProcessor editorProcessor = this;
		addPageChangedListener(new IPageChangedListener() {

			@Override
			public void pageChanged(PageChangedEvent event) {

				switch(getActivePage()) {
					case PAGE_INDEX_PROCESSOR:
						pageProcessor.setEditorProcessor(editorProcessor);
						break;
				}
			}
		});
	}

	@Override
	protected void createPages() {

		pageProcessor = new PageProcessor(getContainer());
		int pageIndex = addPage(pageProcessor.getControl());
		setPageText(pageIndex, "Trace Compare");
	}

	@Override
	public void setActivePage(int pageIndex) {

		super.setActivePage(pageIndex);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		// try {
		// ProcessorModelWriter processorModelWriter = new ProcessorModelWriter();
		// processorModelWriter.write(file, processorData.getProcessorModel(), monitor);
		// setDirty(false);
		// } catch(JAXBException e) {
		// logger.warn(e);
		// }
	}

	@Override
	public void doSaveAs() {

		// Shell shell = Display.getDefault().getActiveShell();
		// FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
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
		if(input instanceof IFileEditorInput) {
			//
			//
			try {
				IFileEditorInput fileEditorInput = (IFileEditorInput)input;
				File file = fileEditorInput.getFile().getLocation().toFile();
				ProcessorModelReader processorModelReader = new ProcessorModelReader();
				ProcessorModel processorModel = processorModelReader.read(file, new NullProgressMonitor());
			} catch(JAXBException e) {
				logger.warn(e);
			}
		} else {
			throw new PartInitException("The file could't be loaded.");
		}
	}

	@Override
	public void setFocus() {

		pageProcessor.setEditorProcessor(this);
		pageProcessor.setFocus();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	public boolean isDirty() {

		return isDirty;
	}

	public void setDirty(boolean isDirty) {

		this.isDirty = isDirty;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
