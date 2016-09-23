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

public class ProgressMonitorAbstract implements ProgressMonitor {

	private boolean cancelled;
	private String taskName;
	protected int totalWorkload = UNKNOWN_WORKLOAD;
	protected int worked;

	@Override
	public void worked() {

		worked(1);
	}

	@Override
	public void finished() {

		totalWorkload = UNKNOWN_WORKLOAD;
	}

	@Override
	public void notifySubtask(String name) {

	}

	@Override
	public void worked(int i) {

		worked += i;
	}

	@Override
	public void started(int totalWorkload) {

		this.totalWorkload = totalWorkload;
	}

	public String getTaskName() {

		return taskName;
	}

	@Override
	public void setTaskName(String taskName) {

		this.taskName = taskName;
	}

	public ProgressMonitorAbstract() {
	}

	@Override
	public synchronized boolean isCancelled() {

		return cancelled;
	}

	@Override
	public synchronized void setCancelled(final boolean cancelled) {

		this.cancelled = cancelled;
	}

	@Override
	public synchronized void started(String taskName, int totalWorkload) {

		setTaskName(taskName);
		started(totalWorkload);
	}
}
