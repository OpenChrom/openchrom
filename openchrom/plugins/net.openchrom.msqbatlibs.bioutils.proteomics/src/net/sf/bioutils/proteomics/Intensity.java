/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Intensity extends Number implements Serializable, Comparable<Intensity> {

    protected final BigDecimal delegate;

    private transient double cacheDouble;

    private static final long serialVersionUID = -7368718631256337133L;

    public static int getScale() {
        return 10;
    }

    public static double toDouble(final BigDecimal big) {
        if (new BigDecimal(Double.MAX_VALUE).compareTo(big) < 0) {
            throw new IllegalStateException("Cannot convert to primitive " + big);
        }
        return big.doubleValue();
    }

    public Intensity() {
        delegate = BigDecimal.ZERO;
        setScale();

    }

    public Intensity(final BigDecimal delegate) {
        this.delegate = delegate;
        setScale();

    }

    public Intensity(final double doubleValue) {
        delegate = new BigDecimal(doubleValue);
        setScale();

    }

    public Intensity(final Intensity template) {
        delegate = template.delegate;
        setScale();

    }

    public Intensity(final String s) {
        delegate = new BigDecimal(s);
        setScale();

    }

    public Intensity add(final BigDecimal augend) {
        return new Intensity(delegate.add(augend));
    }

    public Intensity add(final Intensity augend) {
        return new Intensity(delegate.add(augend.delegate));
    }

    @Override
    public int compareTo(final Intensity o) {
        return delegate.compareTo(o.delegate);
    }

    public Intensity divide(final BigDecimal divisor) {
        return new Intensity(delegate.divide(divisor, getScale()));
    }

    public Intensity divide(final double divisor) {
        return new Intensity(delegate.divide(new BigDecimal(divisor), getScale()));
    }

    public Intensity divide(final Intensity divisor) {
        return new Intensity(delegate.divide(divisor.delegate, getScale(), RoundingMode.HALF_UP));
    }

    @Override
    public synchronized double doubleValue() {
        double result = cacheDouble;
        if (result == 0) {
            cacheDouble = toDouble(delegate);
            result = cacheDouble;
        }
        return result;
    }

    @Override
    public float floatValue() {
        return delegate.floatValue();
    }

    public BigDecimal getBigDecimal() {
        return delegate;
    }

    @Override
    public int intValue() {
        if (new Intensity(Integer.MAX_VALUE).isLess(this)) {
            throw new IllegalStateException("Cannot convert to primitive " + this);
        }
        return delegate.intValue();
    }

    public boolean isEqual(final Intensity intensity) {
        return compareTo(intensity) == 0;
    }

    public boolean isLess(final Intensity intensity) {
        return compareTo(intensity) < 0;
    }

    public boolean isMore(final Intensity intensity) {
        return compareTo(intensity) > 0;
    }

    public boolean isValid() {
        return isMore(new Intensity());
    }

    @Override
    public long longValue() {
        return delegate.longValue();
    }

    public Intensity multiply(final BigDecimal multiplicand) {
        return new Intensity(delegate.multiply(multiplicand));
    }

    public Intensity multiply(final Intensity multiplicand) {
        return new Intensity(delegate.multiply(multiplicand.delegate));
    }

    protected void setScale() {
        delegate.setScale(getScale(), RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

}
