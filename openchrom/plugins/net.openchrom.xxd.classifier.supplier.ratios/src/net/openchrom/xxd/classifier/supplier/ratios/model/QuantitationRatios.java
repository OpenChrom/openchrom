/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model;

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

import net.openchrom.xxd.classifier.supplier.ratios.util.QuantitationRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.QuantitationRatioValidator;

public class QuantitationRatios extends ArrayList<QuantitationRatio> {

	private static final Logger logger = Logger.getLogger(QuantitationRatios.class);
	//
	private static final long serialVersionUID = 3055499236651500754L;
	private QuantitationRatioListUtil listUtil = new QuantitationRatioListUtil();
	private static final String SEPARATOR_TOKEN = QuantitationRatioListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = QuantitationRatioListUtil.SEPARATOR_ENTRY;

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		StringBuilder builder = new StringBuilder();
		Iterator<QuantitationRatio> iterator = iterator();
		while(iterator.hasNext()) {
			QuantitationRatio setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public String extractSettingString(QuantitationRatio setting) {

		StringBuilder builder = new StringBuilder();
		extractSetting(setting, builder);
		return builder.toString();
	}

	public QuantitationRatio extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				QuantitationRatio setting = extract(line);
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
			Iterator<QuantitationRatio> iterator = iterator();
			while(iterator.hasNext()) {
				StringBuilder builder = new StringBuilder();
				QuantitationRatio setting = iterator.next();
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

	private QuantitationRatio extract(String text) {

		QuantitationRatio setting = null;
		QuantitationRatioValidator validator = listUtil.getValidator();
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
					QuantitationRatio setting = extractSettingInstance(item);
					if(setting != null && !contains(setting)) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(QuantitationRatio setting, StringBuilder builder) {

		builder.append(setting.getName());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getExpectedContent());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getUnit());
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
