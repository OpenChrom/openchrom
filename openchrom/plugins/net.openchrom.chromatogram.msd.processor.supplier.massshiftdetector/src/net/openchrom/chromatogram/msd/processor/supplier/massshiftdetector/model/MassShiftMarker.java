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

public class MassShiftMarker {

	private int scan;
	// TODO Details
	private boolean validated;

	public MassShiftMarker(int scan) {
		this.scan = scan;
	}

	public int getScan() {

		return scan;
	}

	public boolean isValidated() {

		return validated;
	}

	public void setValidated(boolean validated) {

		this.validated = validated;
	}
}
