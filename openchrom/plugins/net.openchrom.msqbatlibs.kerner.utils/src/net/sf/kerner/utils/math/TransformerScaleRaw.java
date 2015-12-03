package net.sf.kerner.utils.math;

public class TransformerScaleRaw implements TransformerScale {

    public double getRatio(final double n1, final double n2) {
        return n1 / n2;
    }

    public double invert(final double number) {
        return number;
    }

    public boolean isLog() {
        return false;
    }

    public Double transform(final Double number) {
        return number;
    }
}
