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
package net.sf.jfasta.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.jfasta.FASTAFile;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.AbstractBufferedReader;
import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

/**
 * 
 * TODO description
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
 * last reviewed: 2013-04-29
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 0000-00-00
 * 
 */
class FASTASequenceReader extends AbstractBufferedReader {

	protected final BufferedStringReader reader2 = new BufferedStringReader(super.reader);
	protected final char[] alphabet;

	public FASTASequenceReader(final BufferedReader reader) throws IOException {

		super(reader);
		alphabet = null;
	}

	public FASTASequenceReader(final BufferedReader reader, final char[] alphabet) throws IOException {

		super(reader);
		this.alphabet = alphabet;
	}

	public FASTASequenceReader(final File file) throws IOException {

		super(file);
		alphabet = null;
	}

	public FASTASequenceReader(final File file, final char[] alphabet) throws IOException {

		super(file);
		this.alphabet = alphabet;
	}

	public FASTASequenceReader(final InputStream stream) throws IOException {

		super(stream);
		alphabet = null;
	}

	public FASTASequenceReader(final InputStream stream, final char[] alphabet) throws IOException {

		super(stream);
		this.alphabet = alphabet;
	}

	public FASTASequenceReader(final Reader reader) throws IOException {

		super(reader);
		alphabet = null;
	}

	public FASTASequenceReader(final Reader reader, final char[] alphabet) throws IOException {

		super(reader);
		this.alphabet = alphabet;
	}

	/**
	 * Reads in sequence until sequence is complete, using default buffer size.
	 */
	public StringBuilder all() throws IOException {

		return all(UtilIO.DEFAULT_BUFFER);
	}

	/**
	 * Reads in sequence until sequence is complete, using internal buffer size
	 * of {@code buffersize}.
	 */
	public StringBuilder all(final int bufferSize) throws IOException {

		final StringBuilder result = new StringBuilder(bufferSize);
		StringBuilder buffer = null;
		while((buffer = nextChars(bufferSize)) != null)
			result.append(buffer);
		if(result.length() == 0)
			return null;
		return result;
	}

	public synchronized StringBuilder nextChars() throws IOException {

		return nextChars(UtilIO.DEFAULT_BUFFER);
	}

	/**
	 * A FASTA sequence must not start with {@link FASTAFile#HEADER_IDENT}; {@code null} will be returned in this case.
	 */
	public synchronized StringBuilder nextChars(final int buffer) throws IOException {

		final StringBuilder result = new StringBuilder(buffer);
		for(int i = 0; i < buffer; i++) {
			super.reader.mark(1);
			final int ci = reader2.nextChar();
			if(ci < 0) {
				// nothing left to read
				break;
			}
			final char c = (char)ci;
			// trim sequence
			if(Character.isWhitespace(c))
				continue;
			// seq end reached
			if(c == FASTAFile.HEADER_IDENT) {
				super.reader.reset();
				break;
			}
			if(alphabet != null) {
				// check validity
				boolean ok = false;
				for(final char s : alphabet) {
					if(s == c) {
						// matches
						ok = true;
						break;
					}
				}
				if(!ok)
					throw new IllegalArgumentException("Illegal character [" + c + "]");
			}
			result.append(c);
		}
		if(result.length() < 1)
			return null;
		return result;
	}
}
