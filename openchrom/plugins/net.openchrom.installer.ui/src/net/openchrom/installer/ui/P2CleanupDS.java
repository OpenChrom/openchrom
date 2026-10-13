/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Aleksandar Kurtakov - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.propertytypes.EventTopics;

@Component(service = EventHandler.class)
@EventTopics(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE)
public class P2CleanupDS implements EventHandler {

	private static final long SHUTDOWN_TIMEOUT = 5000;
	private volatile Job job;

	@Override
	public void handleEvent(Event event) {

		Job cleanupJob = Job.create("Clean old p2 repositories and profiles", (IProgressMonitor monitor) -> {

			P2Cleanup.clean(monitor);
			return Status.OK_STATUS;
		});
		cleanupJob.setSystem(true);
		cleanupJob.schedule();
		job = cleanupJob;
	}

	@Deactivate
	void deactivate() {

		Job cleanupJob = job;
		if(cleanupJob == null) {
			return;
		}
		cleanupJob.cancel();
		try {
			cleanupJob.join(SHUTDOWN_TIMEOUT, null);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch(OperationCanceledException e) {
			// the join itself was canceled
		}
	}
}
