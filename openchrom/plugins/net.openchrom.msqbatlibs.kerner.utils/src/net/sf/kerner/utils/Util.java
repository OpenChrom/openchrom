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
package net.sf.kerner.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * General utility class.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 *
 */
public class Util {

	private final static Logger logger = Logger.getLogger(Util.class);
	/**
	 * Number of CPUs that are available to this JVM.
	 */
	public static final int NUM_CPUS = Runtime.getRuntime().availableProcessors();
	/**
	 * {@link Locale} of current user, e.g. "de", "en" or "fr".
	 */
	public static final Locale USER_LOCALE = new Locale(System.getProperty("user.language"));
	/**
	 * The working directory is the location in the file system from where the
	 * java command was invoked.
	 */
	public static final File WORKING_DIR = new File(System.getProperty("user.dir"));

	public static void checkForNull(final Object... objects) throws NullPointerException {

		for(final Object o : objects) {
			if(o == null) {
				throw new NullPointerException();
			}
		}
	}

	public static String getCurrentStackTraceString() {

		return getStackTraceString(new Exception());
	}

	public static String getStackTraceString(final Throwable t) {

		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	public static String getToStringDelimiter() {

		return ":";
	}

	public static String getToStringPostFix(final Class<?> c) {

		return "";
	}

	public static String getToStringPreFix(final Class<?> c) {

		return c.getSimpleName() + getToStringDelimiter();
	}

	/**
	 * Load a property file as a resource stream and return the {@code version} property.
	 *
	 * @param clazz
	 * @param propertiesFile
	 * @return version string or {@code n/a} if property could not be read
	 */
	public static String readVersionFromProperties(final Class<?> clazz, final String propertiesFile) {

		String result = "n/a";
		try {
			final Properties props = new Properties();
			props.load(clazz.getResourceAsStream(propertiesFile));
			result = props.getProperty("version");
		} catch(final Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	private Util() {

	}
}
