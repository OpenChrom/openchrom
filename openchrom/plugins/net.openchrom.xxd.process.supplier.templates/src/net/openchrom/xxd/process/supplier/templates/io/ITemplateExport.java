/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.comparator.IonAbundanceComparator;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;

import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;

public interface ITemplateExport {

	default String extractTraces(IPeak peak, int numberTraces) {

		String traces = "";
		if(numberTraces > 0 && (peak instanceof IPeakMSD || peak instanceof IPeakWSD)) {
			if(peak instanceof IPeakMSD) {
				IPeakMSD peakMSD = (IPeakMSD)peak;
				IScan scan = peakMSD.getPeakModel().getPeakMaximum();
				if(scan instanceof IScanMSD) {
					StringBuilder builder = new StringBuilder();
					IonAbundanceComparator comparator = new IonAbundanceComparator(SortOrder.DESC);
					IScanMSD scanMSD = (IScanMSD)scan;
					List<IIon> ions = new ArrayList<>(scanMSD.getIons());
					Collections.sort(ions, comparator); // SORT OK
					Iterator<IIon> iterator = ions.iterator();
					int counter = 1;
					exitloop:
					while(iterator.hasNext()) {
						IIon ion = iterator.next();
						builder.append(Math.round(ion.getIon()));
						counter++;
						//
						if(iterator.hasNext() && counter <= numberTraces) {
							builder.append(AbstractTemplateListUtil.SEPARATOR_TRACE_ITEM);
						} else {
							break exitloop;
						}
					}
					traces = builder.toString();
				}
			}
		}
		return traces;
	}
}
