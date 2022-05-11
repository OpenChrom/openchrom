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
 * Christoph Läubrich - add support for comments
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.AbstractTemplateLabelProvider;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorEditingSupport;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorFilter;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakDetectorLabelProvider;

public class PeakDetectorListUI extends AbstractTemplateListUI {

	private static final String[] TITLES = PeakDetectorLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakDetectorLabelProvider.BOUNDS;
	//
	private PeakDetectorLabelProvider labelProvider = new PeakDetectorLabelProvider();
	private PeakDetectorComparator tableComparator = new PeakDetectorComparator();
	private PeakDetectorFilter listFilter = new PeakDetectorFilter();

	public PeakDetectorListUI(Composite parent, int style) {

		this(parent, style, true);
	}

	public PeakDetectorListUI(Composite parent, int style, boolean enableEditPositionDirective) {

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
		if(PreferenceSupplier.isDetectorSettingsSort()) {
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
				tableViewerColumn.setEditingSupport(new PeakDetectorEditingSupport(this, label));
			}
		}
	}

	private boolean isEditEnabled(String label, boolean enableEditPositionDirective) {

		if(AbstractTemplateLabelProvider.POSITION_START.equals(label)) {
			return false;
		} else if(AbstractTemplateLabelProvider.POSITION_STOP.equals(label)) {
			return false;
		} else if(AbstractTemplateLabelProvider.POSITION_DIRECTIVE.equals(label)) {
			return enableEditPositionDirective;
		} else {
			return true;
		}
	}
}