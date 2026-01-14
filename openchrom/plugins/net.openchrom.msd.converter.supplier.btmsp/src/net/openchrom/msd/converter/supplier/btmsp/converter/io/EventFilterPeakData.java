/*******************************************************************************
 * Copyright (c) 2020, 2026 Matthias Mailänder.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.btmsp.converter.io;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;

public class EventFilterPeakData implements EventFilter {

	private List<String> acceptedElements;

	public EventFilterPeakData() {

		acceptedElements = new ArrayList<>();
		// TODO: actually follow this hierarchy
		acceptedElements.add("mainSpectrumPeaklist");
		acceptedElements.add("peaks");
		acceptedElements.add("peak");
	}

	@Override
	public boolean accept(XMLEvent xmlEvent) {

		boolean result = false;
		String element;
		if(xmlEvent.isStartElement()) {
			element = xmlEvent.asStartElement().getName().getLocalPart();
			exitloop:
			for(String acceptedElement : acceptedElements) {
				if(element.equals(acceptedElement)) {
					result = true;
					break exitloop;
				}
			}
		}
		return result;
	}
}
