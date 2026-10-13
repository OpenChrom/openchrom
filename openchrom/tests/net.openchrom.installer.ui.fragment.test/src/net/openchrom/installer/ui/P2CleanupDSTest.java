/*******************************************************************************
 * Copyright (c) 2026 Aleksandar Kurtakov and others.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.equinox.internal.p2.engine.Profile;
import org.eclipse.equinox.internal.p2.engine.SimpleProfileRegistry;
import org.eclipse.equinox.internal.p2.garbagecollector.GarbageCollector;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
class P2CleanupDSTest {

	private static final String JOB_NAME = "Clean old p2 repositories and profiles";
	private static final long TIMEOUT = 10;

	private final AtomicReference<Job> cleanupJob = new AtomicReference<>();
	private final AtomicReference<IProgressMonitor> cleanupMonitor = new AtomicReference<>();
	private final CountDownLatch removeProfileEntered = new CountDownLatch(1);
	private final CountDownLatch garbageCollectorEntered = new CountDownLatch(1);
	private final CountDownLatch garbageCollectorRelease = new CountDownLatch(1);
	private volatile boolean blockRemoveProfile;
	private volatile boolean blockGarbageCollector;
	private ServiceRegistration<IProvisioningAgent> registration;

	@BeforeEach
	void setUp() {

		Job.getJobManager().setProgressProvider(new ProgressProvider() {

			@Override
			public IProgressMonitor createMonitor(Job job) {

				IProgressMonitor monitor = new NullProgressMonitor();
				if(JOB_NAME.equals(job.getName())) {
					cleanupJob.set(job);
					cleanupMonitor.set(monitor);
				}
				return monitor;
			}
		});
		BundleContext context = FrameworkUtil.getBundle(P2Cleanup.class).getBundleContext();
		registration = context.registerService(IProvisioningAgent.class, new FakeAgent(), FrameworkUtil.asDictionary(Map.of(Constants.SERVICE_RANKING, Integer.MAX_VALUE)));
	}

	@AfterEach
	void tearDown() throws InterruptedException {

		garbageCollectorRelease.countDown();
		Job job = cleanupJob.get();
		if(job != null) {
			job.join(TimeUnit.SECONDS.toMillis(TIMEOUT), null);
		}
		registration.unregister();
		Job.getJobManager().setProgressProvider(null);
	}

	@Test
	void testDeactivateCancelsBeforeGarbageCollection() throws InterruptedException {

		blockRemoveProfile = true;
		P2CleanupDS p2CleanupDS = startCleanup();
		assertTrue(removeProfileEntered.await(TIMEOUT, TimeUnit.SECONDS), "Cleanup job did not remove profiles");
		p2CleanupDS.deactivate();
		Job job = cleanupJob.get();
		assertNotNull(job);
		assertEquals(Job.NONE, job.getState(), "Cleanup job still running after deactivate");
		assertEquals(IStatus.CANCEL, job.getResult().getSeverity());
		assertEquals(1, garbageCollectorEntered.getCount(), "Garbage collector ran after cancel");
	}

	@Test
	void testDeactivateDoesNotWaitForSlowGarbageCollection() throws InterruptedException {

		blockGarbageCollector = true;
		P2CleanupDS p2CleanupDS = startCleanup();
		assertTrue(garbageCollectorEntered.await(TIMEOUT, TimeUnit.SECONDS), "Garbage collector did not run");
		long start = System.nanoTime();
		p2CleanupDS.deactivate();
		long elapsed = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start);
		assertTrue(elapsed < TIMEOUT, "Deactivate waited " + elapsed + "s for the garbage collector");
		Job job = cleanupJob.get();
		assertNotNull(job);
		// The garbage collector can not be canceled, so the job outlives deactivate.
		assertEquals(Job.RUNNING, job.getState());
		assertTrue(cleanupMonitor.get().isCanceled());
		garbageCollectorRelease.countDown();
		assertTrue(job.join(TimeUnit.SECONDS.toMillis(TIMEOUT), null));
		assertFalse(job.getResult().matches(IStatus.ERROR | IStatus.CANCEL));
	}

	private P2CleanupDS startCleanup() {

		P2CleanupDS p2CleanupDS = new P2CleanupDS();
		p2CleanupDS.handleEvent(new Event(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, Map.of()));
		return p2CleanupDS;
	}

	private void waitForCancel() {

		long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(TIMEOUT);
		while(!cleanupMonitor.get().isCanceled() && System.nanoTime() < deadline) {
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}

	/*
	 * Provides a profile with one old state and a garbage collector.
	 */
	private class FakeAgent implements IProvisioningAgent {

		private final IProfile profile = new Profile(this, "test", null, null);
		private final IProfileRegistry profileRegistry = new SimpleProfileRegistry(this, new File("unused")) {

			@Override
			public IProfile getProfile(String id) {

				return profile;
			}

			@Override
			public long[] listProfileTimestamps(String id) {

				return new long[]{1, 2};
			}

			@Override
			public void removeProfile(String id, long timestamp) {

				removeProfileEntered.countDown();
				if(blockRemoveProfile) {
					waitForCancel();
				}
			}
		};
		private final GarbageCollector garbageCollector = new GarbageCollector(this) {

			@Override
			public void runGC(IProfile gcProfile) {

				garbageCollectorEntered.countDown();
				if(blockGarbageCollector) {
					try {
						garbageCollectorRelease.await(TIMEOUT, TimeUnit.SECONDS);
					} catch(InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};

		@Override
		public Object getService(String serviceName) {

			if(IProfileRegistry.SERVICE_NAME.equals(serviceName)) {
				return profileRegistry;
			}
			if(GarbageCollector.SERVICE_NAME.equals(serviceName)) {
				return garbageCollector;
			}
			return null;
		}

		@Override
		public void registerService(String serviceName, Object service) {

		}

		@Override
		public void stop() {

		}

		@Override
		public void unregisterService(String serviceName, Object service) {

		}
	}
}
