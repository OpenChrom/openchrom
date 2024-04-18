/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;

import net.openchrom.xxd.base.model.io.SuspectMatricesIO;

public class SuspectMatrices extends ArrayList<SuspectMatrix> {

	private static final Logger logger = Logger.getLogger(SuspectMatrices.class);
	//
	public static final String DESCRIPTION = "Suspect Matrices";
	public static final String FILE_EXTENSION = ".spm";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";
	//
	public static final String WHITE_SPACE = " ";
	public static final String SEPARATOR_TOKEN = ";";
	public static final String SEPARATOR_ENTRY = "|";
	public static final String NEW_LINE_GENERIC = "\n";
	//
	private static final long serialVersionUID = -2079111652770831459L;

	public Set<String> keySet() {

		Set<String> keys = new HashSet<>();
		for(SuspectMatrix setting : this) {
			keys.add(setting.getName());
		}
		//
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

	public String extractSetting(SuspectMatrix setting) {

		return extractSettings(Arrays.asList(setting));
	}

	public String extractSettings(Collection<SuspectMatrix> settings) {

		return SuspectMatricesIO.toJson(settings);
	}

	public void importItems(File file) {

		try {
			loadSettings(Files.readString(file.toPath()));
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	public boolean exportItems(File file) {

		boolean success = false;
		try (PrintWriter printWriter = new PrintWriter(file)) {
			printWriter.write(extractSettings(this));
			printWriter.flush();
			success = true;
		} catch(FileNotFoundException e) {
			logger.warn(e);
		}
		//
		return success;
	}

	private void loadSettings(String items) {

		List<SuspectMatrix> suspectMatrices = SuspectMatricesIO.fromJson(items);
		for(SuspectMatrix suspectMatrix : suspectMatrices) {
			if(suspectMatrix != null && !this.contains(suspectMatrix)) {
				add(suspectMatrix);
			}
		}
	}
}