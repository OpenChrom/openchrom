/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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