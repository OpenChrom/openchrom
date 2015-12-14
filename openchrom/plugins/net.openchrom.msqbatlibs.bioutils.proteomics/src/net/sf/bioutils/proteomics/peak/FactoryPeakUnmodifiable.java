/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peak;

public class FactoryPeakUnmodifiable implements FactoryPeak {

	private FactoryPeak delegate;

	
	public Peak create(final double mz, final double intensity, final double intensityToNoise) {

		return create(null, mz, intensity, intensityToNoise);
	}

	
	public Peak create(final String name, final double mz, final double intensity, final double intensityToNoise) {

		return new PeakUnmodifiable(delegate.create(name, mz, intensity, intensityToNoise));
	}

	public synchronized FactoryPeak getDelegate() {

		return delegate;
	}

	public synchronized FactoryPeakUnmodifiable setDelegate(final FactoryPeak delegate) {

		this.delegate = delegate;
		return this;
	}
}
