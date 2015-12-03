/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
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
package net.sf.kerner.utils.pair;

/**
 * 
 * Default implementation for {@link Pair}.
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * <b>Threading:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * Not thread save.
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-12-09
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 * @param <F>
 *            type of first object
 * @param <S>
 *            type of second object
 */
public class PairImpl<F, S> implements Pair<F, S> {

    private F first;

    private S second;

    public PairImpl() {
    }

    public PairImpl(final F first) {
        this.first = first;

    }

    public PairImpl(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public PairImpl(final Pair<? extends F, ? extends S> template) {
        this(template.getFirst(), template.getSecond());
    }

    /**
     * <b>Note:</b> A new {@code ObjectPairImpl} object is created, but
     * {@code first} and {@code second} objects are not cloned here. If this is
     * desired, use {@link PairImpl#ObjectPairImpl(Object, Object)}
     * constructor to create a new instance and clone {@code first} and
     * {@code second} here also.
     */
    @Override
    public PairImpl<F, S> clone() {
        return new PairImpl<F, S>(getFirst(), getSecond());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Pair))
            return false;
        @SuppressWarnings("rawtypes")
        final PairImpl other = (PairImpl) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first) && !first.equals(other.second))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second) && !second.equals(other.first))
            return false;
        return true;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime + result + ((first == null) ? 0 : first.hashCode());
        result = prime + result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    public PairImpl<S, F> invert() {
        return new PairImpl<S, F>(getSecond(), getFirst());
    }

    public void setFirst(final F first) {
        this.first = first;
    }

    public void setSecond(final S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return getFirst() + "," + getSecond();
    }
}
