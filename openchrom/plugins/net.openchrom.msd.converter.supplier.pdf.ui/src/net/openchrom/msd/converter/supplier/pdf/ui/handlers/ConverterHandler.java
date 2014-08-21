/*******************************************************************************
 * Copyright (c) 2013, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
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
