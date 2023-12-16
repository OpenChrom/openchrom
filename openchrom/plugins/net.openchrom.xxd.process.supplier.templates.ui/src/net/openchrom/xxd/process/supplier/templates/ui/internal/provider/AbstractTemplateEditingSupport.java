/*******************************************************************************
 * Copyright (c) 2022, 2023 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.ui.swt.AbstractTemplateListUI;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateValidator;
import net.openchrom.xxd.process.supplier.templates.util.ReportValidator;

public abstract class AbstractTemplateEditingSupport extends EditingSupport {

	protected String[] integratorItems = new String[]{ //
			IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID, //
			IntegratorSetting.INTEGRATOR_NAME_MAX //
	};
	//
	private AbstractTemplateListUI tableViewer;
	private String column;
	private CellEditor cellEditor;

	protected AbstractTemplateEditingSupport(AbstractTemplateListUI tableViewer, String column) {

		super(tableViewer);
		this.tableViewer = tableViewer;
		this.column = column;
		EnumLabelProvider enumLabelProvider = new EnumLabelProvider();
		//
		if(column.equals(AbstractTemplateLabelProvider.OPTIMIZE_RANGE)) {
			this.cellEditor = new CheckboxCellEditor(tableViewer.getTable());
		} else if(column.equals(AbstractTemplateLabelProvider.PEAK_TYPE)) {
			ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor((Composite)tableViewer.getControl());
			ComboViewer comboViewer = editor.getViewer();
			comboViewer.setContentProvider(ArrayContentProvider.getInstance());
			comboViewer.setInput(AbstractTemplateValidator.DETECTOR_TYPES);
			comboViewer.setLabelProvider(enumLabelProvider);
			this.cellEditor = editor;
		} else if(column.equals(AbstractTemplateLabelProvider.POSITION_DIRECTIVE)) {
			ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor((Composite)tableViewer.getControl());
			ComboViewer comboViewer = editor.getViewer();
			comboViewer.setContentProvider(ArrayContentProvider.getInstance());
			comboViewer.setInput(PositionDirective.values());
			comboViewer.setLabelProvider(enumLabelProvider);
			this.cellEditor = editor;
		} else if(column.equals(AbstractTemplateLabelProvider.REPORT_STRATEGY)) {
			ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor((Composite)tableViewer.getControl());
			ComboViewer comboViewer = editor.getViewer();
			comboViewer.setContentProvider(ArrayContentProvider.getInstance());
			comboViewer.setInput(ReportValidator.REPORT_STRATEGIES);
			this.cellEditor = editor;
		} else if(column.equals(AbstractTemplateLabelProvider.INTEGRATOR)) {
			this.cellEditor = new ComboBoxCellEditor(tableViewer.getTable(), //
					integratorItems, //
					SWT.READ_ONLY); //
		} else {
			this.cellEditor = new TextCellEditor(tableViewer.getTable());
		}
	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {

		return tableViewer.isEditEnabled();
	}

	protected AbstractTemplateListUI getTableViewer() {

		return tableViewer;
	}

	protected String getColumn() {

		return column;
	}

	protected CellEditor getCellEditor() {

		return cellEditor;
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof AbstractSetting setting) {
			switch(column) {
				case AbstractTemplateLabelProvider.POSITION_START:
					return Double.toString(setting.getPositionStart());
				case AbstractTemplateLabelProvider.POSITION_STOP:
					return Double.toString(setting.getPositionStop());
				case AbstractTemplateLabelProvider.POSITION_DIRECTIVE:
					return setting.getPositionDirective();
			}
		}
		//
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof AbstractSetting setting) {
			switch(column) {
				case AbstractTemplateLabelProvider.POSITION_START:
					double start = convertValue(value);
					if(!Double.isNaN(start)) {
						if(start <= setting.getPositionStop()) {
							setting.setPositionStart(start);
						}
					}
					break;
				case AbstractTemplateLabelProvider.POSITION_STOP:
					double stop = convertValue(value);
					if(!Double.isNaN(stop)) {
						if(stop >= setting.getPositionStart()) {
							setting.setPositionStop(stop);
						}
					}
					break;
				case AbstractTemplateLabelProvider.POSITION_DIRECTIVE:
					PositionDirective positionDirective = setting.getPositionDirective();
					if(value instanceof PositionDirective positionDirectiveNew) {
						double positionStart = getAdjustedPosition(positionDirective, positionDirectiveNew, setting.getPositionStart());
						double positionStop = getAdjustedPosition(positionDirective, positionDirectiveNew, setting.getPositionStop());
						setting.setPositionDirective(positionDirectiveNew);
						setting.setPositionStart(positionStart);
						setting.setPositionStop(positionStop);
					}
					break;
			}
		}
	}

	private double getAdjustedPosition(PositionDirective positionDirectiveSource, PositionDirective positionDirectiveSink, double value) {

		if(!positionDirectiveSource.equals(positionDirectiveSink)) {
			switch(positionDirectiveSink) {
				case RETENTION_TIME_MS:
					if(positionDirectiveSource.equals(PositionDirective.RETENTION_TIME_MIN)) {
						value = value * IChromatogramOverview.MINUTE_CORRELATION_FACTOR;
					}
					break;
				case RETENTION_TIME_MIN:
					if(positionDirectiveSource.equals(PositionDirective.RETENTION_TIME_MS)) {
						value = value / IChromatogramOverview.MINUTE_CORRELATION_FACTOR;
					}
					break;
				default:
					/*
					 * RETENTION_INDEX
					 * No action if sink is RI, because the RI can't be calculated dynamically.
					 */
					break;
			}
		}
		//
		return value;
	}

	protected double convertValue(Object value) {

		double result = Double.NaN;
		try {
			result = Double.parseDouble(((String)value).trim());
		} catch(NumberFormatException e) {
			//
		}
		return result;
	}

	protected void updateTableViewer() {

		tableViewer.refresh();
		tableViewer.updateContent();
	}
}
