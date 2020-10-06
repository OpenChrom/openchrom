/*******************************************************************************
 * Copyright (c) 2020 Matthias Mailänder.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.btmsp.converter.io;

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;

public class EventFilterSample implements EventFilter {

	private String acceptedElement;

	public EventFilterSample() {

		acceptedElement = "sample";
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
