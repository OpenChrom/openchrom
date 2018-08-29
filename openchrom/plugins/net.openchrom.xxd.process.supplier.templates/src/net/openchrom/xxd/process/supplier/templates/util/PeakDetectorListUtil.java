/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeakDetectorListUtil {

	public static final String SEPARATOR_TOKEN = ";";
	public static final String SEPARATOR_ENTRY = "|";

	public String createList(String[] items) {

		List<String> list = getValues(items);
		String values = "";
		for(String value : list) {
			values = values.concat(value + SEPARATOR_TOKEN);
		}
		return values;
	}

	public String[] parseString(String stringList) {

		String[] decodedArray;
		if(stringList.contains(SEPARATOR_TOKEN)) {
			decodedArray = stringList.split(SEPARATOR_TOKEN);
		} else {
			decodedArray = new String[0];
		}
		return decodedArray;
	}

	public List<String> getList(String preferenceEntry) {

		List<String> values = new ArrayList<String>();
		if(preferenceEntry != "") {
			String[] items = parseString(preferenceEntry);
			if(items.length > 0) {
				for(String item : items) {
					values.add(item);
				}
			}
		}
		Collections.sort(values);
		return values;
	}

	private List<String> getValues(String[] items) {

		List<String> values = new ArrayList<String>();
		if(items != null) {
			int size = items.length;
			for(int i = 0; i < size; i++) {
				values.add(items[i]);
			}
		}
		return values;
	}
}
