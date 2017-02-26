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

import net.openchrom.msd.process.supplier.cms.ui.internal.provider.CmsLibraryListContentProvider;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.CmsLibraryListLabelProvider;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.CmsLibraryListTableComparator;

public class CmsLibraryUI extends ExtendedTableViewer {

	public static final String NAME = "Name";
	public static final String RETENTION_TIME = "Retention Time";
	public static final String RETENTION_INDEX = "Retention Index";
	public static final String BASE_PEAK = "Base Peak";
	public static final String BASE_PEAK_ABUNDANCE = "Base Peak Abundance";
	public static final String NUMBER_OF_IONS = "Number of Ions";
	//
	private String[] titles = {//
			NAME, //
			RETENTION_TIME, //
			RETENTION_INDEX, //
			BASE_PEAK, //
			BASE_PEAK_ABUNDANCE, //
			NUMBER_OF_IONS};
	//
	private int bounds[] = {//
			300, //
			100, //
			100, //
			100, //
			100, //
			100};

	public CmsLibraryUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	private void createColumns() {

		createColumns(titles, bounds);
		//
		setLabelProvider(new CmsLibraryListLabelProvider());
		setContentProvider(new CmsLibraryListContentProvider());
		setComparator(new CmsLibraryListTableComparator());
	}
}
