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
package net.sf.bioutils.proteomics.transformer;

import net.sf.bioutils.proteomics.provider.ProviderSample;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;

public class TransformerPeakToSample extends
        AbstractTransformingListFactory<ProviderSample, Sample> {

    public TransformerPeakToSample() {
        super();

    }

    public TransformerPeakToSample(final FactoryList<Sample> factory) {
        super(factory);

    }

    @Override
    public Sample transform(final ProviderSample element) {
        return element.getSample();
    }

}
