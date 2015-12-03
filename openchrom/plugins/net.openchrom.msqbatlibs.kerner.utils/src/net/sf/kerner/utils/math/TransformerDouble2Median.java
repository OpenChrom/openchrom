package net.sf.kerner.utils.math;

import java.util.List;

import net.sf.kerner.utils.transformer.Transformer;

public class TransformerDouble2Median implements Transformer<List<Double>, Double> {

    public Double transform(final List<Double> element) {
        return UtilMath.getMedian(element);
    }

}
