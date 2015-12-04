package net.sf.bioutils.proteomics.annotation;

import java.util.Collection;

import net.sf.bioutils.proteomics.peak.FactoryPeak;

public interface FactoryPeakAnnotatable extends FactoryPeak {

	public PeakAnnotatable create(final String name, final double mz, final double intensity, final double intensityToNoise, Collection<AnnotationSerializable> a);
}
