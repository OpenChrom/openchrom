/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.processing.core.IMessageConsumer;
import org.eclipse.chemclipse.processing.filter.FilterContext;
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
	public Class<ConfigType> getConfigClass() {

		return configClass;
	}

	public ConfigType createConfiguration(IMeasurement item) throws IllegalArgumentException {

		ConfigType config = Adapters.adapt(item, configClass);
		if(config != null) {
			return config;
		}
		return createNewConfiguration();
	}

	@Override
	public boolean acceptsIMeasurements(Collection<? extends IMeasurement> items) {

		for(IMeasurement measurement : items) {
			if(!acceptsIMeasurement(measurement)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ConfigType createConfiguration(Collection<? extends IMeasurement> items) throws IllegalArgumentException {

		for(IMeasurement measurement : items) {
			return createConfiguration(measurement);
		}
		return IMeasurementFilter.super.createConfiguration(items);
	}

	@Override
	public <ResultType> ResultType filterIMeasurements(Collection<? extends IMeasurement> filterItems, ConfigType configuration, Function<? super Collection<? extends IMeasurement>, ResultType> chain, IMessageConsumer messageConsumer, IProgressMonitor monitor) throws IllegalArgumentException {

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
				IMeasurement filteredMeasurement = doFiltering(FilterContext.create(nmrMeasurement, this, settings), messageConsumer, subMonitor.split(100));
				if(filteredMeasurement != null) {
					if(filteredMeasurement instanceof FilteredMeasurement<?, ?>) {
						filteredMeasurement.setDataName(getName());
					}
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
		return chain.apply(filtered);
	}

	protected abstract boolean acceptsIMeasurement(IMeasurement measurement);

	protected abstract IMeasurement doFiltering(FilterContext<SubType, ConfigType> context, IMessageConsumer messageConsumer, IProgressMonitor monitor);

	protected boolean isValidConfig(ConfigType config) {

		return config != null;
	}
}