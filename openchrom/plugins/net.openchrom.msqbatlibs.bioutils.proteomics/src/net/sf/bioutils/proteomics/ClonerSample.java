package net.sf.bioutils.proteomics;

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.ClonerImpl;

public class ClonerSample extends ClonerImpl<Sample> {

	private final String newName;
	private final ClonerPeak clonerPeak = new ClonerPeak();

	public ClonerSample(final String newName) {

		this.newName = newName;
	}

	@Override
	public Sample clone(final Sample element) {

		final Sample result = element.clone(newName);
		result.setPeaks(clonerPeak.cloneList(element.getPeaks()));
		return result;
	}
}
