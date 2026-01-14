/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.AbstractTemplateLabelProvider;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsAssignerLabelProvider;

public class StandardsAssignerListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = StandardsAssignerLabelProvider.TITLES;
	private static final int[] BOUNDS = StandardsAssignerLabelProvider.BOUNDS;

	private StandardsAssignerLabelProvider labelProvider = new StandardsAssignerLabelProvider();
	private StandardsAssignerComparator tableComparator = new StandardsAssignerComparator();
	private StandardsAssignerFilter listFilter = new StandardsAssignerFilter();

	public StandardsAssignerListUI(Composite parent, int style) {

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
			if(!label.equals(AbstractTemplateLabelProvider.NAME)) {
				tableViewerColumn.setEditingSupport(new StandardsAssignerEditingSupport(this, label));
			}
		}
	}
}
