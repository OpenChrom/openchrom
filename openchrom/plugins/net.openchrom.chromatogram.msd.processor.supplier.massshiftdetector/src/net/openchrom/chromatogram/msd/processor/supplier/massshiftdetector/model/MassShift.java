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

public class MassShift {

	private int mz;
	private Set<Integer> shifts;
	private double probability;

	public MassShift(int mz) {
		this.mz = mz;
		shifts = new HashSet<Integer>();
	}

	public int getMz() {

		return mz;
	}

	public Set<Integer> getShifts() {

		return shifts;
	}

	public double getProbability() {

		return probability;
	}

	public void setProbability(double probability) {

		this.probability = probability;
	}
}
