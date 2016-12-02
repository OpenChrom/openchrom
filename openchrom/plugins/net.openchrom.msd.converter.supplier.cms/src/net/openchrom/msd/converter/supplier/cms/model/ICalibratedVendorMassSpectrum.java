/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;

public interface ICalibratedVendorMassSpectrum extends IRegularLibraryMassSpectrum {

	/*
	 * TODO WALTER
	 * Add the CMS specific field here.
	 */
	/**
	 * Returns the source.
	 * 
	 * @return String
	 */
	String getSource();

	/**
	 * Sets the source.
	 * 
	 * @param source
	 */
	void setSource(String source);
}
