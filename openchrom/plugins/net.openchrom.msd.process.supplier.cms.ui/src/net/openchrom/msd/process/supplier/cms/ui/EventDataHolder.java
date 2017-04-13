/*******************************************************************************
 * Copyright (c) 2017 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui;

import java.util.concurrent.ConcurrentHashMap;

public class EventDataHolder {

	private static final Object NULL = new Object();
	private static ConcurrentHashMap<String, Object> eventDataMap; // key is topic name string, value is data object for that event
	static {
		eventDataMap = new ConcurrentHashMap<String, Object>();
	}

	public static void setData(String topic, Object data) {

		if(null == data) {
			eventDataMap.put(topic, NULL);
		} else {
			eventDataMap.put(topic, data);
		}
	}

	public static Object getData(String topic) {

		Object ob = eventDataMap.get(topic);
		if(NULL == ob) {
			return null;
		} else {
			return ob;
		}
	}
}
