/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
