/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractGenericReader2<T> extends AbstractGenericReader<T> implements GenericReader<T>, GenericCollectionReader<T> {

	protected final AbstractGenericCollectionReader<T> colReader = new AbstractGenericCollectionReader<T>() {

		public Collection<T> readAll(Reader reader) throws IOException {

			final Collection<T> result = new ArrayList<T>();
			T t = null;
			while((t = read(reader)) != null) {
				result.add(t);
			}
			return result;
		}
	};

	public Collection<T> readAll(File file) throws IOException {

		return colReader.readAll(file);
	}

	public Collection<T> readAll(Reader reader) throws IOException {

		return colReader.readAll(reader);
	}

	public Collection<T> readAll(InputStream stream) throws IOException {

		return colReader.readAll(stream);
	}
}
