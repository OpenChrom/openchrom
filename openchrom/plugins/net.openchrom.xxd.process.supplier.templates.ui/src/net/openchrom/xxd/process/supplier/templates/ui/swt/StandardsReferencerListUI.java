/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.AbstractTemplateLabelProvider;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsReferencerComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsReferencerEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsReferencerFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsReferencerLabelProvider;

public class StandardsReferencerListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = StandardsReferencerLabelProvider.TITLES;
	private static final int[] BOUNDS = StandardsReferencerLabelProvider.BOUNDS;
	//
	private StandardsReferencerLabelProvider labelProvider = new StandardsReferencerLabelProvider();
	private StandardsReferencerComparator tableComparator = new StandardsReferencerComparator();
	private StandardsReferencerFilter listFilter = new StandardsReferencerFilter();

	public StandardsReferencerListUI(Composite parent, int style) {

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
		setFilters(new ViewerFilter[]{listFilter});
		setEditingSupport();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			if(!label.equals(AbstractTemplateLabelProvider.NAME)) {
				tableViewerColumn.setEditingSupport(new StandardsReferencerEditingSupport(this, label));
			}
		}
	}
}
