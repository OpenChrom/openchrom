/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.DisplayOption;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioEditingSupport;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioTableComparator;

public class TraceRatioListUI extends AbstractRatioListUI {

	private static final String[] TITLES = TraceRatioResultTitles.TITLES_SETTINGS;
	private static final int[] BOUNDS = TraceRatioResultTitles.BOUNDS_SETTINGS;
	//
	private TraceRatioLabelProvider labelProvider = new TraceRatioLabelProvider(DisplayOption.SETTINGS);
	private TraceRatioTableComparator tableComparator = new TraceRatioTableComparator(DisplayOption.SETTINGS);

	public TraceRatioListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(tableComparator);
		setFilters(new ViewerFilter[]{listFilter});
		setEditingSupport();
		setCellColorProvider();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			if(!label.equals(TraceRatioResultTitles.NAME)) {
				tableViewerColumn.setEditingSupport(new TraceRatioEditingSupport(this, label));
			}
		}
	}
}
