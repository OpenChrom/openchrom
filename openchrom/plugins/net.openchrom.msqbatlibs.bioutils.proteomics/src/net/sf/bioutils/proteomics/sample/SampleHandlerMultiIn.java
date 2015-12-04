package net.sf.bioutils.proteomics.sample;

import java.util.List;

public interface SampleHandlerMultiIn<S extends Sample> {

	S handle(List<? extends Sample> samples) throws Exception;
}
