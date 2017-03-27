/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ScanMarker_v1000;

public class ScanMarkerCheckBoxEditingSupport extends EditingSupport {

	private CheckboxCellEditor cellEditor;
	private TableViewer tableViewer;

	public ScanMarkerCheckBoxEditingSupport(TableViewer tableViewer) {
		super(tableViewer);
		this.tableViewer = tableViewer;
		cellEditor = new CheckboxCellEditor(tableViewer.getTable());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {

		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {

		return true;
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof ScanMarker_v1000) {
			IScanMarker scanMarker = (IScanMarker)element;
			return scanMarker.isValidated();
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof ScanMarker_v1000) {
			IScanMarker scanMarker = (IScanMarker)element;
			scanMarker.setValidated(Boolean.valueOf(value.toString()));
			tableViewer.refresh();
		}
	}
}
