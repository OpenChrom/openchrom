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

import java.util.HashMap;

public class EventDataHolder {

	private static HashMap<String, Object> eventDataMap; // key is topic name string, value is data object for that event
	static {
		eventDataMap = new HashMap<String, Object>();
	}

	public static void setData(String topic, Object data) {

		eventDataMap.put(topic, data);
	}

	public static Object getData(String topic) {

		return eventDataMap.get(topic);
	}
}
