/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
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
package net.openchrom.swtchart.extension.export.vectorgraphics.core;

import java.io.File;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.vectorgraphics2d.pdf.PDFProcessor;

import net.openchrom.swtchart.extension.export.vectorgraphics.internal.io.AbstractExportHandler;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

public class PDFExportHandler extends AbstractExportHandler {

	public static final String DESCRIPTION = "Vector Graphics 2D";
	public static final String FILE_EXTENSION = ".pdf";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";
	public static final String TYPE_NAME = "application/pdf";

	@Override
	public String getName() {

		return FILTER_NAME;
	}

	@Override
	public Image getIcon() {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PDF, IApplicationImageProvider.SIZE_16x16);
	}

	@Override
	public void execute(Shell shell, ScrollableChart scrollableChart) {

		String fileName = scrollableChart.getFileName().isEmpty() ? FILE_NAME : scrollableChart.getFileName();
		execute(shell, scrollableChart, new PDFProcessor(), TYPE_NAME, FILTER_NAME, FILTER_EXTENSION, fileName);
	}

	/**
	 * 
	 * Writes the chart into the given file. True on success.
	 * 
	 * @param file
	 * @param shell
	 * @param pageSizeOption
	 * @param indexAxisX
	 * @param indexAxisY
	 * @param scrollableChart
	 * @return boolean
	 */
	public boolean execute(File file, Shell shell, PageSizeOption pageSizeOption, int indexAxisX, int indexAxisY, ScrollableChart scrollableChart) {

		return execute(file, shell, pageSizeOption, scrollableChart, indexAxisX, indexAxisY, new PDFProcessor());
	}
}