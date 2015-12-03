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
package net.sf.bioutils.proteomics.view;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;

public class FactoryViewPeakToFractionIndex extends AbstractTransformingListFactory<Peak, ViewPeakToFractionIndex> {

    public FactoryViewPeakToFractionIndex() {
        super();
    }

    public FactoryViewPeakToFractionIndex(final FactoryList<ViewPeakToFractionIndex> factory) {
        super(factory);
    }

    @Override
    public ViewPeakToFractionIndex transform(final Peak element) {
        return new ViewPeakToFractionIndex(element);
    }
}
