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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

public abstract class AbstractListUtil<T extends IValidator> implements IListUtil<T> {

	private static final Logger logger = Logger.getLogger(AbstractListUtil.class);
	//
	public static final String SEPARATOR_TOKEN = ";";
	public static final String SEPARATOR_ENTRY = "|";
	//
	private T validator;

	public AbstractListUtil(T validator) {
		this.validator = validator;
	}

	@Override
	public T getValidator() {

		return validator;
	}

	@Override
	public String[] parseString(String stringList) {

		String[] decodedArray;
		if(stringList.contains(SEPARATOR_TOKEN)) {
			decodedArray = stringList.split(SEPARATOR_TOKEN);
		} else {
			decodedArray = new String[0];
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
