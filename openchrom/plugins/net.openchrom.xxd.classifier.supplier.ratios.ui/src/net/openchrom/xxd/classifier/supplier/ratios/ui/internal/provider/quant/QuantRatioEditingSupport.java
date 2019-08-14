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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;

public class QuantRatioEditingSupport extends EditingSupport {

	private static final Logger logger = Logger.getLogger(QuantRatioEditingSupport.class);
	//
	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public QuantRatioEditingSupport(ExtendedTableViewer tableViewer, String column) {
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

		if(element instanceof QuantRatio) {
			QuantRatio setting = (QuantRatio)element;
			switch(column) {
				case QuantRatioResultTitles.QUANTITATION_NAME:
					return setting.getQuantitationName();
				case QuantRatioResultTitles.EXPECTED_CONCENTRATION:
					return Double.toString(setting.getExpectedConcentration());
				case QuantRatioResultTitles.CONCENTRATION_UNIT:
					return setting.getConcentrationUnit();
				case QuantRatioResultTitles.DEVIATION_WARN:
					return Double.toString(setting.getDeviationWarn());
				case QuantRatioResultTitles.DEVIATION_ERROR:
					return Double.toString(setting.getDeviationError());
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof QuantRatio) {
			QuantRatio setting = (QuantRatio)element;
			switch(column) {
				case QuantRatioResultTitles.QUANTITATION_NAME:
					String quantitationName = (String)value;
					if(!"".equals(quantitationName)) {
						setting.setQuantitationName(quantitationName);
					}
					break;
				case QuantRatioResultTitles.EXPECTED_CONCENTRATION:
					double expectedConcentration = parseDouble((String)value);
					if(expectedConcentration > 0) {
						setting.setExpectedConcentration(expectedConcentration);
					}
					break;
				case QuantRatioResultTitles.CONCENTRATION_UNIT:
					String concentrationUnit = (String)value;
					if(!"".equals(concentrationUnit)) {
						setting.setConcentrationUnit(concentrationUnit);
					}
					break;
				case QuantRatioResultTitles.DEVIATION_WARN:
					double deviationWarn = parseDouble((String)value);
					if(deviationWarn > 0 && deviationWarn < setting.getDeviationError()) {
						setting.setDeviationWarn(deviationWarn);
					}
					break;
				case QuantRatioResultTitles.DEVIATION_ERROR:
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
