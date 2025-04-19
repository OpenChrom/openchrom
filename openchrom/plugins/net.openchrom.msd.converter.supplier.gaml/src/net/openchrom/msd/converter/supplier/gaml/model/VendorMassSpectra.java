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
