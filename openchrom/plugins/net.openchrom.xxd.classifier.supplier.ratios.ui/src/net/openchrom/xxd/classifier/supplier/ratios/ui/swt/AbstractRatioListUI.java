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

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractRatioTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.RatioFilter;

public abstract class AbstractRatioListUI extends ExtendedTableViewer {

	protected RatioFilter listFilter = new RatioFilter();

	public AbstractRatioListUI(Composite parent, int style) {
		super(parent, style);
	}

	public void setSearchText(String searchText, boolean caseSensitive) {

		listFilter.setSearchText(searchText, caseSensitive);
		refresh();
	}

	public void clear() {

		setInput(null);
	}

	protected void setCellColorProvider() {

		TableViewerColumn tableViewerColumn;
		/*
		 * Warn
		 */
		tableViewerColumn = getTableViewerColumn(AbstractRatioTitles.DEVIATION_WARN);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						IPeakRatio peakRatio = (IPeakRatio)cell.getElement();
						cell.setBackground(Colors.YELLOW);
						cell.setForeground(Colors.BLACK);
						cell.setText(">= " + Double.toString(peakRatio.getDeviationWarn()));
						super.update(cell);
					}
				}
			});
		}
		/*
		 * Error
		 */
		tableViewerColumn = getTableViewerColumn(AbstractRatioTitles.DEVIATION_ERROR);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						IPeakRatio peakRatio = (IPeakRatio)cell.getElement();
						cell.setBackground(Colors.RED);
						cell.setForeground(Colors.WHITE);
						cell.setText(">= " + Double.toString(peakRatio.getDeviationError()));
						super.update(cell);
					}
				}
			});
		}
	}
}
