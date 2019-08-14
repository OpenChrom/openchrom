/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IMassShift;

public class MassShiftListTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		//
		if(e1 instanceof IMassShift && e2 instanceof IMassShift) {
			IMassShift massShift1 = (IMassShift)e1;
			IMassShift massShift2 = (IMassShift)e2;
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = Double.compare(massShift2.getMz(), massShift1.getMz());
					break;
				case 1:
					sortOrder = Integer.compare(massShift2.getRetentionTimeReference(), massShift1.getRetentionTimeReference());
					break;
				case 2:
					sortOrder = Integer.compare(massShift2.getRetentionTimeIsotope(), massShift1.getRetentionTimeIsotope());
					break;
				case 3:
					sortOrder = Integer.compare(massShift2.getMassShiftLevel(), massShift1.getMassShiftLevel());
					break;
				case 4:
					sortOrder = Double.compare(massShift2.getCertainty(), massShift1.getCertainty());
					break;
			}
		}
		//
		if(getDirection() == ASCENDING) {
			sortOrder = -sortOrder;
		}
		return sortOrder;
	}
}
