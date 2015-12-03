package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.math.UtilRandom;

public class SelectorRandom<T> implements Selector<T> {

    public T select(Collection<? extends T> elements) {
        List<T> l = new ArrayList<T>(elements);
        int index = UtilRandom.generateBetween(0, l.size() - 1);
        return l.get(index);
    }
}
