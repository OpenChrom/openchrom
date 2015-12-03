package net.sf.kerner.utils.math;

public class TransformerLogImpl extends TransformerLogAbstract {

    private final double base;

    public TransformerLogImpl(final double base) {
        this.base = base;
    }

    public double invert(final double number) {
        return Math.pow(base, number);
    }

    public Double transform(final Double number) {
        return UtilMath.log(number, base);
    }

}
