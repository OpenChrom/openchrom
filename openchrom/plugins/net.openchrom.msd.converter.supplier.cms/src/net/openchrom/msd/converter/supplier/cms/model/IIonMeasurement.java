/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

public interface IIonMeasurement extends Comparable<IIonMeasurement> {

	@Override
	int compareTo(IIonMeasurement other);

	double getMZ();

	float getSignal();

	/**
	 * returns true if mass is within +/- tol of mz value
	 */
	boolean massEqual(double mass, double tol);

	/**
	 * returns true if mass is less than (mz-tol)
	 */
	boolean massLess(double mass, double tol);

	void setSignal(float signal);
}
