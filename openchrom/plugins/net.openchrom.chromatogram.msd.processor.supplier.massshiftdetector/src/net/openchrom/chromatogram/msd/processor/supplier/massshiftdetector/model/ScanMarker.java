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

import java.util.HashSet;
import java.util.Set;

public class ScanMarker {

	private int scan;
	private Set<MassShift> massShifts;
	private boolean validated;

	public ScanMarker(int scan) {
		this.scan = scan;
		this.massShifts = new HashSet<MassShift>();
	}

	public int getScan() {

		return scan;
	}

	public Set<MassShift> getMassShifts() {

		return massShifts;
	}

	public boolean isValidated() {

		return validated;
	}

	public void setValidated(boolean validated) {

		this.validated = validated;
	}
}
