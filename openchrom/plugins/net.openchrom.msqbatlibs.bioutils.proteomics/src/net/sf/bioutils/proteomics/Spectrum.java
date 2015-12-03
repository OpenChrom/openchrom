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

import java.util.Collection;
import java.util.List;

import net.sf.bioutils.proteomics.peak.Peak;

/**
 * 
 * A {@code Spectrum} is a {@link Collection} of {@link Peak Peaks} which result
 * from fractionation of another {@link Peak}.
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
 * last reviewed: 2013-07-08
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-07-08
 * 
 */
public interface Spectrum extends Iterable<Peak> {

    /**
     * 
     * @return this {@code Spectrum's} name, if available, or {@code null}
     *         otherwise
     */
    String getName();

    /**
     * 
     * @return mass of parent ion which was fractionated
     */
    double getParentMass();

    /**
     * 
     * @return {@link Peak Peaks} representing this {@code Spectrum}
     */
    List<Peak> getPeaks();

}
