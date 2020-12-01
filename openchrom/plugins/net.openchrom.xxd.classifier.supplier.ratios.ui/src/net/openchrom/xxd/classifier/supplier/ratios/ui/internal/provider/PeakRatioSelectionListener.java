/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - adjust for new API
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.swt.ui.notifier.UpdateNotifierUI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;

public class PeakRatioSelectionListener extends SelectionAdapter implements SelectionListener, ISelectionChangedListener {

	@Override
	public void widgetSelected(SelectionEvent e) {

		try {
			Object object = e.getSource();
			if(object instanceof Table) {
				Table table = (Table)object;
				int index = table.getSelectionIndex();
				TableItem tableItem = table.getItem(index);
				Object data = tableItem.getData();
				handleSelection(e.display, data);
			}
		} catch(Exception e1) {
			//
		}
	}

	public void handleSelection(Display display, Object data) {

		if(data instanceof IPeakRatio) {
			IPeakRatio peakRatio = (IPeakRatio)data;
			IPeak peak = peakRatio.getPeak();
			if(peak != null) {
				UpdateNotifierUI.update(display, peak);
			}
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

		ISelection selection = event.getSelection();
		if(selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			handleSelection(Display.getDefault(), element);
		}
	}
}
