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
package net.sf.kerner.utils.time;

/**
 * A stop watch to time a process.
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
 * @version 2011-08-02
 */
public class StopWatch {

	/**
	 * An Exception to indicate that a {@code StopWatch} is already running and
	 * cannot be started.
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
	 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander
	 *         Kerner</a>
	 * @version 2011-08-02
	 */
	public class AlreadyRunningException extends RuntimeException {

		private static final long serialVersionUID = -8543832250156761882L;

		public AlreadyRunningException() {
		}

		public AlreadyRunningException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public AlreadyRunningException(String arg0) {
			super(arg0);
		}

		public AlreadyRunningException(Throwable arg0) {
			super(arg0);
		}
	}

	/**
	 * An Exception to indicate that a {@code StopWatch} is not running and
	 * cannot be stoped.
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
	 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander
	 *         Kerner</a>
	 * @version 2011-08-02
	 */
	public class NotRunningException extends RuntimeException {

		private static final long serialVersionUID = 2016104424322122373L;

		public NotRunningException() {
		}

		public NotRunningException(String message, Throwable cause) {
			super(message, cause);
		}

		public NotRunningException(String message) {
			super(message);
		}

		public NotRunningException(Throwable cause) {
			super(cause);
		}
	}

	/**
	 * 
	 */
	private long time = -1;

	/**
	 * Start this {@code StopWatch}.
	 * 
	 * @throws AlreadyRunningException
	 *             if this this {@code StopWatch} is already running
	 */
	public synchronized void start() throws AlreadyRunningException {

		if(time > 0)
			throw new AlreadyRunningException();
		time = System.currentTimeMillis();
	}

	/**
	 * Retrieve time that has passed since starting this {@code StopWatch}.
	 * 
	 * @return time time that has passed since starting this {@code StopWatch}
	 * @throws NotRunningException
	 *             if this {@code StopWatch} is not running
	 */
	public synchronized TimePeriod round() throws NotRunningException {

		if(time < 0)
			throw new NotRunningException();
		return new TimePeriod(time, System.currentTimeMillis());
	}

	public synchronized TimePeriod current() throws NotRunningException {

		final long result = time;
		if(result < 0)
			throw new NotRunningException();
		return new TimePeriod(result, System.currentTimeMillis());
	}

	/**
	 * Stop this {@code StopWatch}.
	 * 
	 * @return time that has passed since starting this {@code StopWatch}
	 * @throws NotRunningException
	 *             if this {@code StopWatch} is not running
	 */
	public synchronized TimePeriod stop() throws NotRunningException {

		final TimePeriod result = current();
		// reset
		time = -1;
		return result;
	}
}
