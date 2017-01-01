/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.bioutils.proteomics.annotation.FeatureAnnotatable;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakUnmodifiable;
import net.sf.bioutils.proteomics.peak.TransformerPeakToUnmodifiable;

public class FeatureUnmodifiable extends PeakUnmodifiable implements FeatureAnnotatable {

	public FeatureUnmodifiable(final Feature delegate) {
		super(delegate);
	}

	public FeatureUnmodifiable clone() {

		return new FeatureUnmodifiable(((FeatureAnnotatable)delegate).clone());
	}

	public int getIndexCenter() {

		return ((FeatureAnnotatable)delegate).getIndexCenter();
	}

	public int getIndexFirst() {

		return ((FeatureAnnotatable)delegate).getIndexFirst();
	}

	public int getIndexLast() {

		return ((FeatureAnnotatable)delegate).getIndexLast();
	}

	public List<Peak> getMembers() {

		final List<Peak> l = new ArrayList<Peak>(new TransformerPeakToUnmodifiable().transformCollection(((FeatureAnnotatable)delegate).getMembers()));
		return Collections.unmodifiableList(l);
	}

	public Iterator<Peak> iterator() {

		return ((FeatureAnnotatable)delegate).iterator();
	}

	public String toString() {

		return "FeatureUnmodifiable:" + delegate;
	}
}
