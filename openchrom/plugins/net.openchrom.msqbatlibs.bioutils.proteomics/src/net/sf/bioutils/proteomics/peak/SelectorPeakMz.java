package net.sf.bioutils.proteomics.peak;

import java.util.Collection;

public class SelectorPeakMz implements SelectorPeak {

    private final Peak peak;

    public SelectorPeakMz(Peak peak) {
        super();
        this.peak = peak;
    }

    @Override
    public Peak select(Collection<? extends Peak> elements) {
        Peak result = null;
        double massDiffAbs = -1;
        for (Peak p : elements) {
            double diff = Math.abs(peak.getMz() - p.getMz());
            if (diff < massDiffAbs) {
                massDiffAbs = diff;
                result = p;
            }
        }
        return result;
    }

}
