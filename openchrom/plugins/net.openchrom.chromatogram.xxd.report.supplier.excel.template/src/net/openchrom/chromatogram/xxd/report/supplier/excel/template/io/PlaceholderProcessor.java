/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.io;

import java.util.function.Function;

public class PlaceholderProcessor {

	public static final String PLACEHOLDER_START = "{";
	public static final String PLACEHOLDER_STOP = "}";
	//
	private String key = "";
	private String placeholder = "";
	private Function<CellData, String> function = null;

	public PlaceholderProcessor(String key, Function<CellData, String> function) {

		this.key = key;
		this.placeholder = PLACEHOLDER_START + key + PLACEHOLDER_STOP;
		this.function = function;
	}

	public String getKey() {

		return key;
	}

	public String getPlaceholder() {

		return placeholder;
	}

	public void populate(CellData cellData) {

		if(cellData != null) {
			String cellValue = cellData.getCellValue();
			if(cellValue != null) {
				if(cellValue.contains(placeholder)) {
					if(function != null) {
						String cellValueProcessed = function.apply(cellData);
						if(cellValueProcessed != null) {
							cellData.setCellValue(cellValue.replace(placeholder, cellValueProcessed));
						}
					}
				}
			}
		}
	}
}