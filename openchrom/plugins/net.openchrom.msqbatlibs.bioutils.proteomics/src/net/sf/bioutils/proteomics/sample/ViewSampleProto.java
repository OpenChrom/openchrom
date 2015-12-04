package net.sf.bioutils.proteomics.sample;

public abstract class ViewSampleProto implements ViewSample {

	private final Sample original;

	public ViewSampleProto(final Sample original) {

		this.original = original;
	}

	@Override
	public Sample getOriginal() {

		return original;
	}
}
