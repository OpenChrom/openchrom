/*******************************************************************************
 * Copyright (c) 2008, 2013 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.model;

import java.util.Date;

import net.openchrom.chromatogram.msd.model.core.AbstractChromatogramMSD;

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
