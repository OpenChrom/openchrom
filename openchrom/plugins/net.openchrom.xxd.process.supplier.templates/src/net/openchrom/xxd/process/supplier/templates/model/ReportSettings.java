/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.comparator.ReportComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.ReportListUtil;
import net.openchrom.xxd.process.supplier.templates.util.ReportValidator;

public class ReportSettings extends ArrayList<ReportSetting> implements ISettings {

	private static final Logger logger = Logger.getLogger(ReportSettings.class);
	private static final long serialVersionUID = -5652667140827481688L;
	//
	private ReportListUtil listUtil = new ReportListUtil();

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(ReportSetting reportSetting : this) {
			keys.add(reportSetting.getName());
		}
		return keys;
	}

	@Override
	public boolean add(ReportSetting setting) {

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

	public String extractSetting(ReportSetting setting) {

		List<ReportSetting> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<ReportSetting> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<ReportSetting> iterator = settings.iterator();
		while(iterator.hasNext()) {
			ReportSetting setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public ReportSetting extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				ReportSetting setting = extract(line);
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
			List<ReportSetting> settings = new ArrayList<>(this);
			if(PreferenceSupplier.isSortExportTemplate()) {
				Collections.sort(settings, new ReportComparator()); // SORT OK
			}
			//
			for(ReportSetting setting : settings) {
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

	private ReportSetting extract(String text) {

		ReportSetting setting = null;
		ReportValidator validator = listUtil.getValidator();
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
					ReportSetting setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(ReportSetting setting, StringBuilder builder) {

		List<String> entries = new ArrayList<>();
		//
		entries.add(getFormattedPosition(setting.getPositionStart()));
		entries.add(getFormattedPosition(setting.getPositionStop()));
		entries.add(setting.getName());
		entries.add(setting.getCasNumber());
		entries.add(setting.getReportStrategy().name());
		entries.add(setting.getPositionDirective().name());
		//
		compile(builder, entries);
	}
}