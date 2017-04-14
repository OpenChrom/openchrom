/*******************************************************************************
 * Copyright (c) 2017 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * whitlow - initial API and implementation
*******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class EventDataListener {
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private EventHandler eventHandler;

	@PostConstruct
	
	/**
	 * Subscribes to events.
	 */
	public void subscribeMe(String topic, String property) {

		if(eventBroker == null) {
			System.out.println("*** EventDataListener line: "+new Throwable().getStackTrace()[0].getLineNumber()+", eventBroker==null in call to EventHandler.subscribeMe("+topic+","+property+");");
		}
		if(eventBroker != null) {
			eventHandler = new EventHandler() {

				@Override
				public void handleEvent(Event event) {

					EventDataHolder.setData(topic, event.getProperty(property));
				}
			};
			eventBroker.subscribe(topic, eventHandler);
		}
	}

	public void unsubscribeMe() {

		if(eventBroker != null && eventHandler != null) {
			eventBroker.unsubscribe(eventHandler);
		}
	}
}
