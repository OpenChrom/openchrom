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
package net.sf.jfasta.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
import net.sf.kerner.utils.io.ObjectWriter;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.AbstractBufferedWriter;

/**
 * 
 * A {@code FASTAFileWriter} writes {@link FASTAElement FASTAElement}s and {@link FASTAFile FASTAFile}s to
 * <ul>
 * <li>
 * a {@link java.io.File}</li>
 * <li>
 * a {@link java.io.Writer}</li>
 * <li>
 * an {@link java.io.OutputStream}</li>
 * </ul>
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
 * 
 * <p>
 * last reviewed: 2013-04-29
 * </p>
 * 
 * <p>
 * {@code FASTAFileWriter} is thread save.
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-30
 * 
 */
public class FASTAFileWriter extends AbstractBufferedWriter implements ObjectWriter<FASTAElement> {

	/**
	 * 
	 * Creates a {@code FASTAFileWriter} that writes to a file.
	 * 
	 * @param file
	 *            file to write to
	 * @throws IOException
	 *             if file is not accessible
	 */
	public FASTAFileWriter(final File file) throws IOException {
		super(file);
	}

	/**
	 * 
	 * Creates a {@code FASTAFileWriter} that writes to a stream.
	 * 
	 * @param stream
	 *            output stream to write to
	 */
	public FASTAFileWriter(final OutputStream stream) {
		super(stream);
	}

	/**
	 * 
	 * Creates a {@code FASTAFileWriter} that writes to a writer.
	 * 
	 * @param writer
	 *            writer to write to
	 */
	public FASTAFileWriter(final Writer writer) {
		super(writer);
	}

	/**
	 * Writes a {@link FASTAElement FASTAElement}.
	 * <p>
	 * <b>Note:</b> For big FASTA elements, prepare for equivalent memory usage.
	 * </p>
	 * 
	 */
	public synchronized void write(final FASTAElement e) throws IOException {

		super.writer.write(e.toString());
		super.writer.write(UtilIO.NEW_LINE_STRING);
	}

	/**
	 * 
	 * Writes a {@link FASTAFile FASTAFile} at once.
	 * <p>
	 * <b> Note:</b> For big FASTA files, use {@link #write(FASTAElement)} instead and write FASTA file element by element.
	 * </p>
	 * 
	 * @param e
	 *            FASTA file to write
	 * @throws IOException
	 *             if writing fails
	 */
	public synchronized void write(final FASTAFile e) throws IOException {

		super.writer.write(e.toString());
		super.writer.write(UtilIO.NEW_LINE_STRING);
	}
}
