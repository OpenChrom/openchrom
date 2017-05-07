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

import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

public class CmsLibraryListTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof IRegularLibraryMassSpectrum && e2 instanceof IRegularLibraryMassSpectrum) {
			IRegularLibraryMassSpectrum massSpectrum1 = (IRegularLibraryMassSpectrum)e1;
			IRegularLibraryMassSpectrum massSpectrum2 = (IRegularLibraryMassSpectrum)e2;
			sortOrder = getSortOrder(massSpectrum1, massSpectrum2, massSpectrum1.getLibraryInformation(), massSpectrum2.getLibraryInformation());
		}
		if(getDirection() == ASCENDING) {
			sortOrder = -sortOrder;
		}
		return sortOrder;
	}

	private int getSortOrder(IScanMSD massSpectrum1, IScanMSD massSpectrum2, ILibraryInformation libraryInformation1, ILibraryInformation libraryInformation2) {

		int sortOrder = 0;
		switch(getPropertyIndex()) {
			case 0: // Name
				if(libraryInformation1 != null && libraryInformation2 != null) {
					sortOrder = libraryInformation2.getName().compareTo(libraryInformation1.getName());
				}
				break;
			case 1: // CAS Number
				if(libraryInformation1 != null && libraryInformation2 != null) {
					sortOrder = libraryInformation2.getCasNumber().compareTo(libraryInformation1.getCasNumber());
				}
				break;
			case 2: // Mol Weight
				if(libraryInformation1 != null && libraryInformation2 != null) {
					sortOrder = Double.compare(libraryInformation2.getMolWeight(), libraryInformation1.getMolWeight());
				}
				break;
			case 3: // Base Peak
				sortOrder = Double.compare(massSpectrum2.getBasePeak(), massSpectrum1.getBasePeak());
				break;
			case 4: // Base Peak Abundance
				sortOrder = Float.compare(massSpectrum2.getBasePeakAbundance(), massSpectrum1.getBasePeakAbundance());
				break;
			case 5: // Number of Ions
				sortOrder = Integer.compare(massSpectrum2.getNumberOfIons(), massSpectrum1.getNumberOfIons());
				break;
			case 6: // Formula
				if(libraryInformation1 != null && libraryInformation2 != null) {
					sortOrder = libraryInformation2.getFormula().compareTo(libraryInformation1.getFormula());
				}
				break;
			default:
				sortOrder = 0;
		}
		return sortOrder;
	}
}
