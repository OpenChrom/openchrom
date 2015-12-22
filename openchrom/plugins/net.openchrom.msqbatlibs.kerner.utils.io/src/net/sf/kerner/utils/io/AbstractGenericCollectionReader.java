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
package net.sf.kerner.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public abstract class AbstractGenericCollectionReader<T> implements GenericCollectionReader<T> {

	/**
	 * 
	 */
	public Collection<T> readAll(File file) throws IOException {

		return readAll(new FileInputStream(file));
	}

	/**
	 * 
	 */
	public Collection<T> readAll(InputStream stream) throws IOException {

		return readAll(UtilIO.inputStreamToReader(stream));
	}
}
