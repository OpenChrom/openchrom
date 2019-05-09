/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.settings.OperatingSystemUtils;
import org.eclipse.core.runtime.IStatus;

public abstract class AbstractTemplateListUtil<T extends ITemplateValidator> implements ITemplateListUtil<T> {

	private static final Logger logger = Logger.getLogger(AbstractTemplateListUtil.class);
	//
	public static final String SEPARATOR_TOKEN = ";";
	public static final String SEPARATOR_ENTRY = "|";
	//
	public static final String SEPARATOR_TRACE_ITEM = ",";
	public static final String SEPARATOR_TRACE_RANGE = "-";
	public static final int TRACE_ERROR = -1;
	//
	private T validator;

	public AbstractTemplateListUtil(T validator) {
		this.validator = validator;
	}

	@Override
	public T getValidator() {

		return validator;
	}

	@Override
	public String[] parseString(String stringList) {

		String lineDelimiterSpecific = OperatingSystemUtils.getLineDelimiter();
		String lineDelimiterGeneric = "\n";
		//
		String[] decodedArray;
		if(stringList.contains(SEPARATOR_TOKEN)) {
			decodedArray = stringList.split(SEPARATOR_TOKEN);
		} else if(stringList.contains(lineDelimiterSpecific)) {
			decodedArray = stringList.split(lineDelimiterSpecific);
		} else if(stringList.contains(lineDelimiterGeneric)) {
			decodedArray = stringList.split(lineDelimiterGeneric);
		} else {
			decodedArray = new String[1];
			decodedArray[0] = stringList;
		}
		return decodedArray;
	}

	@Override
	public String createList(String[] items) {

		List<String> list = getValues(items);
		String values = "";
		for(String value : list) {
			values = values.concat(value + SEPARATOR_TOKEN);
		}
		return values;
	}

	@Override
	public List<String> importItems(File file) {

		List<String> items = new ArrayList<>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String item;
			while((item = bufferedReader.readLine()) != null) {
				IStatus status = validator.validate(item);
				if(status.isOK()) {
					items.add(item);
				}
			}
			bufferedReader.close();
		} catch(FileNotFoundException e1) {
			logger.warn(e1);
		} catch(IOException e1) {
			logger.warn(e1);
		}
		//
		return items;
	}

	@Override
	public void exportItems(File file, String[] items) {

		try {
			PrintWriter printWriter = new PrintWriter(file);
			for(String item : items) {
				printWriter.println(item);
			}
			printWriter.flush();
			printWriter.close();
		} catch(FileNotFoundException e1) {
			logger.warn(e1);
		}
	}

	@Override
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

	@Override
	public Set<Integer> extractTraces(String traces) {

		Set<Integer> traceSet = new HashSet<>();
		String[] values = traces.split(SEPARATOR_TRACE_ITEM);
		for(String value : values) {
			if(value.contains(SEPARATOR_TRACE_RANGE)) {
				String[] parts = value.split(SEPARATOR_TRACE_RANGE);
				if(parts.length == 2) {
					int startTrace = validator.getTrace(parts[0]);
					int stopTrace = validator.getTrace(parts[1]);
					if(startTrace != TRACE_ERROR && stopTrace != TRACE_ERROR) {
						if(startTrace <= stopTrace) {
							for(int trace = startTrace; trace <= stopTrace; trace++) {
								traceSet.add(trace);
							}
						}
					}
				}
			} else {
				int trace = validator.getTrace(value);
				if(trace != TRACE_ERROR) {
					traceSet.add(trace);
				}
			}
		}
		return traceSet;
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
