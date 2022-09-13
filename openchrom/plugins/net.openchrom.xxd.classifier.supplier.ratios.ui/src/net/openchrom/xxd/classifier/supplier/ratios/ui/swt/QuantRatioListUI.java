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
package net.openchrom.xxd.classifier.supplier.ratios.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioEditingSupport;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioTableComparator;

public class QuantRatioListUI extends AbstractRatioListUI {

	private QuantRatioLabelProvider labelProvider;
	private QuantRatioTableComparator tableComparator;
	private DisplayOption displayOption;

	public QuantRatioListUI(Composite parent, int style) {

		this(parent, style, DisplayOption.SETTINGS);
	}

	public QuantRatioListUI(Composite parent, int style, DisplayOption displayOption) {

		super(parent, style);
		this.displayOption = displayOption;
		labelProvider = new QuantRatioLabelProvider(displayOption);
		tableComparator = new QuantRatioTableComparator(displayOption);
		createColumns();
	}

	private void createColumns() {

		String[] titles = DisplayOption.SETTINGS.equals(displayOption) ? QuantRatioResultTitles.TITLES_SETTINGS : QuantRatioResultTitles.TITLES_RESULTS;
		int[] bounds = DisplayOption.SETTINGS.equals(displayOption) ? QuantRatioResultTitles.BOUNDS_SETTINGS : QuantRatioResultTitles.BOUNDS_RESULTS;
		//
		createColumns(titles, bounds);
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
			if(!label.equals(QuantRatioResultTitles.NAME)) {
				tableViewerColumn.setEditingSupport(new QuantRatioEditingSupport(this, label));
			}
		}
	}
}