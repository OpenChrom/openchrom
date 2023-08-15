/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.internal.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.editor.SystemEditor;
import org.eclipse.chemclipse.support.ui.files.ExtendedFileDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.export.core.AbstractSeriesExportHandler;
import org.eclipse.swtchart.export.core.ISeriesExportConverter;
import org.eclipse.swtchart.extensions.core.ChartType;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.linecharts.LineChart;

import net.openchrom.swtchart.extension.export.vectorgraphics.dialogs.PageSizeDialog;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.preferences.PreferenceSupplier;

import de.erichseifert.vectorgraphics2d.Document;
import de.erichseifert.vectorgraphics2d.Processor;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;

public abstract class AbstractExportHandler extends AbstractSeriesExportHandler implements ISeriesExportConverter {

	private static final Logger logger = Logger.getLogger(AbstractExportHandler.class);

	public void execute(Shell shell, ScrollableChart scrollableChart, Processor processor, String typeName, String filterName, String filterExtension, String fileName) {

		PageSizeOption pageSizeOption = PreferenceSupplier.getPageSizeOption();
		PageSizeDialog pageSizeDialog = new PageSizeDialog(shell);
		if(pageSizeDialog.open() == Dialog.OK) {
			pageSizeOption = pageSizeDialog.getPageSizeOption();
			PreferenceSupplier.setPageSizeOption(pageSizeOption);
		}
		//
		CommandSequence commandSequene = getCommandSequence(shell, pageSizeOption, scrollableChart);
		if(commandSequene != null) {
			Document document = processor.getDocument(commandSequene, pageSizeOption.pageSize());
			executeFile(shell, document, filterName, filterExtension, fileName);
			executeClipboard(document, typeName);
		} else {
			MessageDialog.openInformation(shell, "VectorGraphics", "Sorry, the chart type is not supported yet. Please ask for support.");
		}
	}

	private void executeClipboard(Document document, String typeName) {

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			document.writeTo(outputStream);
			ByteArrayTransfer byteArrayTransfer = getByteArrayTransfer(typeName);
			Object[] data = new Object[]{outputStream.toByteArray()};
			Transfer[] dataTypes = new Transfer[]{byteArrayTransfer};
			Clipboard clipboard = new Clipboard(Display.getDefault());
			clipboard.setContents(data, dataTypes);
			clipboard.dispose();
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	private ByteArrayTransfer getByteArrayTransfer(String typeName) {

		return new ByteArrayTransfer() {

			@Override
			protected String[] getTypeNames() {

				return new String[]{typeName};
			}

			@Override
			protected int[] getTypeIds() {

				return new int[]{registerType(typeName)};
			}
		};
	}

	private void executeFile(Shell shell, Document document, String filterName, String filterExtension, String fileName) {

		FileDialog fileDialog = ExtendedFileDialog.create(shell, SWT.SAVE);
		fileDialog.setOverwrite(true);
		fileDialog.setText(filterName);
		fileDialog.setFilterExtensions(new String[]{filterExtension});
		fileDialog.setFileName(fileName);
		fileDialog.setFilterPath(PreferenceSupplier.getPathExport());
		//
		String pathname = fileDialog.open();
		if(pathname != null) {
			/*
			 * File
			 */
			File file = new File(pathname);
			PreferenceSupplier.setPathExport(file.getParentFile().getAbsolutePath());
			try (FileOutputStream outputStream = new FileOutputStream(file)) {
				document.writeTo(outputStream);
				SystemEditor.open(file);
			} catch(IOException e) {
				logger.warn(e);
			}
		}
	}

	/*
	 * EPS is deactivated in MS Office.
	 * https://support.microsoft.com/de-de/office/unterst%C3%BCtzung-f%C3%BCr-eps-bilder-wurde-in-office-deaktiviert-a069d664-4bcf-415e-a1b5-cbb0c334a840
	 */
	private CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart) {

		IChartCommandGenerator commandGenerator = null;
		if(scrollableChart instanceof LineChart) {
			commandGenerator = new LineChartCommandGenerator();
		} else {
			ChartType chartType = scrollableChart.getChartType();
			switch(chartType) {
				case STEP:
				case LINE:
					commandGenerator = new LineChartCommandGenerator();
					break;
				default:
					break;
			}
		}
		/*
		 * Check that the generator is available.
		 */
		if(commandGenerator != null) {
			return commandGenerator.getCommandSequence(shell, pageSizeOption, scrollableChart);
		}
		//
		return null;
	}
}