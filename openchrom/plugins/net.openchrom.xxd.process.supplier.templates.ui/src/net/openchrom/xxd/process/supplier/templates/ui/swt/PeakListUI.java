/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakLabelProvider;

public class PeakListUI extends ExtendedTableViewer {

	private static final String[] TITLES = PeakLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakLabelProvider.BOUNDS;
	//
	private PeakLabelProvider labelProvider = new PeakLabelProvider();
	private PeakComparator tableComparator = new PeakComparator();

	public PeakListUI(Composite parent, int style) {
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
