/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
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
