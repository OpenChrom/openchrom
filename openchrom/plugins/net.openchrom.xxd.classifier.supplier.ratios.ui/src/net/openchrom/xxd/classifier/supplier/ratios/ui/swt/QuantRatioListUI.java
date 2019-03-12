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
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioEditingSupport;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioLabelProvider;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioResultTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant.QuantRatioTableComparator;

public class QuantRatioListUI extends AbstractRatioListUI {

	private static final String[] TITLES = QuantRatioResultTitles.TITLES_SETTINGS;
	private static final int[] BOUNDS = QuantRatioResultTitles.BOUNDS_SETTINGS;
	//
	private QuantRatioLabelProvider labelProvider = new QuantRatioLabelProvider(DisplayOption.SETTINGS);
	private QuantRatioTableComparator tableComparator = new QuantRatioTableComparator(DisplayOption.SETTINGS);

	public QuantRatioListUI(Composite parent, int style) {
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
			if(!label.equals(QuantRatioResultTitles.NAME)) {
				tableViewerColumn.setEditingSupport(new QuantRatioEditingSupport(this, label));
			}
		}
	}
}
