/*******************************************************************************
 * Copyright (c) 2018, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.core;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.export.core.ISeriesExportConverter;
import org.eclipse.swtchart.extensions.core.ScrollableChart;

import net.openchrom.swtchart.extension.export.vectorgraphics.internal.io.AbstractExportHandler;

import de.erichseifert.vectorgraphics2d.pdf.PDFProcessor;

public class PDFExportHandler extends AbstractExportHandler implements ISeriesExportConverter {

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
}