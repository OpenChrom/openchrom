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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Bundle;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.Version;

/**
 * A means of discovering plugins.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class PluginDiscovery {

	private List<DiscoveryPlugin> plugins = Collections.emptyList();
	private List<DiscoveryCategory> categories = Collections.emptyList();
	private List<DiscoveryPlugin> filteredConnectors = Collections.emptyList();
	private final List<AbstractDiscoveryStrategy> discoveryStrategies = new ArrayList<>();
	private Dictionary<String, Object> environment = new Hashtable<>();
	private Map<String, Version> featureToVersion = null;
	private static final Logger logger = Logger.getLogger(PluginDiscovery.class);

	public PluginDiscovery() {

		Dictionary<Object, Object> props = System.getProperties();
		for(Enumeration<?> iterator = props.keys(); iterator.hasMoreElements();) {
			String key = (String)iterator.nextElement();
			environment.put(key, props.get(key));
		}
	}

	/**
	 * get the discovery strategies to use.
	 */
	public List<AbstractDiscoveryStrategy> getDiscoveryStrategies() {

		return discoveryStrategies;
	}

	/**
	 * Initialize this by performing discovery. Discovery may take a long time as it involves network access.
	 * PRECONDITION: must add at least one {@link #getDiscoveryStrategies() discovery strategy} prior to calling.
	 */
	public void performDiscovery(IProgressMonitor monitor) throws CoreException {

		if(discoveryStrategies.isEmpty()) {
			throw new IllegalStateException();
		}
		plugins = new ArrayList<>();
		filteredConnectors = new ArrayList<>();
		categories = new ArrayList<>();
		final int totalTicks = 100000;
		final int discoveryTicks = totalTicks - (totalTicks / 10);
		monitor.beginTask("Creating list of plug-ins.", totalTicks);
		try {
			for(AbstractDiscoveryStrategy discoveryStrategy : discoveryStrategies) {
				discoveryStrategy.setCategories(categories);
				discoveryStrategy.setConnectors(plugins);
				discoveryStrategy.performDiscovery(SubMonitor.convert(monitor, discoveryTicks / discoveryStrategies.size()));
			}
			filterDescriptors();
			connectCategoriesToDescriptors();
		} finally {
			monitor.done();
		}
	}

	/**
	 * get the top-level categories
	 * 
	 * @return the categories, or an empty list if there are none.
	 */
	public List<DiscoveryCategory> getCategories() {

		return categories;
	}

	/**
	 * get the plugins that were discovered and not filtered
	 * 
	 * @return the plugins, or an empty list if there are none.
	 */
	public List<DiscoveryPlugin> getConnectors() {

		return plugins;
	}

	/**
	 * get the plugins that were discovered but filtered
	 * 
	 * @return the filtered plugins, or an empty list if there were none.
	 */
	public List<DiscoveryPlugin> getFilteredConnectors() {

		return filteredConnectors;
	}

	/**
	 * The environment used to resolve {@link PluginDescriptor#getPlatformFilter() platform filters}. Defaults to the
	 * current environment.
	 */
	public Dictionary<?, ?> getEnvironment() {

		return environment;
	}

	/**
	 * The environment used to resolve {@link PluginDescriptor#getPlatformFilter() platform filters}. Defaults to the
	 * current environment.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setEnvironment(Dictionary environment) {

		if(environment == null) {
			throw new IllegalArgumentException();
		}
		this.environment = environment;
	}

	/**
	 * <em>not for general use: public for testing purposes only</em> A map of installed features to their version. Used
	 * to resolve {@link PluginDescriptor#getFeatureFilter() feature filters}.
	 */
	public Map<String, Version> getFeatureToVersion() {

		return featureToVersion;
	}

	/**
	 * <em>not for general use: public for testing purposes only</em> A map of installed features to their version. Used
	 * to resolve {@link PluginDescriptor#getFeatureFilter() feature filters}.
	 */
	public void setFeatureToVersion(Map<String, Version> featureToVersion) {

		this.featureToVersion = featureToVersion;
	}

	private void connectCategoriesToDescriptors() {

		Map<String, DiscoveryCategory> idToCategory = new HashMap<>();
		for(DiscoveryCategory category : categories) {
			DiscoveryCategory previous = idToCategory.put(category.getId(), category);
			if(previous != null) {
				logger.error("Duplicate");
			}
		}
		for(DiscoveryPlugin plugin : plugins) {
			DiscoveryCategory category = idToCategory.get(plugin.getCategoryId());
			if(category != null) {
				category.getConnectors().add(plugin);
				plugin.setCategory(category);
			} else {
				logger.error("Unknown Category");
			}
		}
	}

	/**
	 * eliminate any plugins whose {@link PluginDescriptor#getPlatformFilter() platform filters} don't match
	 */
	private void filterDescriptors() {

		for(DiscoveryPlugin plugin : new ArrayList<DiscoveryPlugin>(plugins)) {
			if(plugin.getPlatformFilter() != null && plugin.getPlatformFilter().trim().length() > 0) {
				boolean match = false;
				try {
					Filter filter = FrameworkUtil.createFilter(plugin.getPlatformFilter());
					match = filter.match(environment);
				} catch(InvalidSyntaxException e) {
					logger.error(e);
				}
				if(!match) {
					plugins.remove(plugin);
					filteredConnectors.add(plugin);
				}
			}
			for(FeatureFilter featureFilter : plugin.getFeatureFilter()) {
				if(featureToVersion == null) {
					featureToVersion = computeFeatureToVersion();
				}
				boolean match = false;
				Version version = featureToVersion.get(featureFilter.getFeatureId());
				if(version != null) {
					VersionRange versionRange = new VersionRange(featureFilter.getVersion());
					if(versionRange.isIncluded(version)) {
						match = true;
					}
				}
				if(!match) {
					plugins.remove(plugin);
					filteredConnectors.add(plugin);
					break;
				}
			}
		}
	}

	private Map<String, Version> computeFeatureToVersion() {

		Map<String, Version> featureToVersion = new HashMap<>();
		for(IBundleGroupProvider provider : Platform.getBundleGroupProviders()) {
			for(IBundleGroup bundleGroup : provider.getBundleGroups()) {
				for(Bundle bundle : bundleGroup.getBundles()) {
					featureToVersion.put(bundle.getSymbolicName(), bundle.getVersion());
				}
			}
		}
		return featureToVersion;
	}

	public void dispose() {

		for(final AbstractDiscoveryStrategy strategy : discoveryStrategies) {
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {

					strategy.dispose();
				}

				@Override
				public void handleException(Throwable exception) {

					logger.warn(exception);
				}
			});
		}
	}
}
