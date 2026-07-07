/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui;

import java.net.URI;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.equinox.internal.p2.garbagecollector.GarbageCollector;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.repository.IRepository;
import org.eclipse.equinox.p2.repository.IRepositoryManager;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.ui.IStartup;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;
import org.osgi.util.tracker.ServiceTracker;

public class P2Cleanup implements IStartup {

	private static final Logger logger = Logger.getLogger(P2Cleanup.class);

	@Override
	public void earlyStartup() {

		BundleContext context = FrameworkUtil.getBundle(P2Cleanup.class).getBundleContext();
		ServiceTracker<IProvisioningAgent, IProvisioningAgent> tracker = new ServiceTracker<>(context, IProvisioningAgent.class, null);
		tracker.open();
		IProvisioningAgent agent = tracker.getService();
		cleanRepositories(agent, context.getBundle().getVersion());
		cleanProfileStates(agent);
		tracker.close();
	}

	/*
	 * Delete old snapshots of the running profile so bundles referenced in it are removed from disk.
	 */
	private static void cleanProfileStates(IProvisioningAgent agent) {

		if(agent == null) {
			return;
		}
		IProfileRegistry profileRegistry = agent.getService(IProfileRegistry.class);
		if(profileRegistry == null) {
			return;
		}
		IProfile profile = profileRegistry.getProfile(IProfileRegistry.SELF);
		if(profile == null) {
			return;
		}
		String profileId = profile.getProfileId();
		long[] timestamps = profileRegistry.listProfileTimestamps(profileId);
		if(timestamps.length <= 1) {
			return;
		}
		boolean removed = false;
		// The last timestamp belongs to the currently running profile and must be kept.
		for(int i = 0; i < timestamps.length - 1; i++) {
			try {
				profileRegistry.removeProfile(profileId, timestamps[i]);
				removed = true;
			} catch(ProvisionException e) {
				logger.warn(e);
			}
		}
		if(removed) {
			runGarbageCollector(agent, profile);
		}
	}

	@SuppressWarnings("restriction")
	private static void runGarbageCollector(IProvisioningAgent agent, IProfile profile) {

		GarbageCollector garbageCollector = agent.getService(GarbageCollector.class);
		if(garbageCollector != null) {
			garbageCollector.runGC(profile);
		}
	}

	private static void cleanRepositories(IProvisioningAgent agent, Version version) {

		var currentVersion = version.getMajor() + "." + version.getMinor() + "." + version.getMicro();
		IMetadataRepositoryManager metadataManager = agent.getService(IMetadataRepositoryManager.class);
		cleanRepositories(metadataManager, currentVersion);
		IArtifactRepositoryManager artifactManager = agent.getService(IArtifactRepositoryManager.class);
		cleanRepositories(artifactManager, currentVersion);
	}

	private static void cleanRepositories(IRepositoryManager<?> repositoryManager, String currentVersion) {

		if(repositoryManager == null) {
			return;
		}

		for(URI uri : repositoryManager.getKnownRepositories(IRepository.NONE)) {
			var repo = uri.toString();
			if(!repo.startsWith("http")) {
				continue;
			}
			if(repo.contains(currentVersion)) {
				continue;
			}
			logger.info("Cleaning old repository " + uri);
			repositoryManager.removeRepository(uri);
		}
	}
}
