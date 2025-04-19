/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.io.support;

public interface IScanMarker {

	String getMassesDataset();

	void setMassesDataset(String massesDataset);

	String getIntensitiesDataset();

	void setIntensitiesDataset(String intensityDataset);

	int getLength();

	void setLength(int length);

	int getOffset();

	void setOffset(int offset);
}
