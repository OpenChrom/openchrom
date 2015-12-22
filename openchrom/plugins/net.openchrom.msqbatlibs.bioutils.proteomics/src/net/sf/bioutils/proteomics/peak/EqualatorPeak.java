/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
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
	public boolean areEqual(final Peak peak, final Object obj) {

		if(peak == null && obj != null)
			return false;
		if(peak != null && obj == null)
			return false;
		if(peak == obj)
			return true;
		if(!(obj instanceof Peak))
			return false;
		if(!UtilEqual.areEqual(UtilMath.round(peak.getMz(), 4), UtilMath.round(((Peak)obj).getMz(), 4)))
			return false;
		if(!UtilEqual.areEqual(peak.getFractionIndex(), ((Peak)obj).getFractionIndex()))
			return false;
		if(peak.getSampleName() != null && ((Peak)obj).getSampleName() == null) {
			return false;
		}
		if(peak.getSampleName() == null && ((Peak)obj).getSampleName() != null) {
			return false;
		}
		if(peak.getSampleName() == null && ((Peak)obj).getSampleName() == null) {
		} else {
			if(!peak.getSampleName().equals(((Peak)obj).getSampleName())) {
				return false;
			}
		}
		return true;
	}

	public Boolean transform(final Pair<Peak, Object> element) {

		return Boolean.valueOf(areEqual(element.getFirst(), element.getSecond()));
	}
}
