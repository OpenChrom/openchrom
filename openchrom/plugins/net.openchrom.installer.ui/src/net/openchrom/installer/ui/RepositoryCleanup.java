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
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.repository.IRepository;
import org.eclipse.equinox.p2.repository.IRepositoryManager;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.ui.IStartup;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;
import org.osgi.util.tracker.ServiceTracker;

public class RepositoryCleanup implements IStartup {

	private static final Logger logger = Logger.getLogger(RepositoryCleanup.class);

	@Override
	public void earlyStartup() {

		BundleContext context = FrameworkUtil.getBundle(RepositoryCleanup.class).getBundleContext();
		ServiceTracker<IProvisioningAgent, IProvisioningAgent> tracker = new ServiceTracker<>(context, IProvisioningAgent.class, null);
		tracker.open();
		cleanRepositories(tracker.getService(), context.getBundle().getVersion());
		tracker.close();
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
