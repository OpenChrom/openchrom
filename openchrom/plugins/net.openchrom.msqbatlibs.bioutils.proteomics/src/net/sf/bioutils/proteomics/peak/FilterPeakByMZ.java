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
import net.sf.kerner.utils.math.UtilMath;

public class FilterPeakByMZ implements Filter<Peak> {

	public static int DEFAULT_ACCURACY = 1;

	private final int accuracy;

	private final double mz;

	public FilterPeakByMZ(final double mz) {
		this(mz, DEFAULT_ACCURACY);
	}

	public FilterPeakByMZ(final double mz, final int accuracy) {
		this.accuracy = accuracy;
		this.mz = mz;
	}

	public boolean filter(final Peak element) {
		return UtilMath.round(element.getMz(), accuracy) == UtilMath.round(mz,
				accuracy);
	}

	@Override
	public String toString() {
		return "mz=" + mz + ",accuracy=" + accuracy;
	}
}
