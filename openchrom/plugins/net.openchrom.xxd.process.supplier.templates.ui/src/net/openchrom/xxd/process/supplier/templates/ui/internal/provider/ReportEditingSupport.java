/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReportStrategy;
import net.openchrom.xxd.process.supplier.templates.ui.swt.ReportListUI;
import net.openchrom.xxd.process.supplier.templates.util.ReportValidator;

public class ReportEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ReportListUI tableViewer;
	private String column;

	public ReportEditingSupport(ReportListUI tableViewer, String column) {

		super(tableViewer);
		this.column = column;
		if(column.equals(ReportLabelProvider.REPORT_STRATEGY)) {
			ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor((Composite)tableViewer.getControl());
			ComboViewer comboViewer = editor.getViewer();
			comboViewer.setContentProvider(ArrayContentProvider.getInstance());
			comboViewer.setInput(ReportValidator.REPORT_STRATEGIES);
			this.cellEditor = editor;
		} else {
			this.cellEditor = new TextCellEditor(tableViewer.getTable());
		}
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

		if(element instanceof ReportSetting) {
			ReportSetting setting = (ReportSetting)element;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case ReportLabelProvider.START_RETENTION_TIME:
					return Double.toString(setting.getStartRetentionTimeMinutes());
				case ReportLabelProvider.STOP_RETENTION_TIME:
					return Double.toString(setting.getStopRetentionTimeMinutes());
				case ReportLabelProvider.CAS_NUMBER:
					return setting.getCasNumber();
				case ReportLabelProvider.REPORT_STRATEGY:
					return setting.getReportStrategy();
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof ReportSetting) {
			ReportSetting setting = (ReportSetting)element;
			double result;
			String text;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case PeakIdentifierLabelProvider.START_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result <= setting.getStopRetentionTimeMinutes()) {
							setting.setStartRetentionTimeMinutes(result);
						}
					}
					break;
				case PeakIdentifierLabelProvider.STOP_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result >= setting.getStartRetentionTimeMinutes()) {
							setting.setStopRetentionTimeMinutes(result);
						}
					}
					break;
				case PeakIdentifierLabelProvider.CAS_NUMBER:
					text = ((String)value).trim();
					if(!"".equals(text)) {
						setting.setCasNumber(text);
					}
					break;
				case ReportLabelProvider.REPORT_STRATEGY:
					if(value instanceof ReportStrategy) {
						setting.setReportStrategy((ReportStrategy)value);
					}
					break;
			}
			//
			tableViewer.refresh();
			tableViewer.updateContent();
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
