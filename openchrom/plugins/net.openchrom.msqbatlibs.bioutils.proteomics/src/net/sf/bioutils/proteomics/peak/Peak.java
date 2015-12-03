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
package net.sf.bioutils.proteomics.peak;

import net.sf.bioutils.proteomics.fraction.Fraction;
import net.sf.bioutils.proteomics.provider.ProviderFractionIndex;
import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.bioutils.proteomics.provider.ProviderMz;
import net.sf.bioutils.proteomics.provider.ProviderSample;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.Cloneable;

/**
 *
 * A {@code Peak} represents a MS signal which has at least two properties:
 * <ol>
 * <li>a mass-to-charge-ratio</li>
 * <li>and a signal intensity</li>
 * </ol>
 * A {@code Peak} is usually also associated to a {@link Sample} and, if it was
 * detected during a MALDI-MS experiment, also a {@link Fraction}.
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Peak extends ProviderIntensity, ProviderMz, ProviderFractionIndex, ProviderSample,
        Cloneable<Peak> {

    @Override
    Peak clone();

    @Override
    int getFractionIndex();

    String getName();

    @Override
    Sample getSample();

    void setFractionIndex(int index);

    void setSample(Sample sample);

}
