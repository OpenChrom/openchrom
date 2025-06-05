/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.AbstractTemplateLabelProvider;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakReviewComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakReviewEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakReviewFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakReviewLabelProvider;

public class PeakReviewListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = PeakReviewLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakReviewLabelProvider.BOUNDS;

	private PeakReviewLabelProvider labelProvider = new PeakReviewLabelProvider();
	private PeakReviewComparator tableComparator = new PeakReviewComparator();
	private PeakReviewFilter listFilter = new PeakReviewFilter();

	public PeakReviewListUI(Composite parent, int style) {

		this(parent, style, true);
	}

	public PeakReviewListUI(Composite parent, int style, boolean enableEditPositionDirective) {

		super(parent, style);
		createColumns(enableEditPositionDirective);
	}

	@Override
	public void setSearchText(String searchText, boolean caseSensitive) {

		listFilter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	private void createColumns(boolean enableEditPositionDirective) {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		if(PreferenceSupplier.isReviewSettingsSort()) {
			setComparator(tableComparator); // SORT OK
		}
		setFilters(new ViewerFilter[]{listFilter});
		setEditingSupport(enableEditPositionDirective);
	}

	private void setEditingSupport(boolean enableEditPositionDirective) {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			if(isEditEnabled(label, enableEditPositionDirective)) {
				tableViewerColumn.setEditingSupport(new PeakReviewEditingSupport(this, label));
			}
		}
	}

	private boolean isEditEnabled(String label, boolean enableEditPositionDirective) {

		if(AbstractTemplateLabelProvider.NAME.equals(label)) {
			return false;
		} else if(AbstractTemplateLabelProvider.POSITION_DIRECTIVE.equals(label)) {
			return enableEditPositionDirective;
		} else {
			return true;
		}
	}
}