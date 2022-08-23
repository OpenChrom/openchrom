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

import net.openchrom.xxd.process.supplier.templates.comparator.StandardComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerListUtil;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerValidator;

public class AssignerStandards extends ArrayList<AssignerStandard> implements ISettings {

	private static final Logger logger = Logger.getLogger(AssignerStandards.class);
	//
	private static final long serialVersionUID = -9211939853837711565L;
	private StandardsAssignerListUtil listUtil = new StandardsAssignerListUtil();

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(AssignerStandard setting : this) {
			keys.add(setting.getName());
		}
		return keys;
	}

	@Override
	public boolean add(AssignerStandard setting) {

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

	public String extractSetting(AssignerStandard setting) {

		List<AssignerStandard> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<AssignerStandard> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<AssignerStandard> iterator = settings.iterator();
		while(iterator.hasNext()) {
			AssignerStandard setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public AssignerStandard extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				AssignerStandard setting = extract(line);
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
			List<AssignerStandard> settings = new ArrayList<>(this);
			if(PreferenceSupplier.isSortExportTemplate()) {
				Collections.sort(settings, new StandardComparator()); // SORT OK
			}
			//
			for(AssignerStandard setting : settings) {
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

	private AssignerStandard extract(String text) {

		AssignerStandard setting = null;
		StandardsAssignerValidator validator = listUtil.getValidator();
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
					AssignerStandard setting = extractSettingInstance(item);
					if(setting != null) {
						add(setting);
					}
				}
			}
		}
	}

	private void extractSetting(AssignerStandard setting, StringBuilder builder) {

		List<String> entries = new ArrayList<>();
		//
		entries.add(getFormattedPosition(setting.getPositionStart()));
		entries.add(getFormattedPosition(setting.getPositionStop()));
		entries.add(setting.getName());
		entries.add(Double.toString(setting.getConcentration()));
		entries.add(setting.getConcentrationUnit());
		entries.add(Double.toString(setting.getResponseFactor()));
		entries.add(setting.getTracesIdentification());
		entries.add(setting.getPositionDirective().name());
		//
		compile(builder, entries);
	}
}