/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioEditingSupport;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace.TraceRatioTableComparator;

public class TraceRatioListUI extends AbstractRatioListUI {

	private TraceRatioLabelProvider labelProvider;
	private TraceRatioTableComparator tableComparator;
	private DisplayOption displayOption;

	public TraceRatioListUI(Composite parent, int style) {

		this(parent, style, DisplayOption.SETTINGS);
	}

	public TraceRatioListUI(Composite parent, int style, DisplayOption displayOption) {

		super(parent, style);
		this.displayOption = displayOption;
		labelProvider = new TraceRatioLabelProvider(displayOption);
		tableComparator = new TraceRatioTableComparator(displayOption);
		createColumns();
	}

	private void createColumns() {

		String[] titles = DisplayOption.SETTINGS.equals(displayOption) ? TraceRatioResultTitles.TITLES_SETTINGS : TraceRatioResultTitles.TITLES_RESULTS;
		int[] bounds = DisplayOption.SETTINGS.equals(displayOption) ? TraceRatioResultTitles.BOUNDS_SETTINGS : TraceRatioResultTitles.BOUNDS_RESULTS;
		//
		createColumns(titles, bounds);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(tableComparator);
		setFilters(new ViewerFilter[]{getPeakRatioFilter()});
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