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

	@Override
	public FeatureUnmodifiable clone() {

		return new FeatureUnmodifiable(((FeatureAnnotatable)delegate).clone());
	}

	@Override
	public int getIndexCenter() {

		return ((FeatureAnnotatable)delegate).getIndexCenter();
	}

	@Override
	public int getIndexFirst() {

		return ((FeatureAnnotatable)delegate).getIndexFirst();
	}

	@Override
	public int getIndexLast() {

		return ((FeatureAnnotatable)delegate).getIndexLast();
	}

	@Override
	public List<Peak> getMembers() {

		final List<Peak> l = new ArrayList<Peak>(new TransformerPeakToUnmodifiable().transformCollection(((FeatureAnnotatable)delegate).getMembers()));
		return Collections.unmodifiableList(l);
	}

	@Override
	public Iterator<Peak> iterator() {

		return ((FeatureAnnotatable)delegate).iterator();
	}

	@Override
	public String toString() {

		return "FeatureUnmodifiable:" + delegate;
	}
}
