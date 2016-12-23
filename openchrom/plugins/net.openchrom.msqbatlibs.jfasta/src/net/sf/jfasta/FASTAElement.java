/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import net.sf.kerner.utils.io.UtilIO;

/**
 * A {@code FASTAElement} represents one entry in a (multi) fasta file.
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * >gi|40806168|ref|NM_014946.3| Homo sapiens spastin (SPAST), transcript variant 1, mRNA
 * GGCCCGAGCCACCGACTGCAGGAGGAGAAGGGGTTGTGCTCCTGGCCGAGGAAGGAGAAAGGGGCGGGGC
 * CGGC[...]
 * </pre>
 * 
 * </p>
 * 
 * 
 * <p>
 * last reviewed: 2013-04-29
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-04
 * @see FASTAFile
 * 
 */
public interface FASTAElement {

	/**
	 * 
	 * Retrieves this {@code FASTAElement}'s header string. Meaning string
	 * starting right after {@link FASTAFile#HEADER_IDENT HEADER_IDENT} and
	 * continuing until first subsequent {@link UtilIO#NEW_LINE_STRING
	 * NEW_LINE_STRING}
	 * 
	 * @return this {@code FASTAElement}'s header string
	 */
	String getHeader();

	/**
	 * 
	 * Retrieves this {@code FASTAElement}'s default line length.
	 * 
	 * @return this {@code FASTAElement}'s default line length
	 */
	int getLineLength();

	/**
	 * Gets this {@code FASTAElement}s sequence.
	 * 
	 * @return {@code String} view of this {@code FASTAElement}s sequence
	 */
	String getSequence();

	/**
	 * 
	 * Retrieves this {@code FASTAElement}'s sequence length.
	 * 
	 * @return this {@code FASTAElement}'s sequence length
	 */
	int getSequenceLength();

	/**
	 * 
	 * Sets this {@code FASTAElement}'s default line length. When invoking
	 * toString() or {@link #toString(boolean)} after {@code length} characters
	 * written to one line, a {@link net.sf.kerner.utils.io.UtilIO#NEW_LINE_STRING NEW_LINE_STRING} will be appended.
	 * 
	 * @param length
	 *            the new default line length
	 */
	void setLineLength(int length);
}
