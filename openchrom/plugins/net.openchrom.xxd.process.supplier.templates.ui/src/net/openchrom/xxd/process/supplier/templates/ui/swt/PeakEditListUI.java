/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakEditComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakEditLabelProvider;

public class PeakEditListUI extends ExtendedTableViewer {

	private static final String[] TITLES = PeakEditLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakEditLabelProvider.BOUNDS;
	//
	private PeakEditLabelProvider labelProvider = new PeakEditLabelProvider();
	private PeakEditComparator tableComparator = new PeakEditComparator();

	public PeakEditListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	public void clear() {

		setInput(null);
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(tableComparator);
	}
}
