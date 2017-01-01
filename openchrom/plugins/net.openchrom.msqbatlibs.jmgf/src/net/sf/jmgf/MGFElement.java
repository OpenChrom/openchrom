/*******************************************************************************
 * Copyright (c) 2016, 2017 Lablicate GmbH.
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
import java.util.Map;

import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2015-12-09
 *
 */
public interface MGFElement {

	public static enum Identifier {
		CHARGE, PEPMASS, RTINSECONDS, SCANS, TITLE, COMMENT;

		private final String identifier;

		private Identifier() {
			this(null);
		}

		private Identifier(final String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String toString() {

			if(identifier != null) {
				return identifier;
			}
			return super.toString();
		}
	}

	public final static String TAG_NA = "n/a";

	short getMSLevel();

	List<Peak> getPeaks();

	String getTag(String element);

	Map<String, String> getTags();

	String getTag(Identifier element);

	String getTitle();
}
