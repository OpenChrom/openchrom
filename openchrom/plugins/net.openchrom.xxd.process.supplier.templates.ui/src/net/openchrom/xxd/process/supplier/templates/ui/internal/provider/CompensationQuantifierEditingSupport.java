/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
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
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;

public class CompensationQuantifierEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public CompensationQuantifierEditingSupport(ExtendedTableViewer tableViewer, String column) {

		super(tableViewer);
		this.column = column;
		if(column.equals(CompensationQuantifierLabelProvider.ADJUST_QUANTITATION_ENTRY)) {
			this.cellEditor = new CheckboxCellEditor(tableViewer.getTable());
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

		if(element instanceof CompensationSetting setting) {
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case CompensationQuantifierLabelProvider.INTERNAL_STANDARD:
					return setting.getInternalStandard();
				case CompensationQuantifierLabelProvider.EXPECTED_CONCENTRATION:
					return Double.toString(setting.getExpectedConcentration());
				case CompensationQuantifierLabelProvider.CONCENTRATION_UNIT:
					return setting.getConcentrationUnit();
				case CompensationQuantifierLabelProvider.TARGET_UNIT:
					return setting.getTargetUnit();
				case CompensationQuantifierLabelProvider.ADJUST_QUANTITATION_ENTRY:
					return setting.isAdjustQuantitationEntry();
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof CompensationSetting setting) {
			double result;
			switch(column) {
				/*
				 * Do not edit the name
				 */
				case CompensationQuantifierLabelProvider.INTERNAL_STANDARD:
					setting.setInternalStandard(value.toString());
					break;
				case CompensationQuantifierLabelProvider.EXPECTED_CONCENTRATION:
					result = convertValue(value);
					if(!Double.isNaN(result) && result > 0) {
						setting.setExpectedConcentration(result);
					}
					break;
				case CompensationQuantifierLabelProvider.CONCENTRATION_UNIT:
					setting.setConcentrationUnit(value.toString());
					break;
				case CompensationQuantifierLabelProvider.TARGET_UNIT:
					setting.setTargetUnit(value.toString());
					break;
				case CompensationQuantifierLabelProvider.ADJUST_QUANTITATION_ENTRY:
					setting.setAdjustQuantitationEntry((boolean)value);
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
