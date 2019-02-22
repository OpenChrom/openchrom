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
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.TraceRatioEditingSupport;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.TraceRatioFilter;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.TraceRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.TraceRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.TraceRatioTableComparator;

public class TraceRatioListUI extends ExtendedTableViewer {

	private static final String[] TITLES = TraceRatioResultTitles.TITLES_SETTINGS;
	private static final int[] BOUNDS = TraceRatioResultTitles.BOUNDS_SETTINGS;
	//
	private TraceRatioLabelProvider labelProvider = new TraceRatioLabelProvider(TraceRatioResultTitles.OPTION_SETTINGS);
	private TraceRatioTableComparator tableComparator = new TraceRatioTableComparator(TraceRatioResultTitles.OPTION_SETTINGS);
	private TraceRatioFilter listFilter = new TraceRatioFilter();

	public TraceRatioListUI(Composite parent, int style) {
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

	private void setCellColorProvider() {

		TableViewerColumn tableViewerColumn;
		/*
		 * Warn
		 */
		tableViewerColumn = getTableViewerColumn(TraceRatioResultTitles.DEVIATION_WARN);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						TraceRatio traceRatio = (TraceRatio)cell.getElement();
						cell.setBackground(Colors.YELLOW);
						cell.setForeground(Colors.BLACK);
						cell.setText(">= " + Double.toString(traceRatio.getDeviationWarn()));
						super.update(cell);
					}
				}
			});
		}
		/*
		 * Error
		 */
		tableViewerColumn = getTableViewerColumn(TraceRatioResultTitles.DEVIATION_ERROR);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						TraceRatio traceRatio = (TraceRatio)cell.getElement();
						cell.setBackground(Colors.RED);
						cell.setForeground(Colors.WHITE);
						cell.setText(">= " + Double.toString(traceRatio.getDeviationError()));
						super.update(cell);
					}
				}
			});
		}
	}
}
