/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
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
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerLabelProvider;

public class StandardsAssignerListUI extends ExtendedTableViewer {

	private static final String[] TITLES = StandardsAssignerLabelProvider.TITLES;
	private static final int[] BOUNDS = StandardsAssignerLabelProvider.BOUNDS;
	//
	private StandardsAssignerLabelProvider labelProvider = new StandardsAssignerLabelProvider();
	private StandardsAssignerComparator tableComparator = new StandardsAssignerComparator();
	private StandardsAssignerFilter listFilter = new StandardsAssignerFilter();

	public StandardsAssignerListUI(Composite parent, int style) {

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
		setComparator(tableComparator);
		setFilters(new ViewerFilter[]{listFilter});
		setEditingSupport();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			if(!label.equals(StandardsAssignerLabelProvider.NAME)) {
				tableViewerColumn.setEditingSupport(new StandardsAssignerEditingSupport(this, label));
			}
		}
	}
}