/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierValidator;

public class IdentifierSettings extends HashMap<String, IdentifierSetting> {

	private static final Logger logger = Logger.getLogger(IdentifierSettings.class);
	//
	private static final long serialVersionUID = 260861794433108481L;
	private PeakIdentifierListUtil listUtil = new PeakIdentifierListUtil();
	private static final String SEPARATOR_TOKEN = PeakIdentifierListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = PeakIdentifierListUtil.SEPARATOR_ENTRY;

	public void add(IdentifierSetting setting) {

		if(setting != null) {
			put(setting.getName(), setting);
		}
	}

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		StringBuilder builder = new StringBuilder();
		Iterator<IdentifierSetting> iterator = values().iterator();
		while(iterator.hasNext()) {
			IdentifierSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public String extractSettingString(IdentifierSetting setting) {

		StringBuilder builder = new StringBuilder();
		extractSetting(setting, builder);
		return builder.toString();
	}

	public IdentifierSetting extractSettingInstance(String item) {

		IdentifierSetting setting = null;
		//
		if(!"".equals(item)) {
			String[] values = item.split("\\" + SEPARATOR_ENTRY);
			setting = new IdentifierSetting();
			setting.setStartRetentionTime(getDouble(values, 0));
			setting.setStopRetentionTime(getDouble(values, 1));
			setting.setName(getString(values, 2));
			setting.setCasNumber(getString(values, 3));
			setting.setComments(getString(values, 4));
			setting.setContributor(getString(values, 5));
			setting.setReferenceId(getString(values, 6));
		}
		//
		return setting;
	}

	public void importItems(File file) {

		try {
			PeakIdentifierValidator validator = new PeakIdentifierValidator();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				IStatus status = validator.validate(line);
				if(status.isOK()) {
					add(validator.getSetting());
				} else {
					logger.warn(status.getMessage());
				}
			}
			bufferedReader.close();
		} catch(FileNotFoundException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	public boolean exportItems(File file) {

		try {
			PrintWriter printWriter = new PrintWriter(file);
			Iterator<IdentifierSetting> iterator = values().iterator();
			while(iterator.hasNext()) {
				StringBuilder builder = new StringBuilder();
				IdentifierSetting setting = iterator.next();
				extractSetting(setting, builder);
				printWriter.println(builder.toString());
			}
			printWriter.flush();
			printWriter.close();
			return true;
		} catch(FileNotFoundException e) {
			logger.warn(e);
			return false;
		}
	}

	private void loadSettings(String iems) {

		if(!"".equals(iems)) {
			String[] items = listUtil.parseString(iems);
			if(items.length > 0) {
				for(String item : items) {
					IdentifierSetting setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(IdentifierSetting setting, StringBuilder builder) {

		builder.append(setting.getStartRetentionTime());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getStopRetentionTime());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getName());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getCasNumber());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getComments());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getContributor());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getReferenceId());
	}

	private String getString(String[] values, int index) {

		return (values.length > index) ? values[index].trim() : "";
	}

	private double getDouble(String[] values, int index) {

		double result = 0.0d;
		String value = getString(values, index);
		try {
			result = Double.parseDouble(value);
		} catch(NumberFormatException e) {
			//
		}
		return result;
	}
}
