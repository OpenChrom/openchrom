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
package net.openchrom.msd.process.supplier.cms.core;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;

public class LibComponent {

	private ICalibratedVendorLibraryMassSpectrum libraryRef; // this ion was found in this library
	private boolean mark; // used to identify if this library component is actually used
	private boolean isQuantitative;
	private int componentIndex;

	public LibComponent(ICalibratedVendorLibraryMassSpectrum libraryMassSpectrum) {
		libraryRef = libraryMassSpectrum;
		mark = false;
		isQuantitative = false;
	}

	public ICalibratedVendorLibraryMassSpectrum getLibraryRef() {

		return libraryRef;
	}

	public void setLibraryRef(ICalibratedVendorLibraryMassSpectrum libraryRef) {

		this.libraryRef = libraryRef;
	}

	public boolean isMark() {

		return mark;
	}

	public void setMark(boolean mark) {

		this.mark = mark;
	}

	public boolean isQuantitative() {

		return isQuantitative;
	}

	public void setQuantitative(boolean isQuantitative) {

		this.isQuantitative = isQuantitative;
	}

	public int getComponentIndex() {

		return componentIndex;
	}

	public void setComponentIndex(int componentIndex) {

		this.componentIndex = componentIndex;
	}
}
