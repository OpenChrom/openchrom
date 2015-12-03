package net.sf.kerner.utils.math;

import net.sf.kerner.utils.transformer.Transformer;

public interface TransformerScale extends Transformer<Double, Double> {

    double getRatio(double n1, double n2);

    double invert(double number);

    boolean isLog();

}
