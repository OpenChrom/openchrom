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
package net.sf.jtables.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * 
 * Convenience class. Just extends {@link WriterTableBufferedImpl}.
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-02-28
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-28
 * 
 */
public class WriterTableString extends WriterTableBufferedImpl {

	/**
	 * Creates a new {@code WriterTableString} that will write to given {@link File}.
	 * 
	 * @param file
	 *            {@link File} to write to
	 * @throws IOException
	 *             if file is not accessible for writing
	 */
	public WriterTableString(final File file) throws IOException {
		super(file);
	}

	/**
	 * Creates a new {@code WriterTableString} that will write to given {@link OutputStream}.
	 * 
	 * @param stream
	 *            {@link OutputStream} to write to
	 * @throws IOException
	 *             if stream is not accessible for writing
	 */
	public WriterTableString(final OutputStream stream) throws IOException {
		super(stream);
	}

	/**
	 * Creates a new {@code WriterTableString} that will write to given {@link Writer}.
	 * 
	 * @param writer
	 *            {@link Writer} to write to
	 * @throws IOException
	 *             if writer is not accessible for writing
	 */
	public WriterTableString(final Writer writer) throws IOException {
		super(writer);
	}
}
