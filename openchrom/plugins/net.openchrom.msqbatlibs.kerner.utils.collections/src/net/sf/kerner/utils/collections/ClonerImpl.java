package net.sf.kerner.utils.collections;

import java.util.List;

import net.sf.kerner.utils.Cloneable;
import net.sf.kerner.utils.Cloner;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class ClonerImpl<T extends Cloneable<T>> extends AbstractTransformingListFactory<T, T> implements Cloner<T>, ClonerList<T> {

	public T clone(final T element) {

		return element.clone();
	}

	public List<T> cloneList(final List<? extends T> elements) {

		return transformCollection(elements);
	}

	public T transform(final T element) {

		return clone(element);
	}
}
