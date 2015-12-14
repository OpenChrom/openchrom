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
package net.sf.jmgf;

import java.util.List;

import net.sf.bioutils.proteomics.peak.Peak;

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
 * last reviewed: 2015-12-09
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2015-12-09
 *
 */
public interface MGFElement {

	public static enum Identifier {
		TITLE, CHARGE, PEPMASS, RTINSECONDS;

		private final String identifier;

		private Identifier() {

			this(null);
		}

		private Identifier(final String identifier) {

			this.identifier = identifier;
		}

		
		public String toString() {

			if(identifier != null) {
				return identifier;
			}
			return super.toString();
		}
	}

	public final static String ELEMENT_NA = "n/a";

	String getElement(Identifier element);

	String getElement(String element);

	List<Peak> getPeaks();

	String getTitle();
}
