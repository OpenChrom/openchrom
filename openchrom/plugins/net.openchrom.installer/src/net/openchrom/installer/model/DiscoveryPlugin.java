/*******************************************************************************
 * Copyright (c) 2009, 2025 Tasktop Technologies, Polarion Software and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author David Green
 * @author Igor Burilo
 */
public class DiscoveryPlugin extends PluginDescriptor {

	private IDiscoverySource source;
	private DiscoveryCategory category;
	private boolean selected;
	private final PropertyChangeSupport changeSupport;

	public DiscoveryPlugin() {

		changeSupport = new PropertyChangeSupport(this);
	}

	public DiscoveryCategory getCategory() {

		return category;
	}

	public void setCategory(DiscoveryCategory category) {

		this.category = category;
	}

	public IDiscoverySource getSource() {

		return source;
	}

	public void setSource(IDiscoverySource source) {

		this.source = source;
	}

	/**
	 * support selection
	 * 
	 * @return true if the item is selected, otherwise false
	 */
	public boolean isSelected() {

		return selected;
	}

	/**
	 * support selection
	 * 
	 * @param selected
	 *            true if the item is selected, otherwise false
	 */
	public void setSelected(boolean selected) {

		this.selected = selected;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {

		changeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {

		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {

		changeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {

		changeSupport.removePropertyChangeListener(propertyName, listener);
	}
}
