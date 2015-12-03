package net.sf.kerner.utils.math;

public class MissingValues {

    public static double[] remove(final double[] values) {
        final double[] result = new double[values.length];
        int i = 0;
        for (final double d : values) {
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                // ignore
            } else {
                result[i] = d;
                i++;
            }
        }
        return result;
    }

    public static double[] replace(final double[] values, final double replacement) {
        final double[] result = new double[values.length];
        int i = 0;
        for (final double d : values) {
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                // replace
                result[i] = replacement;
                i++;
            } else {
                result[i] = d;
                i++;
            }
        }
        return result;
    }

    private MissingValues() {
    }

}
