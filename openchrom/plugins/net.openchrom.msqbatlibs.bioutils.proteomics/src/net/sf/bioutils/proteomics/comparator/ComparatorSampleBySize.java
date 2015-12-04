package net.sf.bioutils.proteomics.comparator;

import java.util.Comparator;

import net.sf.bioutils.proteomics.sample.Sample;

public class ComparatorSampleBySize implements Comparator<Sample> {

	@Override
	public int compare(final Sample o1, final Sample o2) {

		return Integer.valueOf(o1.getSize()).compareTo(Integer.valueOf(o2.getSize()));
	}
}
