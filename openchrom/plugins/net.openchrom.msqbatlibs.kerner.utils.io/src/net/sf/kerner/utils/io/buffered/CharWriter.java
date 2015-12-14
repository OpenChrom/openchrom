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

/**
 * A {@code CharWriter} provides methods for writing characters.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 */
public interface CharWriter {

	/**
	 * Write the next single char.
	 * 
	 * @param c
	 *            char to write
	 * @throws IOException
	 *             if writing fails
	 */
	void write(char c) throws IOException;

	/**
	 * Write the next bunch of chars
	 * 
	 * @param chars
	 *            char array to write
	 * @throws IOException
	 *             if writing fails
	 */
	void write(char[] chars) throws IOException;
}
