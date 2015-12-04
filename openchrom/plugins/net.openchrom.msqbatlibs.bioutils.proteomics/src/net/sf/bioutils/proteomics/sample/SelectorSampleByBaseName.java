package net.sf.bioutils.proteomics.sample;

import java.util.Collection;

import net.sf.kerner.utils.collections.Selector;

public class SelectorSampleByBaseName<T extends Sample> implements Selector<T> {

	private final String name;

	public SelectorSampleByBaseName(final String name) {

		this.name = name;
	}

	@Override
	public T select(final Collection<? extends T> elements) {

		for(final T s : elements) {
			if(s.getNameBase().equals(name)) {
				return s;
			}
		}
		return null;
	}
}
