/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.updates.IUpdateListener;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.base.model.SuspectEvaluation;
import net.openchrom.xxd.base.ui.internal.provider.SuspectListComparator;
import net.openchrom.xxd.base.ui.internal.provider.SuspectListEditingSupport;
import net.openchrom.xxd.base.ui.internal.provider.SuspectListFilter;
import net.openchrom.xxd.base.ui.internal.provider.SuspectListLabelProvider;

public class SuspectsListUI extends ExtendedTableViewer {

	private static final String[] TITLES = SuspectListLabelProvider.TITLES;
	private static final int[] BOUNDS = SuspectListLabelProvider.BOUNDS;
	//
	private final SuspectListLabelProvider labelProvider = new SuspectListLabelProvider();
	private final SuspectListComparator comparator = new SuspectListComparator();
	private final SuspectListFilter filter = new SuspectListFilter();
	//
	private IUpdateListener updateListener;

	public SuspectsListUI(Composite parent, int style) {

		super(parent, style);
		createColumns();
	}

	public void setSearchText(String searchText, boolean caseSensitive) {

		filter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	public void setUpdateListener(IUpdateListener updateListener) {

		this.updateListener = updateListener;
	}

	public void clear() {

		super.setInput(null);
	}

	public void updateContent() {

		if(updateListener != null) {
			updateListener.update();
		}
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(comparator);
		setFilters(new ViewerFilter[]{filter});
		setEditingSupport();
		setCellColorProvider();
	}

	private void setEditingSupport() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		for(int i = 0; i < tableViewerColumns.size(); i++) {
			TableViewerColumn tableViewerColumn = tableViewerColumns.get(i);
			String label = tableViewerColumn.getColumn().getText();
			tableViewerColumn.setEditingSupport(new SuspectListEditingSupport(this, label));
		}
	}

	private void setCellColorProvider() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		setColorMarker(tableViewerColumns.get(SuspectListLabelProvider.INDEX_STATUS));
	}

	private void setColorMarker(TableViewerColumn tableViewerColumn) {

		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						Object element = cell.getElement();
						if(element instanceof SuspectEvaluation suspectEvaluation) {
							String text = "";
							if(!suspectEvaluation.getSuspect().isDefault()) {
								String status = suspectEvaluation.getStatus();
								if(status.equals("OK")) {
									cell.setBackground(Colors.getColor(Colors.LIGHT_GREEN));
								} else if(status.equals("--")) {
									cell.setBackground(Colors.getColor(Colors.LIGHT_RED));
								} else {
									cell.setBackground(Colors.getColor(Colors.LIGHT_YELLOW));
								}
								text = status;
							}
							cell.setForeground(Colors.BLACK);
							cell.setText(text);
						}
						super.update(cell);
					}
				}
			});
		}
	}
}