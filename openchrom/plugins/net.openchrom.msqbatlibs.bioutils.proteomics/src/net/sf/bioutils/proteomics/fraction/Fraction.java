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
package net.sf.bioutils.proteomics.fraction;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.bioutils.proteomics.standard.Standard;
import net.sf.kerner.utils.Cloneable;

/**
 * A {@code Fraction} is a collection of {@link Peak Peaks} eluting at the same
 * time.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 * <p>
 * last reviewed 2014-04-09
 * </p>
 *
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Fraction extends Cloneable<Fraction> {

    public void addPeak(Peak peak);

    void addStandard(Standard standard);

    /**
     * Creates a clone of this {@code Fraction}. {@link Sample} of the new
     * {@code Fraction} instance will be {@code null}.
     */
    @Override
    public Fraction clone();

    /**
     *
     * Clones this {@code Fraction}, dismissing peaks.
     *
     * @see #clone()
     */
    Fraction cloneWOPeaks();

    int getIndex();

    /**
     * Returns the name of this fraction.
     *
     * @return name of this fraction
     */
    String getName();

    List<Peak> getPeaks();

    Sample getSample();

    /**
     * Sample getSample();
     *
     *
     *
     * /** Get number of {@link Peak peaks} in this fraction.
     *
     * @return number of {@link Peak peaks} in this fraction
     */
    int getSize();

    /**
     * Returns this fraction's {@link Standard standards}.
     *
     * @return this fraction's {@link Standard standards}
     */
    Set<Standard> getStandards();

    /**
     * Checks weather this fraction is empty {@code getSize() == 0}.
     *
     * @return {@code true}, if number of {@link Peak peaks} in this fraction is
     *         {@code 0}; {@code false} otherwise
     */
    boolean isEmpty();

    void setPeaks(Collection<? extends Peak> peaks);

    void setSample(Sample sample);

    void setStandards(Collection<? extends Standard> standards);

}
