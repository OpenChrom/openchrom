package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.math.UtilRandom;

public class SelectorMultiRandom<T> implements SelectorMulti<T> {

	public final static double DEFAULT_PROP = 1.0;
	private double prop = DEFAULT_PROP;

	public SelectorMultiRandom() {

	}

	public SelectorMultiRandom(double prop) {

		this.prop = prop;
	}

	public synchronized double getProp() {

		return prop;
	}

	public Collection<T> select(Collection<? extends T> elements) {

		List<T> result = new ArrayList<T>();
		for(T t : elements) {
			if(UtilRandom.generateWithProbability(getProp())) {
				result.add(t);
			}
		}
		return result;
	}

	public synchronized void setProp(double prop) {

		this.prop = prop;
	}
}
