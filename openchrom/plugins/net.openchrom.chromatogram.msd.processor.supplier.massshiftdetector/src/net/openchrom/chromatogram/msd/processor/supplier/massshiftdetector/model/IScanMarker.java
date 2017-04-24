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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

import java.util.Set;

public interface IScanMarker {

	int getScanNumber();

	void setScanNumber(int scanNumber);

	int getRetentionTimeReference();

	void setRetentionTimeReference(int retentionTimeReference);

	int getRetentionTimeIsotope();

	void setRetentionTimeIsotope(int retentionTimeIsotope);

	Set<IMassShift> getMassShifts();

	void setMassShifts(Set<IMassShift> massShifts);

	boolean isValidated();

	void setValidated(boolean validated);
}