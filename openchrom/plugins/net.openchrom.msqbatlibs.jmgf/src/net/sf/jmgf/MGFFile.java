/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jmgf;

import java.io.Serializable;
import java.util.List;

public interface MGFFile extends Iterable<MGFElement>, Serializable {

	public static class Format {

		public final static String FIRST_LINE = "BEGIN IONS";
		public final static String KEY_VALUE_SEPARATOR = "=";
		public final static String LAST_LINE = "END IONS";
		public final static String PEAK_PROPERTIES_SEPARATOR = " ";
	}

	List<MGFElement> getElements();
}
