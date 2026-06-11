/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacement;

public class NameReplacementsEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private ExtendedTableViewer tableViewer;
	private String column;

	public NameReplacementsEditingSupport(ExtendedTableViewer tableViewer, String column) {

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

		/*
		 * Do not edit the name
		 */
		if(element instanceof NameReplacement setting) {
			switch(column) {
				case NameReplacementsLabelProvider.SYNONYM:
					return setting.getSynonym();
			}
		}
		// only TextCellEditor thus "" to match type
		return "";
	}

	@Override
	protected void setValue(Object element, Object value) {

		/*
		 * Do not edit the name
		 */
		if(element instanceof NameReplacement setting) {
			switch(column) {
				case NameReplacementsLabelProvider.SYNONYM:
					setting.setSynonym(value.toString());
					break;
			}
			tableViewer.refresh();
		}
	}
}