/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.comparator.IdentifierComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierValidator;

public class IdentifierSettings extends ArrayList<IdentifierSetting> implements ISettings {

	private static final Logger logger = Logger.getLogger(IdentifierSettings.class);
	//
	public static final String DESCRIPTION = "Peak Identifier Template";
	public static final String FILE_EXTENSION = ".pit";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";
	//
	private static final long serialVersionUID = 260861794433108481L;
	private PeakIdentifierListUtil listUtil = new PeakIdentifierListUtil();

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(IdentifierSetting identifierSetting : this) {
			keys.add(identifierSetting.getName());
		}
		return keys;
	}

	@Override
	public boolean add(IdentifierSetting setting) {

		if(setting != null) {
			return super.add(setting);
		}
		return false;
	}

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		return extractSettings(this);
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

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				IdentifierSetting setting = extract(line);
				if(setting != null) {
					add(setting);
				}
			}
		} catch(FileNotFoundException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	public boolean exportItems(File file) {

		boolean success = false;
		try (PrintWriter printWriter = new PrintWriter(file)) {
			//
			List<IdentifierSetting> settings = new ArrayList<>(this);
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
			success = true;
		} catch(FileNotFoundException e) {
			logger.warn(e);
		}
		//
		return success;
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

		List<String> entries = new ArrayList<>();
		//
		entries.add(getFormattedPosition(setting.getPositionStart()));
		entries.add(getFormattedPosition(setting.getPositionStop()));
		entries.add(setting.getName());
		entries.add(setting.getCasNumber());
		entries.add(getFormattedString(setting.getComments()));
		entries.add(getFormattedString(setting.getContributor()));
		entries.add(getFormattedString(setting.getReference()));
		entries.add(setting.getTraces());
		entries.add(getFormattedString(setting.getReferenceIdentifier()));
		entries.add(setting.getPositionDirective().name());
		//
		compile(builder, entries);
	}
}