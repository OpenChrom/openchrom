/*******************************************************************************
 * Copyright (c) 2020, 2025 Matthias Mailänder.
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

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;

public class EventFilterMainSpectrum implements EventFilter {

	private String acceptedElement;

	public EventFilterMainSpectrum() {

		acceptedElement = "mainSpectrum";
	}

	@Override
	public boolean accept(XMLEvent xmlEvent) {

		boolean result = false;
		String element;
		if(xmlEvent.isStartElement()) {
			element = xmlEvent.asStartElement().getName().getLocalPart();
			if(element.equals(acceptedElement)) {
				result = true;
			}
		}
		return result;
	}
}
