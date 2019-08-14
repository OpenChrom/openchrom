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
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;

public class PeakIntegratorEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;
	//
	private String[] integratorItems = new String[]{ //
			IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID, //
			IntegratorSetting.INTEGRATOR_NAME_MAX //
	};

	public PeakIntegratorEditingSupport(ExtendedTableViewer tableViewer, String column) {
		super(tableViewer);
		this.column = column;
		if(column.equals(PeakIntegratorLabelProvider.INTEGRATOR)) {
			this.cellEditor = new ComboBoxCellEditor(tableViewer.getTable(), //
					integratorItems, //
					SWT.READ_ONLY); //
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

		if(element instanceof IntegratorSetting) {
			IntegratorSetting setting = (IntegratorSetting)element;
			switch(column) {
				case PeakIntegratorLabelProvider.START_RETENTION_TIME:
					return Double.toString(setting.getStartRetentionTimeMinutes());
				case PeakIntegratorLabelProvider.STOP_RETENTION_TIME:
					return Double.toString(setting.getStopRetentionTimeMinutes());
				case PeakIntegratorLabelProvider.IDENTIFIER:
					return setting.getIdentifier();
				case PeakIntegratorLabelProvider.INTEGRATOR:
					String item = setting.getIntegrator();
					if(item.equals(IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID)) {
						return 0;
					} else if(item.equals(IntegratorSetting.INTEGRATOR_NAME_MAX)) {
						return 1;
					} else {
						return 0;
					}
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof IntegratorSetting) {
			IntegratorSetting setting = (IntegratorSetting)element;
			double result;
			switch(column) {
				case PeakIntegratorLabelProvider.START_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result <= setting.getStopRetentionTimeMinutes()) {
							setting.setStartRetentionTimeMinutes(result);
						}
					}
					break;
				case PeakIntegratorLabelProvider.STOP_RETENTION_TIME:
					result = convertValue(value);
					if(!Double.isNaN(result)) {
						if(result >= setting.getStartRetentionTimeMinutes()) {
							setting.setStopRetentionTimeMinutes(result);
						}
					}
					break;
				case PeakIntegratorLabelProvider.IDENTIFIER:
					setting.setIdentifier((String)value);
					break;
				case PeakIntegratorLabelProvider.INTEGRATOR:
					String integrator = integratorItems[(int)value];
					setting.setIntegrator(integrator);
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
