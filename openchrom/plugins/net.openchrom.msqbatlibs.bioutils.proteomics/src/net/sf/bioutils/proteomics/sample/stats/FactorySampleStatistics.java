package net.sf.bioutils.proteomics.sample.stats;

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class FactorySampleStatistics extends
        AbstractTransformingListFactory<Sample, SampleStatistics> {

    @Override
    public SampleStatistics transform(final Sample sample) {
        return new SampleStatisticsCallable(sample).call();
    }

}
