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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;

public class TimeRatioEditingSupport extends EditingSupport {

	private static final Logger logger = Logger.getLogger(TimeRatioEditingSupport.class);
	//
	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public TimeRatioEditingSupport(ExtendedTableViewer tableViewer, String column) {
		super(tableViewer);
		this.column = column;
		this.cellEditor = new TextCellEditor(tableViewer.getTable());
		this.tableViewer = tableViewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {

		return tableViewer.isEditEnabled();
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof TimeRatio) {
			TimeRatio setting = (TimeRatio)element;
			switch(column) {
				case TimeRatioResultTitles.EXPECTED_RETENTION_TIME:
					return Double.toString(setting.getExpectedRetentionTime());
				case TimeRatioResultTitles.DEVIATION_WARN:
					return Double.toString(setting.getDeviationWarn());
				case TimeRatioResultTitles.DEVIATION_ERROR:
					return Double.toString(setting.getDeviationError());
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof TimeRatio) {
			TimeRatio setting = (TimeRatio)element;
			switch(column) {
				case TimeRatioResultTitles.EXPECTED_RETENTION_TIME:
					double expectedRetentionTime = parseDouble((String)value);
					if(expectedRetentionTime > 0) {
						setting.setExpectedRetentionTime(expectedRetentionTime);
					}
					break;
				case TimeRatioResultTitles.DEVIATION_WARN:
					double deviationWarn = parseDouble((String)value);
					if(deviationWarn > 0 && deviationWarn < setting.getDeviationError()) {
						setting.setDeviationWarn(deviationWarn);
					}
					break;
				case TimeRatioResultTitles.DEVIATION_ERROR:
					double deviationError = parseDouble((String)value);
					if(deviationError > 0 && deviationError > setting.getDeviationWarn()) {
						setting.setDeviationError(deviationError);
					}
					break;
			}
			tableViewer.refresh();
		}
	}

	private double parseDouble(String text) {

		double value = -1.0d;
		try {
			value = Double.parseDouble(text.trim());
		} catch(NumberFormatException e) {
			logger.warn(e);
		}
		return value;
	}
}
