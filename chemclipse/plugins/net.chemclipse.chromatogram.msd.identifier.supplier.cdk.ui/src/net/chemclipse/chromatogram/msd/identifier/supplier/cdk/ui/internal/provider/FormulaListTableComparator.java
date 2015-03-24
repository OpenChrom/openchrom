/*******************************************************************************
 * Copyright (c) 2013, 2015 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.internal.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import net.chemclipse.chromatogram.msd.identifier.supplier.cdk.formula.NameAndRating;

public class FormulaListTableComparator extends ViewerComparator {

	private int propertyIndex;
	private static final int ASCENDING = 0;
	// private static final int DESCENDING = -1;
	private int direction = ASCENDING;

	public FormulaListTableComparator() {

		propertyIndex = 0;
		direction = ASCENDING;
	}

	public void setColumn(int column) {

		if(column == this.propertyIndex) {
			// Toggle the direction
			direction = 1 - direction;
		} else {
			this.propertyIndex = column;
			direction = ASCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		/*
		 * SYNCHRONIZE: PeakListLabelProvider PeakListLabelComparator PeakListView
		 */
		int sortOrder = 0;
		if(e1 instanceof NameAndRating && e2 instanceof NameAndRating) {
			NameAndRating element1 = (NameAndRating)e1;
			NameAndRating element2 = (NameAndRating)e2;
			switch(propertyIndex) {
				case 0: // Formula
					sortOrder = (element2.getName().length() > element1.getName().length()) ? 1 : -1;
					break;
				case 1: // Rating
					sortOrder = (element2.getRating() > element1.getRating()) ? 1 : -1;
					break;
				default:
					sortOrder = 0;
			}
		}
		if(direction == ASCENDING) {
			sortOrder = -sortOrder;
		}
		return sortOrder;
	}
}
