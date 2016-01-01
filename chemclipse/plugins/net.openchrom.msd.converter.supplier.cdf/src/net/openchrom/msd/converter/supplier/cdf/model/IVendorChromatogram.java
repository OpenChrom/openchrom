/*******************************************************************************
 * Copyright (c) 2013, 2016 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.model;

import java.util.Date;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;

public interface IVendorChromatogram extends IChromatogramMSD {

	public Date getDateOfExperiment();

	public void setDateOfExperiment(Date dateOfExperiment);
}
