/*******************************************************************************
 * Copyright (c) 2016, 2017 Lablicate GmbH.
 *
 * All rights reserved.
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.model;

import org.eclipse.chemclipse.msd.model.core.IIon;

import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.math.UtilMath;

/**
 * A {@link net.sf.kerner.utils.collections.filter.Filter Filter} to filter
 * {@link IIon ions} by {@code mz}.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 *
 */
public class FilterIonByMz implements Filter<IIon> {

	/**
	 * Default accuracy of this filter.
	 */
	public static int DEFAULT_ACCURACY = 4;
	/**
	 * The accuracy of this filter.
	 */
	private final int accuracy;
	/**
	 * The mass-to-charge ratio on which is filtered.
	 */
	private final double mz;

	public FilterIonByMz(final double mz) {
		this(mz, DEFAULT_ACCURACY);
	}

	public FilterIonByMz(final double mz, final int accuracy) {
		this.accuracy = accuracy;
		this.mz = mz;
	}

	@Override
	public boolean filter(final IIon element) {

		return UtilMath.round(element.getIon(), accuracy) == UtilMath.round(mz, accuracy);
	}

	@Override
	public String toString() {

		return "mz=" + mz + ",accuracy=" + accuracy;
	}
}
