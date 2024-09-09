/*******************************************************************************
 * Copyright (c) 2013, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.gaml.model;

import org.eclipse.chemclipse.msd.model.core.AbstractMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.IStandaloneMassSpectrum;

public class VendorMassSpectra extends AbstractMassSpectra implements IVendorMassSpectra {

	@Override
	public String getName() {

		IScanMSD scanMSD = this.getMassSpectrum(1);
		if(scanMSD instanceof IStandaloneMassSpectrum standaloneMassSpectrum) {
			return standaloneMassSpectrum.getName();
		} else {
			return super.getName();
		}
	}
}
