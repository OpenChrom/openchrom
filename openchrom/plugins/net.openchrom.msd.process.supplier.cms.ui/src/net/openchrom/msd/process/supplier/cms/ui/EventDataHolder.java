/*******************************************************************************
 * Copyright (c) 2017, 2020 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - adjustment event process
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class EventDataHolder {

	private static ConcurrentHashMap<String, Object> eventDataMap; // key is topic name string, value is data object for that event
	private static IEventBroker eventBroker;
	//
	static {
		eventDataMap = new ConcurrentHashMap<String, Object>();
		eventBroker = PlatformUI.getWorkbench().getService(IEventBroker.class);
	}

	public static void addSubscriber(String topic) {

		if(eventBroker instanceof IEventBroker) {
			eventBroker.subscribe(topic, eventHandler);
		}
	}

	private static EventHandler eventHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {

			setData(event.getTopic(), event.getProperty(IEventBroker.DATA));
		}
	};

	private static void setData(String topic, Object data) {

		if(null == data) {
			eventDataMap.remove(topic);
		} else {
			eventDataMap.put(topic, data);
		}
	}

	public static Object getData(String topic) {

		return eventDataMap.get(topic);
	}

	public static Object getData(String topic, String property) {

		Object ob = getData(topic);
		if(ob instanceof Map<?, ?>) {
			return ((Map<String, Object>)ob).get(property);
		} else if(ob instanceof Dictionary<?, ?>) {
			return ((Dictionary<String, Object>)ob).get(property);
		} else {
			return ob;
		}
	}
}
