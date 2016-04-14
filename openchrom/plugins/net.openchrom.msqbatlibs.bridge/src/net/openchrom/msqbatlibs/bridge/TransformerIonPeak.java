/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msqbatlibs.bridge;

import org.eclipse.chemclipse.msd.model.core.IIon;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakBean;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerIonPeak extends AbstractTransformingListFactory<IIon, Peak> {

	@Override
	public Peak transform(IIon ion) {

		return new PeakBean(ion.getIon(), ion.getAbundance());
	}
}
