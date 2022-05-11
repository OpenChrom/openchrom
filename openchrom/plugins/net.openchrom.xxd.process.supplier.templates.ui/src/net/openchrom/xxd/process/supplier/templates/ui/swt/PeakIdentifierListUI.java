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
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIdentifierComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIdentifierEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIdentifierFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIdentifierLabelProvider;

public class PeakIdentifierListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = PeakIdentifierLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakIdentifierLabelProvider.BOUNDS;
	//
	private PeakIdentifierLabelProvider labelProvider = new PeakIdentifierLabelProvider();
	private PeakIdentifierComparator tableComparator = new PeakIdentifierComparator();
	private PeakIdentifierFilter listFilter = new PeakIdentifierFilter();

	public PeakIdentifierListUI(Composite parent, int style) {

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
			if(!label.equals(AbstractTemplateLabelProvider.NAME)) {
				tableViewerColumn.setEditingSupport(new PeakIdentifierEditingSupport(this, label));
			}
		}
	}
}