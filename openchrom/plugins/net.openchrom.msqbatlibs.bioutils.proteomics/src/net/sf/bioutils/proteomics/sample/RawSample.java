package net.sf.bioutils.proteomics.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class RawSample {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((spectra == null) ? 0 : spectra.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RawSample)) {
            return false;
        }
        RawSample other = (RawSample) obj;
        if (spectra == null) {
            if (other.spectra != null) {
                return false;
            }
        } else if (!spectra.equals(other.spectra)) {
            return false;
        }
        return true;
    }

    private final NavigableSet<RawSpectrum> spectra = new TreeSet<RawSpectrum>();

    public void addSpectrum(RawSpectrum spectrum) {
        this.spectra.add(spectrum);
    }

    public Set<RawSpectrum> getSpectra() {
        return Collections.unmodifiableSet(spectra);
    }

    public List<RawSpectrum> getSpectraList() {
        return Collections
                .unmodifiableList(new ArrayList<RawSpectrum>(spectra));
    }
}
