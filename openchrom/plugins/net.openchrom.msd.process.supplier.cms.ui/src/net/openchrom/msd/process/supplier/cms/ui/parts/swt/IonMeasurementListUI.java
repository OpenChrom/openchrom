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
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.IonMeasurementListContentProvider;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.IonMeasurementListLabelProvider;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.IonMeasurementListTableComparator;

public class IonMeasurementListUI extends ExtendedTableViewer {

	private String[] titles = {"m/z", "signal"};
	private int bounds[] = {200, 300};

	public IonMeasurementListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	public void update(ICalibratedVendorLibraryMassSpectrum spectrum) {

		if(spectrum != null) {
			setInput(spectrum);
		} else {
			setInput(null);
		}
	}

	private void createColumns() {

		createColumns(titles, bounds);
		//
		setLabelProvider(new IonMeasurementListLabelProvider());
		setContentProvider(new IonMeasurementListContentProvider());
		setComparator(new IonMeasurementListTableComparator());
	}
}
