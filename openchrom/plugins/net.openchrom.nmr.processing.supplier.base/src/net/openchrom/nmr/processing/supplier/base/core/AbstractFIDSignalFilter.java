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

import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.processing.DataCategory;

public abstract class AbstractFIDSignalFilter<ConfigType> extends AbstractComplexSignalFilter<ConfigType, FIDMeasurement> implements Serializable {

	private static final long serialVersionUID = -6422870258150962140L;

	public AbstractFIDSignalFilter(Class<ConfigType> configClass) {
		super(configClass);
	}

	@Override
	public DataCategory[] getDataCategories() {

		return new DataCategory[]{DataCategory.FID};
	}

	@Override
	public boolean acceptsIMeasurement(IMeasurement item) {

		return item instanceof FIDMeasurement && accepts((FIDMeasurement)item);
	}

	protected boolean accepts(FIDMeasurement item) {

		return true;
	}
}
