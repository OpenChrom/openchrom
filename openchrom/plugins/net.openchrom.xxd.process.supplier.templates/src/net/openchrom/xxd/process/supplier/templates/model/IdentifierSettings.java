/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.comparator.IdentifierComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierValidator;

public class IdentifierSettings extends HashMap<String, IdentifierSetting> implements ISettings {

	private static final Logger logger = Logger.getLogger(IdentifierSettings.class);
	//
	private static final long serialVersionUID = 260861794433108481L;
	private PeakIdentifierListUtil listUtil = new PeakIdentifierListUtil();

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

		return extractSettings(this.values());
	}

	public String extractSetting(IdentifierSetting setting) {

		List<IdentifierSetting> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<IdentifierSetting> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<IdentifierSetting> iterator = settings.iterator();
		while(iterator.hasNext()) {
			IdentifierSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public IdentifierSetting extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				IdentifierSetting setting = extract(line);
				if(setting != null) {
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
			//
			List<IdentifierSetting> settings = new ArrayList<>(values());
			if(PreferenceSupplier.isSortExportTemplate()) {
				Collections.sort(settings, new IdentifierComparator()); // SORT OK
			}
			//
			for(IdentifierSetting setting : settings) {
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

	private IdentifierSetting extract(String text) {

		IdentifierSetting setting = null;
		PeakIdentifierValidator validator = listUtil.getValidator();
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

	private void loadSettings(String items) {

		if(!"".equals(items)) {
			String[] parsedItems = listUtil.parseString(items);
			if(parsedItems.length > 0) {
				for(String item : parsedItems) {
					IdentifierSetting setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(IdentifierSetting setting, StringBuilder builder) {

		builder.append(getFormattedRetentionTime(setting.getStartRetentionTimeMinutes()));
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(getFormattedRetentionTime(setting.getStopRetentionTimeMinutes()));
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getName());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getCasNumber());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getComments());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getContributor());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getReference());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getTraces());
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
		builder.append(AbstractTemplateListUtil.WHITE_SPACE);
		builder.append(setting.getReferenceIdentifier());
	}
}
