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

import java.io.File;

import javax.xml.bind.JAXBException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io.ProcessorModelReader;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io.ProcessorModelWriter;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class EditorProcessor extends MultiPageEditorPart {

	private static final Logger logger = Logger.getLogger(EditorProcessor.class);
	//
	private File file; // This report file.
	//
	public static int PAGE_INDEX_SETTINGS;
	public static int PAGE_INDEX_SHIFT_HEATMAP;
	public static int PAGE_INDEX_SHIFT_TABLE;
	//
	private PageSettings pageSettings;
	private PageShiftHeatmap pageShiftHeatmap;
	private PageShiftTable pageShiftTable;
	//
	private ProcessorData processorData;

	@Override
	protected void createPages() {

		pageSettings = new PageSettings(this, getContainer());
		PAGE_INDEX_SETTINGS = addPage(pageSettings.getControl());
		setPageText(PAGE_INDEX_SETTINGS, "Settings");
		//
		pageShiftHeatmap = new PageShiftHeatmap(this, getContainer());
		PAGE_INDEX_SHIFT_HEATMAP = addPage(pageShiftHeatmap.getControl());
		setPageText(PAGE_INDEX_SHIFT_HEATMAP, "Shift Heatmap");
		//
		pageShiftTable = new PageShiftTable(this, getContainer());
		PAGE_INDEX_SHIFT_TABLE = addPage(pageShiftTable.getControl());
		setPageText(PAGE_INDEX_SHIFT_TABLE, "Shift Table");
	}

	public void focusPage(int pageIndex) {

		setActivePage(pageIndex);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		try {
			ProcessorModelWriter processorModelWriter = new ProcessorModelWriter();
			processorModelWriter.write(file, processorData.getProcessorModel(), monitor);
		} catch(JAXBException e) {
			logger.warn(e);
		}
	}

	@Override
	public void doSaveAs() {

		System.out.println("Implement Save As...");
	}

	@Override
	public boolean isSaveAsAllowed() {

		return true;
	}

	@Override
	public void setFocus() {

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
}
