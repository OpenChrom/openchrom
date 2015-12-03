package net.sf.bioutils.proteomics.peak;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.sf.bioutils.proteomics.fraction.Fraction;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.bioutils.proteomics.standard.Standard;
import net.sf.kerner.utils.Util;

public class FractionUnmodifiable implements Fraction {

    private final Fraction delegate;

    public FractionUnmodifiable(final Fraction delegate) {
        Util.checkForNull(delegate);
        this.delegate = delegate;
    }

    @Override
    public void addPeak(final Peak peak) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addStandard(final Standard standard) {
        throw new UnsupportedOperationException();

    }

    @Override
    public FractionUnmodifiable clone() {
        return new FractionUnmodifiable(delegate.clone());
    }

    @Override
    public FractionUnmodifiable cloneWOPeaks() {
        return new FractionUnmodifiable(delegate.cloneWOPeaks());
    }

    @Override
    public boolean equals(final Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public int getIndex() {
        return delegate.getIndex();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public List<Peak> getPeaks() {
        final List<Peak> l = new ArrayList<Peak>(
                new TransformerPeakToUnmodifiable().transformCollection(delegate.getPeaks()));
        return Collections.unmodifiableList(l);
    }

    @Override
    public Sample getSample() {
        return delegate.getSample();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public Set<Standard> getStandards() {
        return Collections.unmodifiableSet(delegate.getStandards());
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public void setPeaks(final Collection<? extends Peak> peaks) {
        delegate.setPeaks(peaks);
    }

    @Override
    public void setSample(final Sample sample) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStandards(final Collection<? extends Standard> standards) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "FractionUnmodifiable:" + delegate;
    }

}
