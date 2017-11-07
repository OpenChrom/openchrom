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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt;

import java.util.List;

import org.eclipse.chemclipse.support.ui.provider.ListContentProvider;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editingsupport.ScanMarkerCheckBoxEditingSupport;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider.ScanMarkerListLabelProvider;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider.ScanMarkerListTableComparator;

public class ScanMarkerListUI extends ExtendedTableViewer {

	public static final int INDEX_VALIDATED = 3;
	private String[] titles = {"Scan #", "RT (Minutes) - Reference", "RT (Minutes) - Isotope", "Validated"};
	private int bounds[] = {200, 100, 100, 60};

	public ScanMarkerListUI(Composite parent, int style) {
		super(parent, style);
		createColumns();
	}

	private void createColumns() {

		createColumns(titles, bounds);
		//
		setLabelProvider(new ScanMarkerListLabelProvider());
		setContentProvider(new ListContentProvider());
		setComparator(new ScanMarkerListTableComparator());
		//
		setEditingSupport();
		getTable().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				// CTRL + SPACE
				if((e.stateMask & SWT.CTRL) == SWT.CTRL && e.keyCode == 32) {
					IStructuredSelection structuredSelection = getStructuredSelection();
					Object object = structuredSelection.getFirstElement();
					if(object instanceof IScanMarker) {
						IScanMarker scanMarker = (IScanMarker)object;
						scanMarker.setValidated(!scanMarker.isValidated());
						refresh();
					}
				}
			}
		});
	}

	private void setEditingSupport() {

		TableViewerColumn tableViewerColumn;
		List<TableViewerColumn> tableViewerColumns = getTableViewerColumns();
		//
		tableViewerColumn = tableViewerColumns.get(INDEX_VALIDATED);
		tableViewerColumn.setEditingSupport(new ScanMarkerCheckBoxEditingSupport(this));
	}
}
