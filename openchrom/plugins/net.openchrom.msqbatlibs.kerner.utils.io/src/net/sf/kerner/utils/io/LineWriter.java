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

import java.io.IOException;

/**
 * A {@code LineWriter} writes a string line by line.
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
 * @version 2010-11-27
 */
public interface LineWriter {

	/**
	 * Write a string and append {@link UtilIO#NEW_LINE_STRING}.
	 * 
	 * @param line
	 *            string to write
	 * @throws IOException
	 *             if writing fails
	 */
	void writeNextLine(String line) throws IOException;
}
