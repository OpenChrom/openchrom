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

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(service = PluginsDS.class)
public class PluginsDS {

	private final List<IPluginDescriptor> descriptors = new ArrayList<>();

	@Reference(service = IPluginDescriptor.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	void bindDescriptor(IPluginDescriptor descriptor) {

		descriptors.add(descriptor);
	}

	void unbindDescriptor(IPluginDescriptor descriptor) {

		descriptors.remove(descriptor);
	}

	public List<IPluginDescriptor> getDescriptors() {

		return List.copyOf(descriptors);
	}
}
