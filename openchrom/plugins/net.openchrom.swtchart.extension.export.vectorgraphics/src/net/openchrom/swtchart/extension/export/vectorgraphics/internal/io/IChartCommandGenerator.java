/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.ScrollableChart;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;

public interface IChartCommandGenerator {

	CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart);

	CommandSequence getCommandSequence(PageSizeOption pageSizeOption, int indexAxisX, int indexAxisY, ScrollableChart scrollableChart);
}