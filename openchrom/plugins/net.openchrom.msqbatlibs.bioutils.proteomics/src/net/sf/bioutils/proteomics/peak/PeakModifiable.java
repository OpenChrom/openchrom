/*******************************************************************************
 * Copyright 2011-2015 Alexander Kerner. All rights reserved.
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

/**
 *
 * Extends {@link Peak} by modifiability.
 * <ol>
 * <li>Intensity,</li>
 * <li>name</li>
 * <li>and sample</li>
 * </ol>
 * may be set.
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
 *
 * </p>
 * <p>
 * last reviewed: 2015-06-14
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface PeakModifiable extends Peak, PeakModifiableIntensity {

	void setName(String name);

	@Override
	void setSample(Sample sample);

}
