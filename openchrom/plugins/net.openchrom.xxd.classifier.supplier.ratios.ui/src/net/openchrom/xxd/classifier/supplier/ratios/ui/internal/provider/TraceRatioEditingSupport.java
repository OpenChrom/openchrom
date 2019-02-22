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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;

public class TraceRatioEditingSupport extends EditingSupport {

	private static final Logger logger = Logger.getLogger(TraceRatioEditingSupport.class);
	//
	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public TraceRatioEditingSupport(ExtendedTableViewer tableViewer, String column) {
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

		if(element instanceof TraceRatio) {
			TraceRatio setting = (TraceRatio)element;
			switch(column) {
				case TraceRatioResultTitles.TEST_CASE:
					return setting.getTestCase();
				case TraceRatioResultTitles.EXPECTED_RATIO:
					return Double.toString(setting.getExpectedRatio());
				case TraceRatioResultTitles.DEVIATION_WARN:
					return Double.toString(setting.getDeviationWarn());
				case TraceRatioResultTitles.DEVIATION_ERROR:
					return Double.toString(setting.getDeviationError());
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof TraceRatio) {
			TraceRatio setting = (TraceRatio)element;
			switch(column) {
				case TraceRatioResultTitles.TEST_CASE:
					setting.setTestCase((String)value);
					break;
				case TraceRatioResultTitles.EXPECTED_RATIO:
					double expectedRatio = parseDouble((String)value);
					if(expectedRatio > 0) {
						setting.setExpectedRatio(expectedRatio);
					}
					break;
				case TraceRatioResultTitles.DEVIATION_WARN:
					double deviationWarn = parseDouble((String)value);
					if(deviationWarn > 0 && deviationWarn < setting.getDeviationError()) {
						setting.setDeviationWarn(deviationWarn);
					}
					break;
				case TraceRatioResultTitles.DEVIATION_ERROR:
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
