/*******************************************************************************
 * Copyright (c) 2016 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * whitlow - initial API and implementation
*******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;

public class LibComponent {
	IRegularLibraryMassSpectrum libraryRef; // this ion was found in this library
	boolean mark; // used to identify if this library component is actually used
	int componentIndex;
	
	LibComponent(IRegularLibraryMassSpectrum libRef) {
		libraryRef = libRef;
		mark = false;
	}
}
