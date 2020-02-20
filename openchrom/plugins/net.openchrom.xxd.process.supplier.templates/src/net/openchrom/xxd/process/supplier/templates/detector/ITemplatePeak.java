/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.detector;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;

/**
 * Defines the template of the peak that should be detected
 *
 */
public interface ITemplatePeak extends IScanRange {

	/**
	 * 
	 * @return the ion traces to use as marker ions
	 */
	IMarkedIons getIonTraces();

	/**
	 * 
	 * @return the desired {@link PeakType} to detect
	 */
	PeakType gePeakType();

	/**
	 * 
	 * @return <code>true</code> if the given range is just a hint and should be optimized if possible, false if this is a fixed range
	 */
	boolean isOptimizeRange();
}
