/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class IonMeasurementListTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof IIonMeasurement ionMeasurement1 && e2 instanceof IIonMeasurement ionMeasurement2) {
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
