/*******************************************************************************
 * Copyright (c) 2015, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.sf.kerner.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for {@link String} related stuff.
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
 * @version 2013-02-16
 */
public class UtilString {

	/**
	 * System dependent new line string.
	 */
	public final static String NEW_LINE_STRING = System.getProperty("line.separator");

	public static boolean allEmpty(final Collection<? extends String> strings) {

		for(final String value : strings) {
			if(!emptyString(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if a string is {@code null}, empty or contains only whitespaces.
	 *
	 * @param string
	 *            {@code String} to check
	 * @return true, if this {@code String} is {@code null}, empty or contains
	 *         only whitespaces; false otherwise
	 */
	public static boolean emptyString(final String string) {

		if(string == null)
			return true;
		if(string.length() < 1)
			return true;
		if(string.matches("\\s+"))
			return true;
		return false;
	}

	/**
	 * @return a random {@code String}
	 */
	public static String getRandomString() {

		final String result = Long.toHexString(Double.doubleToLongBits(Math.random()));
		return result;
	}

	public static String replaceAllNewLine(final String string, final String replacement) {

		return string.replaceAll("\\r\\n|\\r|\\n", replacement);
	}

	public static List<Integer> toLength(final Collection<String> strings) {

		final List<Integer> lengths = new ArrayList<Integer>();
		for(final String s : strings) {
			lengths.add(s.length());
		}
		return lengths;
	}

	public static String trimAndReduceWhiteSpace(final String string) {

		return string.trim().replaceAll("\\s+", " ");
	}

	private UtilString() {

	}
}
