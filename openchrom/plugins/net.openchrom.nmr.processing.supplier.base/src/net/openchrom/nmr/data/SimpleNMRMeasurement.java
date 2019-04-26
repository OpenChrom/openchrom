/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 * Alexander Kerner - initial API and implementation
 *
 *******************************************************************************/
package net.openchrom.nmr.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurementBody;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;

public class SimpleNMRMeasurement implements SpectrumMeasurementBody {

	private Collection<SimpleNMRSignal> signals = new ArrayList<>();

	@Override
	public Collection<? extends SpectrumSignal> getSignals() {
		return Collections.unmodifiableCollection(signals);
	}

}
