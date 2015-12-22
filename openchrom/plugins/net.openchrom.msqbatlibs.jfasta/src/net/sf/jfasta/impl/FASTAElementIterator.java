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

import net.sf.jfasta.FASTAElement;
import net.sf.kerner.utils.io.buffered.AbstractIOIterator;

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
 * @version 2013-04-29
 * 
 */
public class FASTAElementIterator extends AbstractIOIterator<FASTAElement> {

	protected final char[] alphabet;

	public FASTAElementIterator(final BufferedReader reader) throws IOException {

		super(reader);
		// super.read();
		alphabet = null;
	}

	public FASTAElementIterator(final BufferedReader reader, final char[] alphabet) throws IOException {

		super(reader);
		// super.read();
		this.alphabet = alphabet;
	}

	public FASTAElementIterator(final File file) throws IOException {

		super(file);
		// super.read();
		alphabet = null;
	}

	public FASTAElementIterator(final File file, final char[] alphabet) throws IOException {

		super(file);
		// super.read();
		this.alphabet = alphabet;
	}

	public FASTAElementIterator(final InputStream stream) throws IOException {

		super(stream);
		// super.read();
		alphabet = null;
	}

	public FASTAElementIterator(final InputStream stream, final char[] alphabet) throws IOException {

		super(stream);
		// super.read();
		this.alphabet = alphabet;
	}

	public FASTAElementIterator(final Reader reader) throws IOException {

		super(reader);
		// super.read();
		alphabet = null;
	}

	public FASTAElementIterator(final Reader reader, final char[] alphabet) throws IOException {

		super(reader);
		// super.read();
		this.alphabet = alphabet;
	}

	protected FASTAElement doRead() throws IOException {

		final String header = new FASTAElementHeaderReader().read(super.reader);
		if(header == null)
			return null;
		final StringBuilder seq = getSequence();
		if(seq == null) {
			System.err.println("invalid fasta element [" + header + "]");
			return null;
		}
		seq.trimToSize();
		return new FASTAElementImpl(header, seq);
	}

	private StringBuilder getSequence() throws IOException {

		return new FASTASequenceReader(super.reader, alphabet).all();
	}
}
