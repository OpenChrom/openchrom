/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import java.util.function.Consumer;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.comparator.NameReplacementComparator;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;
import net.openchrom.xxd.process.supplier.templates.util.NameReplacementValidator;

public class NameReplacements extends ArrayList<NameReplacement> implements ISettings {

	private static final Logger logger = Logger.getLogger(NameReplacements.class);
	private static final long serialVersionUID = -3447866151656092250L;

	public static final String DESCRIPTION = "Name Replacements";
	public static final String FILE_EXTENSION = ".nrp";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";

	/*
	 * Name | Synonym
	 */
	public static final String EXAMPLE = "1,8-Cineol | Eucalyptol";

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(NameReplacement setting : this) {
			keys.add(setting.getName());
		}
		return keys;
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

	public String extractSetting(NameReplacement setting) {

		List<NameReplacement> settings = new ArrayList<>();
		settings.add(setting);
		return extractSettings(settings);
	}

	public String extractSettings(Collection<NameReplacement> settings) {

		StringBuilder builder = new StringBuilder();
		Iterator<NameReplacement> iterator = settings.iterator();
		while(iterator.hasNext()) {
			NameReplacement setting = iterator.next();
			extractSetting(setting, builder);
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.SEPARATOR_TOKEN);
			}
		}
		return builder.toString().trim();
	}

	public NameReplacement extractSettingInstance(String item) {

		return extract(item);
	}

	public void importItems(File file) {

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				NameReplacement setting = extract(line);
				if(setting != null && !this.contains(setting)) {
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

			List<NameReplacement> settings = new ArrayList<>(this);
			if(PreferenceSupplier.isSortExportTemplate()) {
				Collections.sort(settings, new NameReplacementComparator()); // SORT OK
			}

			for(NameReplacement setting : settings) {
				StringBuilder builder = new StringBuilder();
				extractSetting(setting, builder);
				printWriter.println(builder.toString());
			}
			printWriter.flush();
			success = true;
		} catch(FileNotFoundException e) {
			logger.warn(e);
		}

		return success;
	}

	private NameReplacement extract(String text) {

		NameReplacement setting = null;
		NameReplacementValidator validator = new NameReplacementValidator();

		IStatus status = validator.validate(text);
		if(status.isOK()) {
			setting = validator.getSetting();
		} else {
			logger.warn(status.getMessage());
		}

		return setting;
	}

	private void loadSettings(String items) {

		if(!"".equals(items)) {
			String content = items.replace(AbstractTemplateListUtil.SEPARATOR_TOKEN, "\n");
			content.lines().forEach(new Consumer<String>() {

				@Override
				public void accept(String line) {

					NameReplacement setting = extractSettingInstance(line);
					if(setting != null && !contains(setting)) {
						add(setting);
					}
				}
			});
		}
	}

	private void extractSetting(NameReplacement setting, StringBuilder builder) {

		List<String> entries = new ArrayList<>();
		entries.add(setting.getName());
		entries.add(setting.getSynonym());
		compile(builder, entries);
	}
}