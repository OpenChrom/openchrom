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

import net.sf.bioutils.proteomics.sample.Sample;

public class FactoryPeakImpl implements FactoryPeak {

    private Sample sample;

    private int fractionIndex;

    @Override
    public PeakImpl create(final double mz, final double intensity, final double intensityToNoise) {
        return create(null, mz, intensity, intensityToNoise);
    }

    @Override
    public PeakImpl create(final String name, final double mz, final double intensity,
            final double intensityToNoise) {
        return new PeakImpl(name, getFractionIndex(), mz, intensity, intensityToNoise, getSample());
    }

    public synchronized int getFractionIndex() {
        return fractionIndex;
    }

    public synchronized Sample getSample() {
        return sample;
    }

    public synchronized void setFractionIndex(final int fractionIndex) {
        this.fractionIndex = fractionIndex;
    }

    public synchronized void setSample(final Sample sample) {
        this.sample = sample;
    }

}
