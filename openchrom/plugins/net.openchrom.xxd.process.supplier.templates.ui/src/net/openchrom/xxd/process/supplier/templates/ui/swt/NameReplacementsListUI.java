/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.NameReplacementsComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.NameReplacementsEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.NameReplacementsFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.NameReplacementsLabelProvider;

public class NameReplacementsListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = NameReplacementsLabelProvider.TITLES;
	private static final int[] BOUNDS = NameReplacementsLabelProvider.BOUNDS;

	private NameReplacementsLabelProvider labelProvider = new NameReplacementsLabelProvider();
	private NameReplacementsComparator tableComparator = new NameReplacementsComparator();
	private NameReplacementsFilter listFilter = new NameReplacementsFilter();

	public NameReplacementsListUI(Composite parent, int style) {

		super(parent, style);
		createColumns();
	}

	@Override
	public void setSearchText(String searchText, boolean caseSensitive) {

		listFilter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(tableComparator);
		setFilters(listFilter);
		setEditingSupport();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			if(!label.equals(NameReplacementsLabelProvider.SYNONYM)) {
				tableViewerColumn.setEditingSupport(new NameReplacementsEditingSupport(this, label));
			}
		}
	}
}
