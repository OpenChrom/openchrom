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

import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.kerner.utils.math.TransformerLog2;
import net.sf.kerner.utils.math.TransformerScale;
import net.sf.kerner.utils.math.UtilMath;

/**
 *
 * TODO description
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
 * Fully thread save (stateless).
 * </pre>
 *
 * </p>
 * <p>
 * last reviewed: 2014-02-18
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class TransformerPeakToIntensityLog2 extends TransformerPeakToIntensity {

    @Override
    public TransformerScale getScale() {
        return new TransformerLog2();
    }

    @Override
    public Double transform(final ProviderIntensity element) {
        return UtilMath.log2(super.transform(element));
    }
}
