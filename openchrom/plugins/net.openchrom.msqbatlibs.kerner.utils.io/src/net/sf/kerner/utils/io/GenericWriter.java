/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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
import java.io.OutputStream;
import java.io.Writer;

/**
 * <p>
 * A {@code GenericWriter} provides the ability to write something to
 * <ul>
 * <li>a {@link java.io.File File}</li>
 * <li>a {@link java.io.Writer Writer}</li>
 * <li>an {@link java.io.OutputStream OutputStream}</li>
 * </ul>
 * </p>
 * <p>
 * TODO example
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 * @see java.io.File File
 * @see java.io.Writer Writer
 * @see java.io.OutputStream OutputStream
 */
public interface GenericWriter {

	/**
	 * <p>
	 * Write something to a {@link java.io.File File}.
	 * </p>
	 * 
	 * @param file
	 *            file to which is written
	 * @throws IOException
	 */
	void write(File file) throws IOException;

	/**
	 * <p>
	 * Write something to a {@link java.io.Writer Writer}.
	 * </p>
	 * 
	 * @param writer
	 *            writer to which is written
	 * @throws IOException
	 */
	void write(Writer writer) throws IOException;

	/**
	 * <p>
	 * Write something to an {@link java.io.OutputStream OutputStream}.
	 * </p>
	 * 
	 * @param stream
	 *            stream to which is written
	 * @throws IOException
	 */
	void write(OutputStream stream) throws IOException;
}
