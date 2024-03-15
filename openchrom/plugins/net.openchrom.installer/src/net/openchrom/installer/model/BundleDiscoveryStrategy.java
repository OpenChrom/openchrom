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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.osgi.framework.Bundle;

/**
 * a strategy for discovering via installed platform {@link Bundle bundles}.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class BundleDiscoveryStrategy extends AbstractDiscoveryStrategy {

	private static final Logger logger = Logger.getLogger(BundleDiscoveryStrategy.class);

	@Override
	public void performDiscovery(IProgressMonitor monitor) throws CoreException {

		if(plugins == null || categories == null) {
			throw new IllegalStateException();
		}
		IExtensionPoint extensionPoint = getExtensionRegistry().getExtensionPoint(PluginDiscoveryExtensionReader.EXTENSION_POINT_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		monitor.beginTask("Loading local extensions", extensions.length == 0 ? 1 : extensions.length);
		try {
			if(extensions.length > 0) {
				processExtensions(SubMonitor.convert(monitor, extensions.length), extensions);
			}
		} finally {
			monitor.done();
		}
	}

	protected void processExtensions(IProgressMonitor monitor, IExtension[] extensions) {

		monitor.beginTask("Processing extensions", extensions.length == 0 ? 1 : extensions.length);
		try {
			PluginDiscoveryExtensionReader extensionReader = new PluginDiscoveryExtensionReader();
			for(IExtension extension : extensions) {
				IDiscoverySource discoverySource = computeDiscoverySource(extension.getContributor());
				IConfigurationElement[] elements = extension.getConfigurationElements();
				for(IConfigurationElement element : elements) {
					if(monitor.isCanceled()) {
						return;
					}
					if(PluginDiscoveryExtensionReader.PLUGIN_DESCRIPTOR.equals(element.getName())) {
						DiscoveryPlugin descriptor = extensionReader.readConnectorDescriptor(element, DiscoveryPlugin.class);
						descriptor.setSource(discoverySource);
						plugins.add(descriptor);
					} else if(PluginDiscoveryExtensionReader.PLUGIN_CATEGORY.equals(element.getName())) {
						DiscoveryCategory category = extensionReader.readConnectorCategory(element, DiscoveryCategory.class);
						category.setSource(discoverySource);
						categories.add(category);
					} else {
						logger.error("unexpected element " + element.getName());
						monitor.done();
					}
				}
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
	}

	protected IDiscoverySource computeDiscoverySource(IContributor contributor) {

		return new BundleDiscoverySource(Platform.getBundle(contributor.getName()));
	}

	protected IExtensionRegistry getExtensionRegistry() {

		return Platform.getExtensionRegistry();
	}
}
