/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorLabelProvider;

public class PeakDetectorListUI extends ExtendedTableViewer {

	private static final String[] TITLES = PeakDetectorLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakDetectorLabelProvider.BOUNDS;
	//
	private PeakDetectorLabelProvider labelProvider = new PeakDetectorLabelProvider();
	private PeakDetectorComparator tableComparator = new PeakDetectorComparator();
	private PeakDetectorFilter listFilter = new PeakDetectorFilter();

	public PeakDetectorListUI(Composite parent, int style) {
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
			if(label.equals(PeakDetectorLabelProvider.DETECTOR_TYPE)) {
				tableViewerColumn.setEditingSupport(new PeakDetectorEditingSupport(this, label));
			} else if(label.equals(PeakDetectorLabelProvider.TRACES)) {
				tableViewerColumn.setEditingSupport(new PeakDetectorEditingSupport(this, label));
			}
		}
	}
}
