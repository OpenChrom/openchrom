/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
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
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.settings.OperatingSystemUtils;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public abstract class AbstractTemplateListUtil<T extends ITemplateValidator> implements ITemplateListUtil<T> {

	private static final Logger logger = Logger.getLogger(AbstractTemplateListUtil.class);
	//
	public static final String WHITE_SPACE = " ";
	public static final String SEPARATOR_TOKEN = ";";
	public static final String SEPARATOR_ENTRY = "|";
	//
	public static final String SEPARATOR_TRACE_ITEM = ",";
	public static final String SEPARATOR_TRACE_RANGE = "-";
	public static final int TRACE_ERROR = -1;
	//
	public static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
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
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String item;
			while((item = bufferedReader.readLine()) != null) {
				IStatus status = validator.validate(item);
				if(status.isOK()) {
					items.add(item);
				}
			}
		} catch(FileNotFoundException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		}
		//
		return items;
	}

	@Override
	public void exportItems(File file, String[] items) {

		try (PrintWriter printWriter = new PrintWriter(file)) {
			for(String item : items) {
				printWriter.println(item);
			}
			printWriter.flush();
		} catch(FileNotFoundException e1) {
			logger.warn(e1);
		}
	}

	@Override
	public List<String> getList(String preferenceEntry) {

		List<String> values = new ArrayList<>();
		if(!"".equals(preferenceEntry)) {
			String[] items = parseString(preferenceEntry);
			if(items.length > 0) {
				for(String item : items) {
					values.add(item);
				}
			}
		}
		//
		if(PreferenceSupplier.isSortImportTemplate()) {
			Collections.sort(values); // SORT OK
		}
		//
		return values;
	}

	@Override
	public Set<Integer> extractTraces(String traces) {

		return validator.extractTraces(traces);
	}

	private List<String> getValues(String[] items) {

		List<String> values = new ArrayList<>();
		if(items != null) {
			int size = items.length;
			for(int i = 0; i < size; i++) {
				values.add(items[i]);
			}
		}
		return values;
	}
}