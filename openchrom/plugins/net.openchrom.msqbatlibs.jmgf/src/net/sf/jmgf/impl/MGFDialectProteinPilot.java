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
package net.sf.jmgf.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class MGFDialectProteinPilot {

	private final static Logger logger = Logger.getLogger(MGFDialectProteinPilot.class);
	private String title;
	public final static String REGEX_SPECTRUM = ".*Locus:([^\\s]+)[\\s].*";

	public MGFDialectProteinPilot() {
	}

	public MGFDialectProteinPilot(final String title) {
		this.title = title;
	}

	public String getSpectrum() {

		final Pattern p;
		p = Pattern.compile(REGEX_SPECTRUM);
		final Matcher m = p.matcher(getTitle());
		final boolean b = m.matches();
		if(b) {
			return m.group(1).trim();
		} else {
			logger.info("no spectrum for " + title);
			return null;
		}
	}

	public synchronized String getTitle() {

		return title;
	}

	public synchronized void setTitle(final String title) {

		this.title = title;
	}
}
