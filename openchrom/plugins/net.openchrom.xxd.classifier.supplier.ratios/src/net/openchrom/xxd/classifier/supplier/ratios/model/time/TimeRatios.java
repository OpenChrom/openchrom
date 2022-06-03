/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.time;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.time.TimeRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.time.TimeRatioValidator;

public class TimeRatios extends ArrayList<TimeRatio> implements IPeakRatios<TimeRatio> {

	private static final Logger logger = Logger.getLogger(TimeRatios.class);
	private static final long serialVersionUID = 3055499236651500754L;
	//
	public static final String DESCRIPTION = "Time Ratios";
	public static final String FILE_EXTENSION = ".tir";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";
	//
	private TimeRatioListUtil listUtil = new TimeRatioListUtil();
	private static final String SEPARATOR_TOKEN = TimeRatioListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = TimeRatioListUtil.SEPARATOR_ENTRY;

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		return extractSettings(this);
	}

	public String extractSettingString(TimeRatio setting) {

		List<TimeRatio> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<TimeRatio> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<TimeRatio> iterator = settings.iterator();
		while(iterator.hasNext()) {
			TimeRatio setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public TimeRatio extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				TimeRatio setting = extract(line);
				if(setting != null && !this.contains(setting)) {
					add(setting);
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
			Iterator<TimeRatio> iterator = iterator();
			while(iterator.hasNext()) {
				StringBuilder builder = new StringBuilder();
				TimeRatio setting = iterator.next();
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

	private TimeRatio extract(String text) {

		TimeRatio setting = null;
		TimeRatioValidator validator = listUtil.getValidator();
		//
		IStatus status = validator.validate(text);
		if(status.isOK()) {
			setting = validator.getSetting();
		} else {
			logger.warn(status.getMessage());
		}
		//
		return setting;
	}

	private void loadSettings(String iems) {

		if(!"".equals(iems)) {
			String[] items = listUtil.parseString(iems);
			if(items.length > 0) {
				for(String item : items) {
					TimeRatio setting = extractSettingInstance(item);
					if(setting != null && !contains(setting)) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(TimeRatio setting, StringBuilder builder) {

		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
		//
		builder.append(setting.getName());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(decimalFormat.format(setting.getExpectedRetentionTimeMinutes()));
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getDeviationWarn());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getDeviationError());
	}
}
