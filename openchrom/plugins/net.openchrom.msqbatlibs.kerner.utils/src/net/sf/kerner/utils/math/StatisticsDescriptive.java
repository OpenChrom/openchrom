package net.sf.kerner.utils.math;

public interface StatisticsDescriptive {

    /**
     * Get maximum of given values.
     *
     * @return maximum value
     *
     */
    double getMax();

    /**
     * Calculate the {@code mean} of given values.
     *
     * @return mean of values
     *
     */
    double getMean();

    double getMedian();

    double getMin();

    int getN();

    double getSum();

}
