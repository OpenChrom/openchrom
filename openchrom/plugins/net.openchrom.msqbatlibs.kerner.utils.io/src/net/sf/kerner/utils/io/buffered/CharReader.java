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
package net.sf.kerner.utils.io.buffered;

import java.io.IOException;

import net.sf.kerner.utils.io.UtilIO;

/**
 * A {@code CharReader} provides methods for reading characters.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-27
 */
public interface CharReader {

	/**
	 * Read the next bunch of chars, using a given buffer size.
	 * <p>
	 * If available number of characters is smaller than {@code bufferSize}, returned array contains all available characters and its size is equal to the number of read characters.
	 * </p>
	 * <p>
	 * If there is nothing (left) to read, {@code null} will be returned.
	 * </p>
	 * 
	 * @param bufferSize
	 *            number of chars to read
	 * @return an array of chars containing all chars that have been read
	 * @throws IOException
	 *             if anything goes wrong
	 */
	char[] nextChars(int bufferSize) throws IOException;

	/**
	 * Read the next bunch of chars, using a default buffer size ( {@link UtilIO#DEFAULT_BUFFER})
	 * <p>
	 * If available number of characters is smaller than {@code bufferSize}, returned array contains all available characters and its size is equal number of read characters.
	 * </p>
	 * <p>
	 * If there is nothing (left) to read, an array of size 0 will be returned.
	 * </p>
	 * 
	 * @return an array of chars containing all chars that have been read
	 * @throws IOException
	 *             if anything goes wrong
	 */
	char[] nextChars() throws IOException;

	/**
	 * Read the next single char.
	 * 
	 * @return the single char that has been read or -1 if there is nothing left to read
	 * @throws IOException
	 *             if anything goes wrong
	 */
	int nextChar() throws IOException;
}
