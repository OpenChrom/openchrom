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
package net.sf.kerner.utils.io.lazy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;

import net.sf.kerner.utils.io.GenericReader;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

/**
 * A {@code LazyStringReader} provides the ability to read a string quickly from
 * <ul>
 * <li>a {@link java.io.File}</li>
 * <li>a {@link java.io.Writer}</li>
 * <li>an {@link java.io.InputStream}</li>
 * </ul>
 * </p>
 * <p>
 * <b>Attention:</b> reading is not buffered! If you want to read large files, consider to use {@link BufferedStringReader} instead.
 * </p>
 * <p>
 * <b>Example:</b>
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * &#064;Test
 * public final void example() throws IOException {
 * 
 * 	final java.io.StringReader sr = new java.io.StringReader(&quot;Hallo Welt!&quot;);
 * 	assertEquals(&quot;Hallo Welt!&quot;, reader.read(sr));
 * }
 * </pre>
 * 
 * @author Alexander Kerner
 * @see java.io.File
 * @see java.io.Reader
 * @see java.io.InputStream
 * @version 2010-09-11
 */
public class LazyStringReader implements GenericReader<String> {

	public String read(File file) throws IOException {

		return read(UtilIO.getInputStreamFromFile(file));
	}

	public String read(Reader reader) throws IOException {

		if(reader == null)
			throw new NullPointerException();
		final StringWriter writer = new StringWriter();
		try {
			UtilIO.readerToWriter(reader, writer);
			return writer.toString();
		} finally {
			UtilIO.closeProperly(reader);
			UtilIO.closeProperly(writer);
		}
	}

	public String read(InputStream stream) throws IOException {

		if(stream == null)
			throw new NullPointerException();
		return read(UtilIO.inputStreamToReader(stream));
	}
}
