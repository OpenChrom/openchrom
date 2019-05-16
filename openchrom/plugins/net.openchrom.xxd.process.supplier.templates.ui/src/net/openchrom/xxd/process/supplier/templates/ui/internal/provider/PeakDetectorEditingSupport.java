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
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetectorEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public PeakDetectorEditingSupport(ExtendedTableViewer tableViewer, String column) {
		super(tableViewer);
		this.column = column;
		if(column.equals(PeakDetectorLabelProvider.OPTIMIZE_RANGE)) {
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

		if(element instanceof DetectorSetting) {
			DetectorSetting setting = (DetectorSetting)element;
			switch(column) {
				case PeakDetectorLabelProvider.DETECTOR_TYPE:
					return setting.getDetectorType();
				case PeakDetectorLabelProvider.TRACES:
					return setting.getTraces();
				case PeakDetectorLabelProvider.OPTIMIZE_RANGE:
					return setting.isOptimizeRange();
				case PeakDetectorLabelProvider.REFERENCE_IDENTIFIER:
					return setting.getReferenceIdentifier();
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof DetectorSetting) {
			DetectorSetting setting = (DetectorSetting)element;
			switch(column) {
				case PeakDetectorLabelProvider.DETECTOR_TYPE:
					String detectorType = ((String)value).trim();
					if(!"".equals(detectorType) && (detectorType.equals(DetectorSetting.DETECTOR_TYPE_BB) || detectorType.equals(DetectorSetting.DETECTOR_TYPE_VV))) {
						setting.setDetectorType(detectorType);
					}
					break;
				case PeakDetectorLabelProvider.TRACES:
					String traces = ((String)value).trim();
					PeakDetectorValidator validator = new PeakDetectorValidator();
					String message = validator.validateTraces(traces);
					if(message == null) {
						setting.setTraces(traces);
					}
					break;
				case PeakDetectorLabelProvider.OPTIMIZE_RANGE:
					setting.setOptimizeRange((boolean)value);
					break;
				case PeakDetectorLabelProvider.REFERENCE_IDENTIFIER:
					String referenceIdentifier = ((String)value).trim();
					setting.setReferenceIdentifier(referenceIdentifier);
					break;
			}
			tableViewer.refresh();
		}
	}
}
