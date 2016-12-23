/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.annotation;

import java.util.Collection;

import net.sf.bioutils.proteomics.peak.FactoryPeak;

public interface FactoryPeakAnnotatable extends FactoryPeak {

	public PeakAnnotatable create(final String name, final double mz, final double intensity, final double intensityToNoise, Collection<AnnotationSerializable> a);
}
