/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@code AbstractGenericReader} is a prototye implementation for {@link net.sf.kerner.utils.io.GenericReader
 * GenericReader}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-26
 */
public abstract class AbstractGenericReader<T> implements GenericReader<T> {

	// Implement //
	/**
	 * 
	 */
	public T read(File file) throws IOException {

		return read(new FileInputStream(file));
	}

	/**
	 * 
	 */
	public T read(InputStream stream) throws IOException {

		return read(UtilIO.inputStreamToReader(stream));
	}
}
