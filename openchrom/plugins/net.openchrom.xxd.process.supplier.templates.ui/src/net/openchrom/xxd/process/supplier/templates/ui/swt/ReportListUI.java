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

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReportComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReportEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReportFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReportLabelProvider;

public class ReportListUI extends ExtendedTableViewer {

	private static final String[] TITLES = ReportLabelProvider.TITLES;
	private static final int[] BOUNDS = ReportLabelProvider.BOUNDS;
	//
	private ReportLabelProvider labelProvider = new ReportLabelProvider();
	private ReportComparator tableComparator = new ReportComparator();
	private ReportFilter listFilter = new ReportFilter();

	public ReportListUI(Composite parent, int style) {

		super(parent, style);
		createColumns();
	}

	public void setSearchText(String searchText, boolean caseSensitive) {

		listFilter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	public void clear() {

		setInput(null);
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		if(PreferenceSupplier.isReportSettingsSort()) {
			setComparator(tableComparator); // SORT OK
		}
		setFilters(new ViewerFilter[]{listFilter});
		setEditingSupport();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			if(!label.equals(ReportLabelProvider.NAME)) {
				tableViewerColumn.setEditingSupport(new ReportEditingSupport(this, label));
			}
		}
	}
}
