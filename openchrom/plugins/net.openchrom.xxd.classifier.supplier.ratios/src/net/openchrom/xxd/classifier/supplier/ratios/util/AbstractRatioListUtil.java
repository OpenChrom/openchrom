/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.settings.OperatingSystemUtils;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

public abstract class AbstractRatioListUtil<T extends IValidator<Object>> implements IRatioListUtil<T> {

	private static final Logger logger = Logger.getLogger(AbstractRatioListUtil.class);
	//
	public static final String SEPARATOR_TOKEN = ";";
	public static final String SEPARATOR_ENTRY = "|";
	//
	public static final String SEPARATOR_TRACE_ITEM = ",";
	public static final String SEPARATOR_TRACE_RANGE = "-";
	public static final int TRACE_ERROR = -1;
	//
	private T validator;

	protected AbstractRatioListUtil(T validator) {

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

		List<String> list = Arrays.asList(items);
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
		} catch(FileNotFoundException e) {
			logger.warn(e);
		}
	}

	@Override
	public List<String> getList(String preferenceEntry) {

		List<String> values = new ArrayList<>();
		if(!"".equals(preferenceEntry)) {
			String[] items = parseString(preferenceEntry);
			values = Arrays.asList(items);
		}
		Collections.sort(values);
		return values;
	}
}
