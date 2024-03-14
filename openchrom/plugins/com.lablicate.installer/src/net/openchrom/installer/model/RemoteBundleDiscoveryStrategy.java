/*******************************************************************************
 * Copyright (c) 2009, 2024 Tasktop Technologies, Polarion Software and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.internal.registry.ExtensionRegistry;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import net.openchrom.installer.util.WebUtil;

/**
 * A discovery strategy that downloads a simple directory of remote jars. The directory is first downloaded, then each
 * remote jar is downloaded.
 * 
 * @author David Green
 * @author Igor Burilo
 */
@SuppressWarnings("restriction")
public class RemoteBundleDiscoveryStrategy extends BundleDiscoveryStrategy {

	private String discoveryUrl;
	private DiscoveryRegistryStrategy registryStrategy;
	private File temporaryStorage;
	private int maxDiscoveryJarDownloadAttempts = 1;
	private static final Logger logger = Logger.getLogger(RemoteBundleDiscoveryStrategy.class);

	@Override
	public void performDiscovery(IProgressMonitor monitor) throws CoreException {

		if(plugins == null || categories == null || discoveryUrl == null) {
			throw new IllegalStateException();
		}
		if(registryStrategy != null) {
			throw new IllegalStateException();
		}
		final int totalTicks = 100000;
		final int ticksTenPercent = totalTicks / 10;
		monitor.beginTask("task_remote_discovery", totalTicks);
		try {
			File registryCacheFolder = null;
			try {
				if(temporaryStorage != null && temporaryStorage.exists()) {
					delete(temporaryStorage);
				}
				temporaryStorage = new File(Files.createTempDirectory("bundleDiscovery") + File.separator + "rbds.tmp");
				if(!temporaryStorage.mkdirs()) {
					throw new IOException();
				}
				registryCacheFolder = new File(temporaryStorage, ".rcache"); //$NON-NLS-1$
				if(!registryCacheFolder.mkdirs()) {
					throw new IOException();
				}
			} catch(IOException e) {
				logger.warn(e);
			}
			if(monitor.isCanceled()) {
				return;
			}
			// TODO correctly call DownloadBundleJob, through our framework operations
			DownloadBundleJob downloadBundleJob = new DownloadBundleJob(this.discoveryUrl, monitor);
			downloadBundleJob.exec();
			File bundleFile = downloadBundleJob.getFile();
			if(bundleFile != null) // is there any network issues or the job was cancelled?
			{
				try {
					registryStrategy = new DiscoveryRegistryStrategy(new File[]{registryCacheFolder}, new boolean[]{false}, this);
					registryStrategy.setDiscoveryInfo(bundleFile, this.discoveryUrl);
					IExtensionRegistry extensionRegistry = new ExtensionRegistry(registryStrategy, this, this);
					try {
						IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(PluginDiscoveryExtensionReader.EXTENSION_POINT_ID);
						if(extensionPoint != null) {
							IExtension[] extensions = extensionPoint.getExtensions();
							if(extensions.length > 0) {
								processExtensions(SubMonitor.convert(monitor, ticksTenPercent * 3), extensions);
							}
						}
					} finally {
						extensionRegistry.stop(this);
					}
				} finally {
					registryStrategy = null;
				}
			}
		} finally {
			monitor.done();
		}
	}

	private class DownloadBundleJob {

		private final IProgressMonitor monitor;
		private final String location;
		private File file;

		public DownloadBundleJob(String location, IProgressMonitor monitor) {

			this.location = location;
			this.monitor = monitor;
		}

		public void exec() {

			String bundleUrl = this.location;
			for(int attemptCount = 0; attemptCount < maxDiscoveryJarDownloadAttempts; ++attemptCount) {
				try {
					if(!bundleUrl.startsWith("http://") && !bundleUrl.startsWith("https://")) { //$NON-NLS-1$//$NON-NLS-2$
						logger.warn("Unrecognized discovery url at " + bundleUrl);
						continue;
					}
					String lastPathElement = bundleUrl.lastIndexOf('/') == -1 ? bundleUrl : bundleUrl.substring(bundleUrl.lastIndexOf('/'));
					File target = File.createTempFile(lastPathElement.replaceAll("^[a-zA-Z0-9_.]", "_") + "_", ".jar", temporaryStorage); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
					if(monitor.isCanceled()) {
						break;
					}
					WebUtil.downloadResource(target, new URL(bundleUrl), new NullProgressMonitor() {

						@Override
						public boolean isCanceled() {

							return super.isCanceled() || monitor.isCanceled();
						}
					}/* don't use sub progress monitor here */);
					file = target;
				} catch(IOException e) {
					logger.warn("Cannot download bundle at " + bundleUrl);
					logger.error(e);
					if(isUnknownHostException(e)) {
						break;
					}
				}
			}
		}

		public File getFile() {

			return this.file;
		}
	}

	/**
	 * walk the exception chain to determine if the given exception or any of its underlying causes are an
	 * {@link UnknownHostException}.
	 * 
	 * @return true if the exception or one of its causes are {@link UnknownHostException}.
	 */
	private boolean isUnknownHostException(Throwable t) {

		while(t != null) {
			if(t instanceof UnknownHostException) {
				return true;
			}
			Throwable t2 = t.getCause();
			if(t2 == t) {
				break;
			}
			t = t2;
		}
		return false;
	}

	private void delete(File file) {

		if(file.exists()) {
			if(file.isDirectory()) {
				File[] children = file.listFiles();
				if(children != null) {
					for(File child : children) {
						delete(child);
					}
				}
			}
			if(!file.delete()) {
				// fail quietly
			}
		}
	}

	@Override
	public void dispose() {

		super.dispose();
		if(temporaryStorage != null) {
			delete(temporaryStorage);
		}
	}

	public void setDiscoveryUrl(String discoveryUrl) {

		this.discoveryUrl = discoveryUrl;
	}

	@Override
	protected IDiscoverySource computeDiscoverySource(IContributor contributor) {

		return new JarDiscoverySource(contributor.getName(), registryStrategy.getJarFile(contributor));
	}

	/**
	 * indicate how many times discover jar downloads should be attempted
	 */
	public int getMaxDiscoveryJarDownloadAttempts() {

		return maxDiscoveryJarDownloadAttempts;
	}

	/**
	 * indicate how many times discover jar downloads should be attempted
	 * 
	 * @param maxDiscoveryJarDownloadAttempts
	 *            a number >= 1
	 */
	public void setMaxDiscoveryJarDownloadAttempts(int maxDiscoveryJarDownloadAttempts) {

		if(maxDiscoveryJarDownloadAttempts < 1 || maxDiscoveryJarDownloadAttempts > 2) {
			throw new IllegalArgumentException();
		}
		this.maxDiscoveryJarDownloadAttempts = maxDiscoveryJarDownloadAttempts;
	}
}
