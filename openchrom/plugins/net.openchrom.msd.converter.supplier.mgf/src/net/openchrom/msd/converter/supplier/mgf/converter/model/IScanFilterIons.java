/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved.
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.model;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;

import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

/**
 * A {@link net.sf.kerner.utils.collections.filter.Filter Filter} to filter a
 * {@link IScanMSD scan's} ions by {@code mz}. Ion filtering is delegated to a
 * given {@link FilterIonByMz}.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 *
 */
public class IScanFilterIons extends AbstractTransformingListFactory<IScanMSD, IScanMSD> {

	private static final Logger log = Logger.getLogger(IScanFilterIons.class);
	private final FilterIonByMzRange filterIon;

	public IScanFilterIons(FilterIonByMzRange filterIon) {
		this.filterIon = filterIon;
	}

	@Override
	public IScanMSD transform(IScanMSD element) {

		try {
			// Alternatively, don't use clone() but create a new instance of
			// ScanMSD, which would work without the need to catch Exception.
			IScanMSD result = element.makeDeepCopy();
			for(IIon i : element.getIons()) {
				if(filterIon.filter(i)) {
					// within precursor mz filter range
					result.removeIon(i);
				}
			}
			// log.debug("Filtered " + (element.getIons().size() -
			// result.getIons().size()) + " ions");
			return result;
		} catch(CloneNotSupportedException e) {
			log.warn(e.getLocalizedMessage(), e);
		}
		// TODO: maybe return null to indicate the result is empty because of an
		// error and not because of filtering
		return new ScanMSD();
	}
}
