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
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class DetectorSettings extends ArrayList<DetectorSetting> {

	private static final Logger logger = Logger.getLogger(DetectorSettings.class);
	//
	private static final long serialVersionUID = -4685218696168308093L;
	private PeakDetectorListUtil listUtil = new PeakDetectorListUtil();
	private static final String SEPARATOR_TOKEN = PeakDetectorListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = PeakDetectorListUtil.SEPARATOR_ENTRY;

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		StringBuilder builder = new StringBuilder();
		Iterator<DetectorSetting> iterator = iterator();
		while(iterator.hasNext()) {
			DetectorSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public String extractSettingString(DetectorSetting setting) {

		StringBuilder builder = new StringBuilder();
		extractSetting(setting, builder);
		return builder.toString();
	}

	public DetectorSetting extractSettingInstance(String item) {

		DetectorSetting setting = null;
		//
		if(!"".equals(item)) {
			String[] values = item.split("\\" + SEPARATOR_ENTRY);
			setting = new DetectorSetting();
			setting.setStartRetentionTime(getDouble(values, 0));
			setting.setStopRetentionTime(getDouble(values, 1));
			setting.setDetectorType(getString(values, 2));
		}
		//
		return setting;
	}

	public void importItems(File file) {

		try {
			PeakDetectorValidator validator = new PeakDetectorValidator();
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
			Iterator<DetectorSetting> iterator = iterator();
			while(iterator.hasNext()) {
				StringBuilder builder = new StringBuilder();
				DetectorSetting setting = iterator.next();
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
					DetectorSetting setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(DetectorSetting setting, StringBuilder builder) {

		builder.append(setting.getStartRetentionTime());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getStopRetentionTime());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getDetectorType().toString());
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
