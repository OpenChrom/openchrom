/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

import java.io.Closeable;

import org.apache.log4j.Logger;

/**
 *
 * A {@code CloserPropery} is used to close resources properly and without the
 * need for exception handling.
 *
 * <p>
 * <b>Example:</b><br>
 * </p>
 *
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 * <p>
 * <b>Threading:</b> Fully thread save.
 * </p>
 *
 * <p>
 * last reviewed: 2015-12-14
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class CloserProperly {

	private final static Logger logger = Logger.getLogger(CloserProperly.class);

	/**
	 * Closes {@code closable} if {@code closable != null} and instanceof {@link Closable}. Exceptions are catched and logged (level WARN).
	 *
	 * @param closable
	 *            object to close
	 */
	public void closeProperly(final Object closable) {

		if(closable != null && closable instanceof Closeable) {
			Closeable c = (Closeable)closable;
			try {
				c.close();
			} catch(final Exception e) {
				logger.warn(e.getLocalizedMessage(), e);
			}
		}
	}
}
