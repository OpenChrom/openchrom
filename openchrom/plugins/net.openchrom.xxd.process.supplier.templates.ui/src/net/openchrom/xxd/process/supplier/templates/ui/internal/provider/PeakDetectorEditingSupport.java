/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class PeakDetectorEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public PeakDetectorEditingSupport(ExtendedTableViewer tableViewer, String column) {
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

		if(element instanceof DetectorSetting) {
			DetectorSetting setting = (DetectorSetting)element;
			switch(column) {
				case PeakDetectorLabelProvider.DETECTOR_TYPE:
					return setting.getDetectorType();
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
					String text = ((String)value).trim();
					if(!"".equals(text) && (text.equals(DetectorSetting.DETECTOR_TYPE_BB) || text.equals(DetectorSetting.DETECTOR_TYPE_VV))) {
						setting.setDetectorType(text);
					}
					break;
			}
			tableViewer.refresh();
		}
	}
}
