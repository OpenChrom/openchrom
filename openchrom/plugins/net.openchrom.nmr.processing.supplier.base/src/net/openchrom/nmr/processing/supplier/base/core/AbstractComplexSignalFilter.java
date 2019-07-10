/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.FilterChain;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

public abstract class AbstractComplexSignalFilter<ConfigType, SubType extends IComplexSignalMeasurement<?>> implements IMeasurementFilter<ConfigType>, Serializable {

	private static final long serialVersionUID = 6491722860634659049L;
	private Class<ConfigType> configClass;

	public AbstractComplexSignalFilter(Class<ConfigType> configClass) {
		this.configClass = configClass;
	}

	@Override
	public ConfigType createNewConfiguration() {

		try {
			return configClass.newInstance();
		} catch(InstantiationException | IllegalAccessException e) {
			throw new AssertionError("can't instantiate config " + configClass);
		}
	}

	@Override
	public ConfigType createConfiguration(Collection<? extends IMeasurement> items) {

		for(IMeasurement item : items) {
			ConfigType config = Adapters.adapt(item, configClass);
			if(config != null) {
				return config;
			}
		}
		return IMeasurementFilter.super.createConfiguration(items);
	}

	@Override
	public Collection<? extends IMeasurement> filterIMeasurements(Collection<? extends IMeasurement> filterItems, ConfigType configuration, FilterChain<Collection<? extends IMeasurement>> nextFilter, MessageConsumer messageConsumer, IProgressMonitor monitor) throws IllegalArgumentException {

		SubMonitor subMonitor = SubMonitor.convert(monitor, getName(), 100 * filterItems.size());
		// collect filtered items here...
		List<IMeasurement> filtered = new ArrayList<>();
		for(IMeasurement measurement : filterItems) {
			if(acceptsIMeasurement(measurement)) {
				@SuppressWarnings("unchecked")
				SubType nmrMeasurement = (SubType)measurement;
				ConfigType settings;
				if(isValidConfig(configuration)) {
					// user settings as is...
					settings = configuration;
				} else {
					// try to determine better settings...
					settings = createConfiguration(measurement);
				}
				FilteredMeasurement<?> filteredMeasurement = doFiltering(nmrMeasurement, settings, messageConsumer, subMonitor.split(100));
				if(filteredMeasurement != null) {
					filteredMeasurement.setDataName(getName());
					filteredMeasurement.setFilter(this);
					filtered.add(filteredMeasurement);
				} else {
					// nothing filtered, add the original one
					filtered.add(nmrMeasurement);
				}
			} else {
				throw new IllegalArgumentException("invalid IMeasurement of type " + measurement.getClass().getName());
			}
		}
		// and pass them to the next filter
		return nextFilter.doFilter(filtered, messageConsumer);
	}

	protected abstract FilteredMeasurement<?> doFiltering(SubType measurement, ConfigType settings, MessageConsumer messageConsumer, IProgressMonitor monitor);

	protected boolean isValidConfig(ConfigType config) {

		return config != null;
	}
}
