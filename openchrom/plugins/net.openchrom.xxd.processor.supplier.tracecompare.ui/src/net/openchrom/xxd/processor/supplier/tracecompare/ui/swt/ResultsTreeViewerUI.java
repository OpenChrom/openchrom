/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import org.eclipse.chemclipse.support.ui.swt.EnhancedTreeViewer;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.provider.ResultsTreeViewerContentProvider;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.provider.ResultsTreeViewerLabelProvider;

public class ResultsTreeViewerUI extends EnhancedTreeViewer {

	public ResultsTreeViewerUI(Composite parent, int style) {
		super(parent, style);
		setLabelProvider(new ResultsTreeViewerLabelProvider());
		setContentProvider(new ResultsTreeViewerContentProvider());
	}
}
