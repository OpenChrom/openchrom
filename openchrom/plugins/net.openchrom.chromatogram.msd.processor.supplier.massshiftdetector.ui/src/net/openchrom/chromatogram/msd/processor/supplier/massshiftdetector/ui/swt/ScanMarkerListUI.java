/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider.ScanMarkerListLabelProvider;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider.ScanMarkerListTableComparator;

public class ScanMarkerListUI extends ExtendedTableViewer {

	private String[] titles = {"scan", "validated"};
	private int bounds[] = {200, 60};

	public ScanMarkerListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
		addCopyToClipboardListener(titles);
	}

	private void createColumns() {

		createColumns(titles, bounds);
		//
		setLabelProvider(new ScanMarkerListLabelProvider());
		setContentProvider(new ListContentProvider());
		setComparator(new ScanMarkerListTableComparator());
	}
}
