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

import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time.TimeRatioEditingSupport;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time.TimeRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time.TimeRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time.TimeRatioTableComparator;

public class TimeRatioListUI extends AbstractRatioListUI {

	private TimeRatioLabelProvider labelProvider;
	private TimeRatioTableComparator tableComparator;
	private DisplayOption displayOption;

	public TimeRatioListUI(Composite parent, int style) {

		this(parent, style, DisplayOption.SETTINGS);
	}

	public TimeRatioListUI(Composite parent, int style, DisplayOption displayOption) {

		super(parent, style);
		this.displayOption = displayOption;
		labelProvider = new TimeRatioLabelProvider(displayOption);
		tableComparator = new TimeRatioTableComparator(displayOption);
		createColumns();
	}

	private void createColumns() {

		String[] titles = DisplayOption.SETTINGS.equals(displayOption) ? TimeRatioResultTitles.TITLES_SETTINGS : TimeRatioResultTitles.TITLES_RESULTS;
		int[] bounds = DisplayOption.SETTINGS.equals(displayOption) ? TimeRatioResultTitles.BOUNDS_SETTINGS : TimeRatioResultTitles.BOUNDS_RESULTS;
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
			if(!label.equals(TimeRatioResultTitles.NAME)) {
				tableViewerColumn.setEditingSupport(new TimeRatioEditingSupport(this, label));
			}
		}
	}
}