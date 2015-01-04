/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.settings;

import net.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettings;

public interface ICdkPeakIdentifierSettings extends IPeakIdentifierSettings {

	boolean isDeleteIdentificationsWithoutFormula();

	void setDeleteIdentificationsWithoutFormula(boolean deleteIdentificationsWithoutFormula);
}
