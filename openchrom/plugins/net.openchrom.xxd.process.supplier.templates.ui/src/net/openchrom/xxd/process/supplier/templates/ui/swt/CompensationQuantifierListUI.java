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

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.CompensationQuantifierComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.CompensationQuantifierEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.CompensationQuantifierFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.CompensationQuantifierLabelProvider;

public class CompensationQuantifierListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = CompensationQuantifierLabelProvider.TITLES;
	private static final int[] BOUNDS = CompensationQuantifierLabelProvider.BOUNDS;
	//
	private CompensationQuantifierLabelProvider labelProvider = new CompensationQuantifierLabelProvider();
	private CompensationQuantifierComparator tableComparator = new CompensationQuantifierComparator();
	private CompensationQuantifierFilter listFilter = new CompensationQuantifierFilter();

	public CompensationQuantifierListUI(Composite parent, int style) {

		super(parent, style);
		createColumns();
	}

	@Override
	public void setSearchText(String searchText, boolean caseSensitive) {

		listFilter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	@Override
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
			if(!label.equals(CompensationQuantifierLabelProvider.NAME)) {
				tableViewerColumn.setEditingSupport(new CompensationQuantifierEditingSupport(this, label));
			}
		}
	}
}
