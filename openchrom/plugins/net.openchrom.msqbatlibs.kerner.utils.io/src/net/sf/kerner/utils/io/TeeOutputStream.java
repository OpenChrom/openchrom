/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream that sends bytes written to it to multiple output streams in
 * much the same way as the UNIX 'tee' command.
 * 
 * @author Sabre150
 */
public class TeeOutputStream extends OutputStream {

	// Obvious
	private final OutputStream[] ostream_;

	/**
	 * Constructs from a varags set of output streams
	 * 
	 * @param ostream
	 *            ... the varags array of OutputStreams
	 */
	public TeeOutputStream(final OutputStream... ostream) {
		ostream_ = ostream;
	}

	public void close() throws IOException {

		for(final OutputStream ostream : ostream_) {
			UtilIO.closeProperly(ostream);
		}
	}

	public void flush() throws IOException {

		for(final OutputStream ostream : ostream_) {
			ostream.flush();
		}
	}

	/**
	 * Writes an array of bytes to all OutputStreams
	 * 
	 * @param b
	 *            the bytes to write
	 * @param off
	 *            the offset to start writing from
	 * @param len
	 *            the number of bytes to write
	 * @throws IOException
	 *             from any of the OutputStreams
	 */
	public void write(final byte[] b, final int off, final int len) throws IOException {

		for(final OutputStream ostream : ostream_) {
			ostream.write(b, off, len);
		}
	}

	/**
	 * Writes a byte to both output streams
	 * 
	 * @param b
	 *            the byte to write
	 * @throws IOException
	 *             from any of the OutputStreams
	 */
	public void write(final int b) throws IOException {

		for(final OutputStream ostream : ostream_) {
			ostream.write(b);
		}
	}
}
