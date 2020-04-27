/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakStatusComparator;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakStatusLabelProvider;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReviewSupport;

public class PeakStatusListUI extends ExtendedTableViewer {

	private static final String[] TITLES = PeakStatusLabelProvider.TITLES;
	private static final int[] BOUNDS = PeakStatusLabelProvider.BOUNDS;
	//
	private PeakStatusLabelProvider labelProvider = new PeakStatusLabelProvider();
	private PeakStatusComparator tableComparator = new PeakStatusComparator();
	private ReviewSetting reviewSetting;

	public PeakStatusListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	public void clear() {

		setInput(null);
	}

	public void setReviewSetting(ReviewSetting reviewSetting) {

		this.reviewSetting = reviewSetting;
	}

	private void createColumns() {

		createColumns(TITLES, BOUNDS);
		setLabelProvider(labelProvider);
		setContentProvider(new ListContentProvider());
		setComparator(tableComparator);
		setCellColorProvider();
	}

	private void setCellColorProvider() {

		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		TableViewerColumn tableViewerColumn = tableViewerColumns.get(PeakStatusLabelProvider.INDEX_CLASSIFICATION);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						IPeak peak = (IPeak)cell.getElement();
						boolean isCompoundAvailable = ReviewSupport.isCompoundAvailable(peak.getTargets(), reviewSetting);
						boolean isPeakReviewed = ReviewSupport.isPeakReviewed(peak);
						Color background = null;
						if(isCompoundAvailable) {
							if(isPeakReviewed) {
								background = Colors.GREEN;
							} else {
								background = Colors.YELLOW;
							}
						} else {
							background = Colors.GRAY;
						}
						cell.setBackground(background);
						cell.setForeground(Colors.BLACK);
						cell.setText(isPeakReviewed ? "OK" : "?");
						super.update(cell);
					}
				}
			});
		}
	}
}
