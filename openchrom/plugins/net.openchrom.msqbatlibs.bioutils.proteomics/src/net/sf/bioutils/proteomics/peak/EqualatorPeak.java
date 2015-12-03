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

import net.sf.kerner.utils.equal.Equalator;
import net.sf.kerner.utils.equal.UtilEqual;
import net.sf.kerner.utils.math.UtilMath;
import net.sf.kerner.utils.pair.Pair;

/**
 *
 * Compares {@code element} with {@code object} for equality. m/z and fraction
 * index are values which are considered.
 *
 *
 * </p>
 * <p>
 * <b>Threading:</b><br>
 *
 * </p>
 * <p>
 * Fully thread save since stateless.
 * </p>
 * <p>
 * last reviewed: 2014-06-20
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class EqualatorPeak implements Equalator<Peak> {

    /**
     * Compares {@code element} with {@code object} for equality. m/z and
     * fraction index are values which are considered.
     *
     * @param peak
     *            a {@code Peak}
     * @param obj
     *            any other object
     */
    @Override
    public boolean areEqual(final Peak peak, final Object obj) {
        if (peak == null && obj != null)
            return false;
        if (peak != null && obj == null)
            return false;
        if (peak == obj)
            return true;
        if (!(obj instanceof Peak))
            return false;
        if (!UtilEqual.areEqual(UtilMath.round(peak.getMz(), 4),
                UtilMath.round(((Peak) obj).getMz(), 4)))
            return false;
        if (!UtilEqual.areEqual(peak.getFractionIndex(), ((Peak) obj).getFractionIndex()))
            return false;
        if (peak.getSampleName() != null && ((Peak) obj).getSampleName() == null) {
            return false;
        }
        if (peak.getSampleName() == null && ((Peak) obj).getSampleName() != null) {
            return false;
        }
        if (peak.getSampleName() == null && ((Peak) obj).getSampleName() == null) {

        } else {
            if (!peak.getSampleName().equals(((Peak) obj).getSampleName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean transform(final Pair<Peak, Object> element) {
        return Boolean.valueOf(areEqual(element.getFirst(), element.getSecond()));
    }

}
