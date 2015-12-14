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
package net.sf.jfasta;

import java.io.IOException;

import net.sf.jfasta.impl.FASTAElementIterator;
import net.sf.kerner.utils.io.buffered.IOIterable;

/**
 * A {@code FASTAFileReader} is used to read a FASTA file.
 * <p>
 * last reviewed: 2013-04-29
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-06
 * 
 */
public interface FASTAFileReader extends IOIterable<FASTAElement> {

	/**
	 * Closes this reader.
	 * 
	 * @see java.io.Reader#close()
	 * 
	 */
	@Override
	void close();

	@Override
	FASTAElementIterator getIterator() throws IOException;

	/**
	 * 
	 * Reads all {@link FASTAElement}s at once in one {@link FASTAFile}.
	 * <p>
	 * Use this with care, since FASTA files can be huge!
	 * </p>
	 * 
	 * @return {@link FASTAFile FASTAFile} that has been read
	 * 
	 * @throws IOException
	 */
	FASTAFile read() throws IOException;
}
