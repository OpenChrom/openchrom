/*******************************************************************************
 * Copyright (c) 2014, 2016 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings;

import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.AbstractPeakIdentifierSettings;

public class VendorPeakIdentifierSettings extends AbstractPeakIdentifierSettings implements IVendorPeakIdentifierSettings {

	private boolean deleteIdentificationsWithoutFormula;

	@Override
	public boolean isDeleteIdentificationsWithoutFormula() {

		return deleteIdentificationsWithoutFormula;
	}

	@Override
	public void setDeleteIdentificationsWithoutFormula(boolean deleteIdentificationsWithoutFormula) {

		this.deleteIdentificationsWithoutFormula = deleteIdentificationsWithoutFormula;
	}
}
