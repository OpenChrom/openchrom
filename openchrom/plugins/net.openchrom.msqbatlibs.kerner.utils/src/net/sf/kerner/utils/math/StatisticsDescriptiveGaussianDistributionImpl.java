package net.sf.kerner.utils.math;

import java.util.Arrays;

public class StatisticsDescriptiveGaussianDistributionImpl implements
        StatisticsDescriptiveGaussianDistribution {

    public static double getCoefficientOfVariation(final double standardDeviation, final double mean) {
        return standardDeviation / mean;
    }

    public static double getStandardDeviation(final double variance) {
        return Math.sqrt(variance);
    }

    public static double getStandardErrorOfMean(final double standardDeviation, final int n) {
        return standardDeviation / Math.sqrt(n);
    }

    public static double getVariance(final double standardDeviation) {
        return Math.pow(standardDeviation, 2);
    }

    private double cacheMean = Double.NaN;

    protected final double[] values;

    private double cacheMedian = Double.NaN;

    public StatisticsDescriptiveGaussianDistributionImpl(final double[] values) {
        if (values == null || values.length < 1)
            throw new IllegalArgumentException();
        final double[] b = new double[values.length];
        System.arraycopy(values, 0, b, 0, b.length);
        Arrays.sort(b);
        this.values = b;
    }

    public double getCoefficientOfVariation() {
        return getCoefficientOfVariation(getStandardDeviation(), getMean());
    }

    public double getMax() {
        return getMax(true);
    }

    protected double getMax(final boolean sorted) {
        if (sorted) {
            return values[values.length - 1];
        }
        double result = values[0];
        for (final double i : values) {
            if (i > result) {
                result = i;
            }
        }
        return result;
    }

    public synchronized double getMean() {
        double result = cacheMean;
        if (Double.isNaN(result)) {
            result = getSum() / getN();
            cacheMean = result;
        }
        return result;
    }

    public synchronized double getMedian() {
        double result = cacheMedian;
        if (Double.isNaN(result)) {
            if (values.length % 2 == 0) {
                result = (values[(values.length / 2) - 1] + values[values.length / 2]) / 2;
            } else {
                result = values[values.length / 2];
            }
            cacheMedian = result;
        }
        return result;
    }

    public double getMin() {
        return getMin(true);
    }

    protected double getMin(final boolean sorted) {
        if (sorted) {
            return values[0];
        }
        double result = values[0];
        for (final double i : values) {
            if (i < result)
                result = i;
        }
        return result;
    }

    public int getN() {
        return values.length;
    }

    public double getStandardDeviation() {
        return getStandardDeviation(getVariance());
    }

    public double getStandardErrorOfMean() {
        return getStandardErrorOfMean(getStandardDeviation(), getN());
    }

    public double getSum() {
        double result = 0;
        for (final double d : values) {
            result += d;
        }
        return result;
    }

    public double getVariance() {
        double result = 0;
        final double mean = getMean();
        for (final double d : values) {
            final double dd = d - mean;
            result += (dd) * (dd);
        }
        return result / (getN() - 1);
    }

}
