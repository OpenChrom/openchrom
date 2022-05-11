/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIntegratorComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIntegratorEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIntegratorFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIntegratorLabelProvider;

public class PeakIntegratorListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = PeakIntegratorLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakIntegratorLabelProvider.BOUNDS;
	//
	private PeakIntegratorLabelProvider labelProvider = new PeakIntegratorLabelProvider();
	private PeakIntegratorComparator tableComparator = new PeakIntegratorComparator();
	private PeakIntegratorFilter listFilter = new PeakIntegratorFilter();

	public PeakIntegratorListUI(Composite parent, int style) {

		super(parent, style);
		createColumns();
	}

	public void setSearchText(String searchText, boolean caseSensitive) {

		listFilter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(tableComparator);
		setFilters(new ViewerFilter[]{listFilter});
		setEditingSupport();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			tableViewerColumn.setEditingSupport(new PeakIntegratorEditingSupport(this, label));
		}
	}
}