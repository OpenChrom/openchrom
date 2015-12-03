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

import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakByMZPpm implements Filter<Peak> {

    protected final double massShift;

    protected final double parentMass;

    public FilterPeakByMZPpm(final double massShift, final double parentMass) {
        super();
        this.massShift = massShift;
        this.parentMass = parentMass;
    }

    @Override
    public boolean filter(final Peak element) {
        final double d = (parentMass - element.getMz()) * 1000000 / parentMass;
        if (Math.abs(d) <= massShift) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public String toString() {
        return "parentMass=" + parentMass + ",shift=" + massShift;
    }

}
