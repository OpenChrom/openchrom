/*******************************************************************************
 * Copyright (c) 2013, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.handlers;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ConverterHandler implements EventHandler {

	@Override
	public void handleEvent(Event event) {

		/*
		 * Do nothing.
		 * It's just to activate the UI plug-in.
		 * Otherwise, the UI serial key dialog wouldn't
		 * be displayed in case, that there is no valid
		 * serial key.
		 * See method to check in Activator ->
		 * ProductValidator.isValidVersion(productPreferences, true, false);
		 */
	}
}
