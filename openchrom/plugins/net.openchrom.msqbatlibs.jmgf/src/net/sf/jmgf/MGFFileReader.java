/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jmgf;

import java.io.IOException;

import net.sf.bioutils.proteomics.peak.FactoryPeak;
import net.sf.jmgf.impl.MGFElementIterator;
import net.sf.kerner.utils.io.buffered.IOIterable;

public interface MGFFileReader extends IOIterable<MGFElement> {

	FactoryPeak getFactoryPeak();

	MGFElementIterator getIterator() throws IOException;

	MGFFile read() throws IOException;

	void setFactoryPeak(FactoryPeak factoryPeak);
}
