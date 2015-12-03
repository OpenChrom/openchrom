package net.sf.kerner.utils.collections;

public class RangeScaler {

    public double[] scale(final double[] arr, final double newMax) {
        final double[] result = new double[arr.length];

        final double scale = newMax / arr[arr.length - 1];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] * scale;
        }

        return result;
    }

}
