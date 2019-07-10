/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Kerner - initial API
 * Christoph Läubrich - implementation
 *******************************************************************************/
package net.openchrom.nmr.processing;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.processing.core.DefaultProcessingResult;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.FilterChain;
import org.eclipse.core.runtime.NullProgressMonitor;

public class FilterUtils {

	public FilterUtils() {
	}

	public static <ConfigType> Collection<? extends IMeasurement> applyFilter(IMeasurementFilter<ConfigType> filter, IMeasurement measurement) {

		Set<IMeasurement> items = Collections.singleton(measurement);
		ConfigType configuration = filter.createConfiguration(items);
		Collection<? extends IMeasurement> filtered = filter.filterIMeasurements(items, configuration, new FilterChain<Collection<? extends IMeasurement>>() {

			@Override
			public Collection<? extends IMeasurement> doFilter(Collection<? extends IMeasurement> items, MessageConsumer messageConsumer) {

				return items;
			}
		}, new DefaultProcessingResult<>(), new NullProgressMonitor());
		return filtered;
	}
}
