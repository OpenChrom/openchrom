package net.sf.bioutils.proteomics.peak;


public class FactoryPeakUnmodifiable implements FactoryPeak {

    private FactoryPeak delegate;

    @Override
    public Peak create(final double mz, final double intensity, final double intensityToNoise) {
        return create(null, mz, intensity, intensityToNoise);
    }

    @Override
    public Peak create(final String name, final double mz, final double intensity,
            final double intensityToNoise) {
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
