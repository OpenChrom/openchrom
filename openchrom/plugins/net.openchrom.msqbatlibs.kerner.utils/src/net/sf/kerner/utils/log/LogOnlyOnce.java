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
package net.sf.kerner.utils.log;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class LogOnlyOnce {

	private final Set<String> logged = new HashSet<String>();
	private final Logger logger;

	public LogOnlyOnce(final Logger log) {

		this.logger = log;
	}

	public synchronized void debug(final String msg) {

		if(logged.contains(msg)) {
			return;
		}
		logger.debug(msg);
		logged.add(msg);
	}

	public synchronized void debug(final String msg, final Exception exception) {

		if(logged.contains(msg)) {
			return;
		}
		logger.debug(msg, exception);
		logged.add(msg);
	}

	public synchronized void info(final String msg) {

		if(logged.contains(msg)) {
			return;
		}
		logger.info(msg);
		logged.add(msg);
	}

	public synchronized void reset() {

		logged.clear();
	}

	public synchronized void warn(final String msg) {

		if(logged.contains(msg)) {
			return;
		}
		logger.warn(msg);
		logged.add(msg);
	}
}
