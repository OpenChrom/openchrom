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
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.util.ReviewValidator;

public class PeakReviewEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public PeakReviewEditingSupport(ExtendedTableViewer tableViewer, String column) {
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

		if(element instanceof ReviewSetting) {
			ReviewSetting setting = (ReviewSetting)element;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case PeakReviewLabelProvider.START_RETENTION_TIME:
					return Double.toString(setting.getStartRetentionTimeMinutes());
				case PeakReviewLabelProvider.STOP_RETENTION_TIME:
					return Double.toString(setting.getStopRetentionTimeMinutes());
				case PeakReviewLabelProvider.CAS_NUMBER:
					return setting.getCasNumber();
				case PeakReviewLabelProvider.TRACES:
					return setting.getTraces();
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof ReviewSetting) {
			ReviewSetting setting = (ReviewSetting)element;
			double result;
			String text;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case PeakReviewLabelProvider.START_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result <= setting.getStopRetentionTimeMinutes()) {
							setting.setStartRetentionTimeMinutes(result);
						}
					}
					break;
				case PeakReviewLabelProvider.STOP_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result >= setting.getStartRetentionTimeMinutes()) {
							setting.setStopRetentionTimeMinutes(result);
						}
					}
					break;
				case PeakReviewLabelProvider.CAS_NUMBER:
					text = ((String)value).trim();
					if(!"".equals(text)) {
						setting.setCasNumber(text);
					}
					break;
				case PeakReviewLabelProvider.TRACES:
					String traces = ((String)value).trim();
					ReviewValidator validator = new ReviewValidator();
					String message = validator.validateTraces(traces);
					if(message == null) {
						setting.setTraces(traces);
					}
					break;
			}
			tableViewer.refresh();
		}
	}

	private double convertValue(Object value) {

		double result = Double.NaN;
		try {
			result = Double.parseDouble(((String)value).trim());
		} catch(NumberFormatException e) {
			//
		}
		return result;
	}
}
