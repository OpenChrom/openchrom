/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;

public class StandardsAssignerEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public StandardsAssignerEditingSupport(ExtendedTableViewer tableViewer, String column) {
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

		if(element instanceof AssignerStandard) {
			AssignerStandard setting = (AssignerStandard)element;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case StandardsAssignerLabelProvider.START_RETENTION_TIME:
					return Double.toString(setting.getStartRetentionTimeMinutes());
				case StandardsAssignerLabelProvider.STOP_RETENTION_TIME:
					return Double.toString(setting.getStopRetentionTimeMinutes());
				case StandardsAssignerLabelProvider.CONCENTRATION:
					return Double.toString(setting.getConcentration());
				case StandardsAssignerLabelProvider.CONCENTRATION_UNIT:
					return setting.getConcentrationUnit();
				case StandardsAssignerLabelProvider.RESPONSE_FACTOR:
					return Double.toString(setting.getResponseFactor());
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof AssignerStandard) {
			AssignerStandard setting = (AssignerStandard)element;
			double result;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case StandardsAssignerLabelProvider.START_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result <= setting.getStopRetentionTimeMinutes()) {
							setting.setStartRetentionTimeMinutes(result);
						}
					}
					break;
				case StandardsAssignerLabelProvider.STOP_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result >= setting.getStartRetentionTimeMinutes()) {
							setting.setStopRetentionTimeMinutes(result);
						}
					}
					break;
				case StandardsAssignerLabelProvider.CONCENTRATION:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result > 0.0d) {
							setting.setConcentration(result);
						}
					}
					break;
				case StandardsAssignerLabelProvider.CONCENTRATION_UNIT:
					String text = ((String)value).trim();
					if(!"".equals(text)) {
						setting.setConcentrationUnit(text);
					}
					break;
				case StandardsAssignerLabelProvider.RESPONSE_FACTOR:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result > 0.0d) {
							setting.setResponseFactor(result);
						}
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
