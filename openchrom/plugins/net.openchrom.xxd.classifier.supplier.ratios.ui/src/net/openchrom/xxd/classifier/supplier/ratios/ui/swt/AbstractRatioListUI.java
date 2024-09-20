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

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.updates.IUpdateListener;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractPeakRatioTitles;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.PeakRatioFilter;

public abstract class AbstractRatioListUI extends ExtendedTableViewer {

	private PeakRatioFilter peakRatioFilter = new PeakRatioFilter();
	private IUpdateListener updateListener;

	protected AbstractRatioListUI(Composite parent, int style) {

		super(parent, style);
	}

	public PeakRatioFilter getPeakRatioFilter() {

		return peakRatioFilter;
	}

	public void setUpdateListener(IUpdateListener updateListener) {

		this.updateListener = updateListener;
	}

	public void updateContent() {

		if(updateListener != null) {
			updateListener.update();
		}
	}

	public void setSearchText(String searchText, boolean caseSensitive) {

		peakRatioFilter.setSearchText(searchText, caseSensitive);
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
		tableViewerColumn = getTableViewerColumn(AbstractPeakRatioTitles.DEVIATION_WARN);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						if(cell.getElement() instanceof IPeakRatio peakRatio) {
							cell.setBackground(Colors.LIGHT_YELLOW);
							cell.setForeground(Colors.BLACK);
							cell.setText("> " + Double.toString(peakRatio.getDeviationWarn()));
							super.update(cell);
						}
					}
				}
			});
		}
		/*
		 * Error
		 */
		tableViewerColumn = getTableViewerColumn(AbstractPeakRatioTitles.DEVIATION_ERROR);
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						if(cell.getElement() instanceof IPeakRatio peakRatio) {
							cell.setBackground(Colors.LIGHT_RED);
							cell.setForeground(Colors.BLACK);
							cell.setText("> " + Double.toString(peakRatio.getDeviationError()));
							super.update(cell);
						}
					}
				}
			});
		}
	}
}