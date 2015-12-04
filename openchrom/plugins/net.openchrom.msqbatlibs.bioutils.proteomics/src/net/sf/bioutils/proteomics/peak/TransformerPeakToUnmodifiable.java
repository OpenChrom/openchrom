package net.sf.bioutils.proteomics.peak;

import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerPeakToUnmodifiable extends AbstractTransformingListFactory<Peak, PeakUnmodifiable> {

	@Override
	public PeakUnmodifiable transform(final Peak element) {

		return new PeakUnmodifiable(element);
	}
}
