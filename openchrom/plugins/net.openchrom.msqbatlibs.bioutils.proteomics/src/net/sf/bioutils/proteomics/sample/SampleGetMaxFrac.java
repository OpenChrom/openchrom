package net.sf.bioutils.proteomics.sample;

import java.util.Arrays;
import java.util.Collection;

import net.sf.bioutils.proteomics.peak.Peak;

public class SampleGetMaxFrac {

	public int getMaxFrac(final Collection<? extends Sample> samples) {

		int result = -1;
		for(final Sample s : samples) {
			for(final Peak p : s.getPeaks()) {
				if(p.getFractionIndex() > result) {
					result = p.getFractionIndex();
				}
			}
		}
		return result;
	}

	public int getMaxFrac(final Sample sample) {

		return getMaxFrac(Arrays.asList(sample));
	}
}
