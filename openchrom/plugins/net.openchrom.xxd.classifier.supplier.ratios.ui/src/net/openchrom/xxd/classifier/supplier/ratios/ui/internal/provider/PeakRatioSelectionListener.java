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

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.Activator;

public class PeakRatioSelectionListener extends SelectionAdapter implements SelectionListener {

	@Override
	public void widgetSelected(SelectionEvent e) {

		try {
			Object object = e.getSource();
			if(object instanceof Table) {
				Table table = (Table)object;
				int index = table.getSelectionIndex();
				TableItem tableItem = table.getItem(index);
				Object data = tableItem.getData();
				if(data instanceof IPeakRatio) {
					IPeakRatio peakRatio = (IPeakRatio)data;
					IPeak peak = peakRatio.getPeak();
					if(peak != null) {
						IEventBroker eventBroker = Activator.getDefault().getEventBroker();
						if(eventBroker != null) {
							eventBroker.send(IChemClipseEvents.TOPIC_PEAK_XXD_UPDATE_SELECTION, peak);
						}
					}
				}
			}
		} catch(Exception e1) {
			//
		}
	}
}
