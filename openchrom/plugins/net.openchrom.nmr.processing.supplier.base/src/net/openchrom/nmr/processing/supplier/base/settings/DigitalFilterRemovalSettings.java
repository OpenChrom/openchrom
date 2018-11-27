/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jan - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings;

public class DigitalFilterRemovalSettings {

	private double dspPhaseFactor;
	private double leftRotationFid;
	private double leftRotationFidOriginal;

	public double getDspPhaseFactor() {

		return dspPhaseFactor;
	}

	public void setDspPhaseFactor(double dspPhaseFactor) {

		this.dspPhaseFactor = dspPhaseFactor;
	}

	public double getLeftRotationFid() {

		return leftRotationFid;
	}

	public void setLeftRotationFid(double leftRotationFid) {

		this.leftRotationFid = leftRotationFid;
	}

	public double getLeftRotationFidOriginal() {

		return leftRotationFidOriginal;
	}

	public void setLeftRotationFidOriginal(double leftRotationFidOriginal) {

		this.leftRotationFidOriginal = leftRotationFidOriginal;
	}
}
