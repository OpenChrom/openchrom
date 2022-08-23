/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
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

import net.openchrom.xxd.process.supplier.templates.comparator.ReviewComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.ReviewListUtil;
import net.openchrom.xxd.process.supplier.templates.util.ReviewValidator;

public class ReviewSettings extends ArrayList<ReviewSetting> implements ISettings {

	private static final Logger logger = Logger.getLogger(ReviewSettings.class);
	private static final long serialVersionUID = -6161941038059031059L;
	//
	public static final String DESCRIPTION = "Peak Review Template";
	public static final String FILE_EXTENSION = ".prt";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";
	//
	private ReviewListUtil listUtil = new ReviewListUtil();

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(ReviewSetting reviewSetting : this) {
			keys.add(reviewSetting.getName());
		}
		return keys;
	}

	@Override
	public boolean add(ReviewSetting setting) {

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

	public String extractSetting(ReviewSetting setting) {

		List<ReviewSetting> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<ReviewSetting> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<ReviewSetting> iterator = settings.iterator();
		while(iterator.hasNext()) {
			ReviewSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public ReviewSetting extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				ReviewSetting setting = extract(line);
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
			List<ReviewSetting> settings = new ArrayList<>(this);
			if(PreferenceSupplier.isSortExportTemplate()) {
				Collections.sort(settings, new ReviewComparator()); // SORT OK
			}
			//
			for(ReviewSetting setting : settings) {
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

	private ReviewSetting extract(String text) {

		ReviewSetting setting = null;
		ReviewValidator validator = listUtil.getValidator();
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
					ReviewSetting setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(ReviewSetting setting, StringBuilder builder) {

		List<String> entries = new ArrayList<>();
		//
		entries.add(getFormattedPosition(setting.getPositionStart()));
		entries.add(getFormattedPosition(setting.getPositionStop()));
		entries.add(setting.getName());
		entries.add(setting.getCasNumber());
		entries.add(setting.getTraces());
		entries.add(setting.getPeakType().name());
		entries.add(Boolean.toString(setting.isOptimizeRange()));
		entries.add(setting.getPositionDirective().name());
		//
		compile(builder, entries);
	}
}