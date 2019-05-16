/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.comparator.DetectorComparator;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class DetectorSettings extends ArrayList<DetectorSetting> implements ISettings {

	private static final Logger logger = Logger.getLogger(DetectorSettings.class);
	//
	private static final long serialVersionUID = -4685218696168308093L;
	private PeakDetectorListUtil listUtil = new PeakDetectorListUtil();

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		return extractSettings(this);
	}

	public String extractSettingString(DetectorSetting setting) {

		List<DetectorSetting> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<DetectorSetting> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<DetectorSetting> iterator = settings.iterator();
		while(iterator.hasNext()) {
			DetectorSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public DetectorSetting extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				DetectorSetting setting = extract(line);
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
			List<DetectorSetting> settings = new ArrayList<>(this);
			Collections.sort(settings, new DetectorComparator());
			for(DetectorSetting setting : settings) {
				StringBuilder builder = new StringBuilder();
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

	private DetectorSetting extract(String text) {

		DetectorSetting setting = null;
		PeakDetectorValidator validator = listUtil.getValidator();
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
					DetectorSetting setting = extractSettingInstance(item);
					if(setting != null && !contains(setting)) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(DetectorSetting setting, StringBuilder builder) {

		builder.append(getFormattedRetentionTime(setting.getStartRetentionTimeMinutes()));
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(getFormattedRetentionTime(setting.getStopRetentionTimeMinutes()));
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getDetectorType());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getTraces());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.isOptimizeRange());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getReferenceIdentifier());
	}
}
