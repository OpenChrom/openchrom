/*******************************************************************************
 * Copyright (c) 2016, 2025 Walter Whitlock.
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
package net.openchrom.msd.process.supplier.cms.core;

public class LibIon extends GenIon {

	// IRegularLibraryMassSpectrum libraryRef; // this ion was found in this library
	private LibComponent componentRef; // this ion came from this library component

	public LibIon(double mass, double abundance, int compSequence, LibComponent ref) {
		super(mass, abundance);
		setIonCompSequence(compSequence);
		componentRef = ref;
	}

	public LibComponent getComponentRef() {

		return componentRef;
	}

	public void setComponentRef(LibComponent componentRef) {

		this.componentRef = componentRef;
	}
}
