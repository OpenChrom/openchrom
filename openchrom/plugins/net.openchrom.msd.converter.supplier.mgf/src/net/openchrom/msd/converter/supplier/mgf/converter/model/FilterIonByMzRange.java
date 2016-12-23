/*******************************************************************************
 * Copyright (c) 2016 Lablicate GmbH.
 *
 * All rights reserved.
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.model;

import org.eclipse.chemclipse.msd.model.core.IIon;

import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.kerner.utils.collections.filter.Filter;

/**
 * A {@link net.sf.kerner.utils.collections.filter.Filter Filter} to filter
 * {@link IIon ions} by {@code mz}.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 *
 */
public class FilterIonByMzRange implements Filter<IIon> {

	/**
	 * The mass-to-charge ratio on which is filtered.
	 */
	private final RangeDouble mz;

	public FilterIonByMzRange(final RangeDouble mz) {
		this.mz = mz;
	}

	@Override
	public boolean filter(final IIon element) {

		return mz.includes(element.getIon());
	}

	@Override
	public String toString() {

		return "mz=" + mz;
	}
}
