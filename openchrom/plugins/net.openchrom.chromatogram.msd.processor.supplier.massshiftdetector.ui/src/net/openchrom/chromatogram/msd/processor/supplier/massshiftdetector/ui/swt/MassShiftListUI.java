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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider.MassShiftListLabelProvider;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider.MassShiftListTableComparator;

public class MassShiftListUI extends ExtendedTableViewer {

	private String[] titles = {"m/z", "RT (Minutes) - Reference", "RT (Minutes) - Isotope", "Mass Shift Level", "Certainty"};
	private int bounds[] = {100, 100, 100, 100, 100};

	public MassShiftListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	private void createColumns() {

		createColumns(titles, bounds);

		setLabelProvider(new MassShiftListLabelProvider());
		setContentProvider(new ListContentProvider());
		setComparator(new MassShiftListTableComparator());
	}
}
