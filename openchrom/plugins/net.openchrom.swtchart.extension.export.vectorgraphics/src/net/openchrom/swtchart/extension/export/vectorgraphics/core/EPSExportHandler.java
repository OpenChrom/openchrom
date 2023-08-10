/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.core;

import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.files.ExtendedFileDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.export.core.ISeriesExportConverter;
import org.eclipse.swtchart.extensions.core.ScrollableChart;

import de.erichseifert.vectorgraphics2d.Document;
import de.erichseifert.vectorgraphics2d.eps.EPSProcessor;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;

public class EPSExportHandler extends AbstractExportHandler implements ISeriesExportConverter {

	private static final Logger logger = Logger.getLogger(EPSExportHandler.class);
	//
	private static final String FILE_EXTENSION = "*.eps";
	private static final String NAME = "Vector Graphic (" + FILE_EXTENSION + ")";
	private static final String TITLE = "Save As Vector Graphic";

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	public void execute(Shell shell, ScrollableChart scrollableChart) {

		FileDialog fileDialog = ExtendedFileDialog.create(shell, SWT.SAVE);
		fileDialog.setOverwrite(true);
		fileDialog.setText(NAME);
		fileDialog.setFilterExtensions(new String[]{FILE_EXTENSION});
		//
		String fileName = fileDialog.open();
		if(fileName != null) {
			try {
				CommandSequence commandSequene = getCommandSequence(shell, scrollableChart);
				EPSProcessor processor = new EPSProcessor();
				Document document = processor.getDocument(commandSequene, SELECTED_PAGE_SIZE);
				document.writeTo(new FileOutputStream(fileName));
				//
				MessageDialog.openInformation(shell, TITLE, MESSAGE_OK);
			} catch(IOException e) {
				logger.warn(e);
			}
		}
	}
}
