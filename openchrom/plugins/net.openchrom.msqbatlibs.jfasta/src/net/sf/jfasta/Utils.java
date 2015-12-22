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
package net.sf.jfasta;

import net.sf.kerner.utils.io.UtilIO;

public class Utils {

	private Utils() {

	}

	public static String formatStringToMultiLinesStrings(String string, int length) {

		string = string.replace(UtilIO.NEW_LINE_STRING, " ");
		StringBuilder sb = new StringBuilder();
		while(string.length() > length) {
			int endIndex = length;
			sb.append(string.subSequence(0, endIndex).toString());
			sb.append(UtilIO.NEW_LINE_STRING);
			string = string.substring(endIndex);
		}
		if(string != null && !string.equals(UtilIO.NEW_LINE_STRING))
			sb.append(string);
		return sb.toString();
	}
}
