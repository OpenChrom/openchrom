package net.sf.bioutils.proteomics.sample;

public interface SampleHandlerVoid<S extends Sample> {

	void handle(Sample sample) throws Exception;
}
