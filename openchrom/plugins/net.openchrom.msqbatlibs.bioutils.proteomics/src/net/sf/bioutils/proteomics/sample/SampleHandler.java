package net.sf.bioutils.proteomics.sample;

import java.util.Collection;
import java.util.List;

import net.sf.bioutils.proteomics.peak.Peak;

public interface SampleHandler<S extends Sample> {

	List<Peak> handle(Collection<? extends Peak> peaks) throws Exception;

	S handle(Sample sample) throws Exception;
}
