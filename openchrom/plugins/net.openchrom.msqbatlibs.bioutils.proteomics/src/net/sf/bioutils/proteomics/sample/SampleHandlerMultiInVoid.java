package net.sf.bioutils.proteomics.sample;

import java.util.List;

public interface SampleHandlerMultiInVoid {

    void handle(List<? extends Sample> samples) throws Exception;

}
