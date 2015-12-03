package net.sf.kerner.utils.math;

public abstract class TransformerLogAbstract implements TransformerScale {

    public double getRatio(final double n1, final double n2) {
        return transform(n1) - transform(n2);
    }

    public boolean isLog() {
        return true;
    }

}
