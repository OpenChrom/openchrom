/*******************************************************************************
 * Copyright (c) 2013, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.cdf.model;

import java.util.Date;

import org.eclipse.chemclipse.msd.model.core.AbstractChromatogramMSD;

public class VendorChromatogram extends AbstractChromatogramMSD implements IVendorChromatogram {

	private static final long serialVersionUID = -346035662820396051L;
	private Date dateOfExperiment = new Date();

	public VendorChromatogram() {
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
