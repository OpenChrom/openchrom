/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

public interface IMassShift {

	double getMz();

	void setMz(double mz);

	int getRetentionTimeReference();

	void setRetentionTimeReference(int retentionTimeReference);

	int getRetentionTimeIsotope();

	void setRetentionTimeIsotope(int retentionTimeIsotope);

	int getMassShiftLevel();

	void setMassShiftLevel(int massShiftLevel);

	double getCertainty();

	void setCertainty(double certainty);
}