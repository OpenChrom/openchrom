package net.sf.bioutils.proteomics.peak;

import net.sf.bioutils.proteomics.fraction.Fraction;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerFractionToUnmodifiable extends AbstractTransformingListFactory<Fraction, FractionUnmodifiable> {

	@Override
	public FractionUnmodifiable transform(final Fraction element) {

		return new FractionUnmodifiable(element);
	}
}
