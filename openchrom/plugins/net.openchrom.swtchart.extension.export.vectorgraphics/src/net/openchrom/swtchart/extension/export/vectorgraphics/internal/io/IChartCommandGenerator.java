/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
package net.openchrom.swtchart.extension.export.vectorgraphics.internal.io;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.CommandSequence;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

public interface IChartCommandGenerator {

	CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart);

	CommandSequence getCommandSequence(PageSizeOption pageSizeOption, int indexAxisX, int indexAxisY, ScrollableChart scrollableChart);
}