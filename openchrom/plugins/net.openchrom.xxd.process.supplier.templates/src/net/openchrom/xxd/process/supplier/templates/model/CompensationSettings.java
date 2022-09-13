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

import net.openchrom.xxd.process.supplier.templates.comparator.CompensationComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.CompensationQuantListUtil;
import net.openchrom.xxd.process.supplier.templates.util.CompensationQuantValidator;

public class CompensationSettings extends ArrayList<CompensationSetting> implements ISettings {

	private static final long serialVersionUID = -1566032312360942165L;
	//
	public static final String DESCRIPTION = "Peak Compensation Quantifier";
	public static final String FILE_EXTENSION = ".pcq";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";
	//
	private static final Logger logger = Logger.getLogger(CompensationSettings.class);
	//
	private CompensationQuantListUtil listUtil = new CompensationQuantListUtil();

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(CompensationSetting setting : this) {
			keys.add(setting.getName());
		}
		return keys;
	}

	@Override
	public boolean add(CompensationSetting setting) {

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

	public String extractSetting(CompensationSetting setting) {

		List<CompensationSetting> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<CompensationSetting> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<CompensationSetting> iterator = settings.iterator();
		while(iterator.hasNext()) {
			CompensationSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public CompensationSetting extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				CompensationSetting setting = extract(line);
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
			List<CompensationSetting> settings = new ArrayList<>(this);
			if(PreferenceSupplier.isSortExportTemplate()) {
				Collections.sort(settings, new CompensationComparator()); // SORT OK
			}
			//
			for(CompensationSetting setting : settings) {
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

	private CompensationSetting extract(String text) {

		CompensationSetting setting = null;
		CompensationQuantValidator validator = listUtil.getValidator();
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
					CompensationSetting setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(CompensationSetting setting, StringBuilder builder) {

		List<String> entries = new ArrayList<>();
		//
		entries.add(setting.getName());
		entries.add(setting.getInternalStandard());
		entries.add(Double.toString(setting.getExpectedConcentration()));
		entries.add(setting.getConcentrationUnit());
		entries.add(Boolean.toString(setting.isAdjustQuantitationEntry()));
		entries.add(setting.getTargetUnit());
		//
		compile(builder, entries);
	}
}