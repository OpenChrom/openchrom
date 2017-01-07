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
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class IonMeasurementListTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof IIonMeasurement && e2 instanceof IIonMeasurement) {
			IIonMeasurement ionMeasurement1 = (IIonMeasurement)e1;
			IIonMeasurement ionMeasurement2 = (IIonMeasurement)e2;
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = Double.compare(ionMeasurement2.getMZ(), ionMeasurement1.getMZ());
					break;
				case 1:
					sortOrder = Float.compare(ionMeasurement2.getSignal(), ionMeasurement1.getSignal());
					break;
			}
		}
		if(getDirection() == ASCENDING) {
			sortOrder = -sortOrder;
		}
		return sortOrder;
	}
}
