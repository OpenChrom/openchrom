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
package net.openchrom.xxd.classifier.supplier.ratios.model.quant;

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

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.quant.QuantRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.quant.QuantRatioValidator;

public class QuantRatios extends ArrayList<QuantRatio> implements IPeakRatios<QuantRatio> {

	private static final Logger logger = Logger.getLogger(QuantRatios.class);
	//
	private static final long serialVersionUID = 3055499236651500754L;
	private QuantRatioListUtil listUtil = new QuantRatioListUtil();
	private static final String SEPARATOR_TOKEN = QuantRatioListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = QuantRatioListUtil.SEPARATOR_ENTRY;

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		StringBuilder builder = new StringBuilder();
		Iterator<QuantRatio> iterator = iterator();
		while(iterator.hasNext()) {
			QuantRatio setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public String extractSettingString(QuantRatio setting) {

		StringBuilder builder = new StringBuilder();
		extractSetting(setting, builder);
		return builder.toString();
	}

	public QuantRatio extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				QuantRatio setting = extract(line);
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
			Iterator<QuantRatio> iterator = iterator();
			while(iterator.hasNext()) {
				StringBuilder builder = new StringBuilder();
				QuantRatio setting = iterator.next();
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

	private QuantRatio extract(String text) {

		QuantRatio setting = null;
		QuantRatioValidator validator = listUtil.getValidator();
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
					QuantRatio setting = extractSettingInstance(item);
					if(setting != null && !contains(setting)) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(QuantRatio setting, StringBuilder builder) {

		builder.append(setting.getName());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getQuantitationName());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getExpectedConcentration());
		builder.append(" ");
		builder.append(SEPARATOR_ENTRY);
		builder.append(" ");
		builder.append(setting.getConcentrationUnit());
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
