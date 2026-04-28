/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mz5.internal.model;

public class FileInformation {

	private short majorVersion;
	private short minorVersion;
	private short didFiltering;
	private short deltaMZ;
	private short translateInten;

	public short getMajorVersion() {

		return majorVersion;
	}

	public void setMajorVersion(short majorVersion) {

		this.majorVersion = majorVersion;
	}

	public short getMinorVersion() {

		return minorVersion;
	}

	public void setMinorVersion(short minorVersion) {

		this.minorVersion = minorVersion;
	}

	public short getDidFiltering() {

		return didFiltering;
	}

	public void setDidFiltering(short didFiltering) {

		this.didFiltering = didFiltering;
	}

	public short getDeltaMZ() {

		return deltaMZ;
	}

	public void setDeltaMZ(short deltaMZ) {

		this.deltaMZ = deltaMZ;
	}

	public short getTranslateInten() {

		return translateInten;
	}

	public void setTranslateInten(short translateInten) {

		this.translateInten = translateInten;
	}
}
