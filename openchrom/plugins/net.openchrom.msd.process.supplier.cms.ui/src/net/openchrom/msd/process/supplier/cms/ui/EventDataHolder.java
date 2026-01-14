/*******************************************************************************
 * Copyright (c) 2017, 2025 Walter Whitlock.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	static {
		eventDataMap = new ConcurrentHashMap<>();
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

	@SuppressWarnings("unchecked")
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
