package net.sf.kerner.utils.collections.filter;

import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.jranges.range.doublerange.impl.RangeDoubleDummy;
import net.sf.kerner.utils.transformer.Transformer;

public abstract class FilterNumberDouble<T> implements Filter<T> {

    public static enum TYPE {
        EXACT, MIN, MAX
    }

    private final RangeDouble range;

    public FilterNumberDouble(final double value, final TYPE type) {
        switch (type) {
            case EXACT:
                range = new RangeDoubleDummy(value, value);
                break;
            case MIN:
                range = new RangeDoubleDummy(value, Integer.MAX_VALUE);
                break;
            case MAX:
                range = new RangeDoubleDummy(Integer.MIN_VALUE, value);
                break;
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }

    public FilterNumberDouble(final RangeDouble range) {
        this.range = range;
    }

    public final boolean filter(final T e) {
        return range.includes(getTransformer().transform(e));
    }

    protected abstract Transformer<T, Double> getTransformer();

}
