/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class Processor {

	public static final String PROCESSOR_FILE_EXTENSION = ".otc";
	//
	private static Map<String, Pattern> samplePatterns = new HashMap<String, Pattern>();

	public static String getSamplePattern(String fileName) {

		String filePattern = PreferenceSupplier.getFilePattern();
		Pattern pattern = samplePatterns.get(filePattern);
		if(pattern == null) {
			pattern = Pattern.compile(filePattern);
			samplePatterns.put(filePattern, pattern);
		}
		//
		Matcher matcher = pattern.matcher(fileName);
		String samplePattern;
		if(matcher.find()) {
			samplePattern = matcher.group(1);
		} else {
			samplePattern = "no match";
		}
		//
		return samplePattern;
	}
}
