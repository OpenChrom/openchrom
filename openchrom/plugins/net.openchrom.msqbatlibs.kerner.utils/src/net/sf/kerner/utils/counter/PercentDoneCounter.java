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
package net.sf.kerner.utils.counter;

import java.util.ArrayList;
import java.util.List;

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
 * last reviewed: 2013-06-12
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-06-12
 * 
 */
public class PercentDoneCounter extends Counter {

	public static interface Listener {

		void update(double percentDone);
	}

	private final List<Listener> listeners = new ArrayList<Listener>();

	/**
	 * Instantiates a new {@code PercentDoneCounter}. Interval is initially set
	 * to 1%.
	 * 
	 * @param totalElements
	 */
	public PercentDoneCounter(final int totalElements) {
		if(totalElements <= 100) {
			setInterval(1);
		} else {
			setInterval(totalElements / 100);
		}
		addRunnable(new Runnable() {

			public void run() {

				final double percent = (double)getCount() / (double)totalElements * 100;
				for(final Listener l : listeners) {
					l.update(percent);
				}
			}
		});
	}

	public void addListener(final Listener listener) {

		listeners.add(listener);
	}

	public void clearListners() {

		listeners.clear();
	}
}
