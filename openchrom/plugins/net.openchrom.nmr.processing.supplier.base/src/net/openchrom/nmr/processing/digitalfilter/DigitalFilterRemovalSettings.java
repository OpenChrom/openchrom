/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.digitalfilter;

import java.io.Serializable;

import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class DigitalFilterRemovalSettings implements Serializable {

	private static final long serialVersionUID = 4796560971210022576L;
	private int leftRotationFid = 0;
	private double dcOffsetMultiplicationFactor = Double.NaN;

	public int getLeftRotationFid() {

		return leftRotationFid;
	}

	public void setLeftRotationFid(int leftRotationFid) {

		this.leftRotationFid = leftRotationFid;
	}

	public double getDcOffsetMultiplicationFactor() {

		return dcOffsetMultiplicationFactor;
	}

	public void setDcOffsetMultiplicationFactor(double dcOffsetMultiplicationFactor) {

		this.dcOffsetMultiplicationFactor = dcOffsetMultiplicationFactor;
	}
}
