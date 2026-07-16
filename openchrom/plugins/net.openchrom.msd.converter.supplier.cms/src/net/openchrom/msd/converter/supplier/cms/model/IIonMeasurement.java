/*******************************************************************************
 * Copyright (c) 2016, 2026 Walter Whitlock.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
