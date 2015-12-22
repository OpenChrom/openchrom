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
package net.sf.kerner.utils.progress;

public interface ProgressMonitor {

	public final static int UNKNOWN_WORKLOAD = -1;

	void finished();

	boolean isCancelled();

	void notifySubtask(String name);

	void setCancelled(boolean cancelled);

	void setTaskName(String name);

	void started(int totalWorkload);

	/**
	 * Same as {@link #worked(1)}.
	 */
	void worked();

	void worked(int i);
}
