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
package net.openchrom.installer.model;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class DSDiscoveryStrategy extends AbstractDiscoveryStrategy {

	private static final String DYNAMIC_GROUP_ID = "net.openchrom.dynamic";

	@Override
	public void performDiscovery(IProgressMonitor monitor) throws CoreException {

		BundleContext ctx = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference<PluginsDS> ref = ctx.getServiceReference(PluginsDS.class);
		if(ref != null) {
			DiscoveryCategory category = new DiscoveryCategory();
			category.setDescription("Converters and extensions from private update sites.");
			category.setName("Private Extensions");
			category.setId(DYNAMIC_GROUP_ID);
			categories.add(category);
			PluginsDS pluginsDS = ctx.getService(ref);
			for(IPluginDescriptor descriptor : pluginsDS.getDescriptors()) {
				descriptor.setCategoryId(DYNAMIC_GROUP_ID);
				descriptor.setCategory(category);
				descriptor.setKind(List.of(PluginDescriptorKind.DYNAMIC));
				plugins.add(descriptor);

			}
		}
	}

}
