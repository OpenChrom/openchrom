package net.sf.bioutils.proteomics;

import java.util.List;

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.filter.Filter;

public class CopierFilterNames {

	public void copy(final Sample sample, final List<? extends Filter<?>> filters) {

		for(int i = 0; i < filters.size(); i++) {
			final String key = "filter-" + i;
			final String value = filters.get(i).toString();
			sample.getProperties().put(key, value);
		}
	}
}
