/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.converter.supplier.cdf.model;

import java.util.Date;

import net.chemclipse.chromatogram.msd.model.core.AbstractChromatogramMSD;

public class CDFChromatogram extends AbstractChromatogramMSD implements ICDFChromatogram {

	private Date dateOfExperiment = new Date();

	public CDFChromatogram() {

		super();
	}

	// ---------------------------------------------ICDFChromatogram
	@Override
	public Date getDateOfExperiment() {

		return dateOfExperiment;
	}

	@Override
	public void setDateOfExperiment(Date dateOfExperiment) {

		this.dateOfExperiment = dateOfExperiment;
	}

	// ---------------------------------------------ICDFChromatogram
	@Override
	public String getName() {

		return extractNameFromFile("CDFChromatogram");
	}
}
