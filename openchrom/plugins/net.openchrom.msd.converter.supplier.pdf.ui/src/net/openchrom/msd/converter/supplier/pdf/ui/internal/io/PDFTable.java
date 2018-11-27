/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.internal.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PDFTable {

	private List<String> titles = new ArrayList<>();
	private List<Float> bounds = new ArrayList<>();
	private List<List<String>> rows = new ArrayList<>();

	public void addColumn(String title, float width) {

		titles.add(title);
		bounds.add(width);
	}

	public void addRow(List<String> row) {

		rows.add(row);
	}

	public List<String> getTitles() {

		return Collections.unmodifiableList(titles);
	}

	public List<Float> getBounds() {

		return Collections.unmodifiableList(bounds);
	}

	public List<List<String>> getRows() {

		return rows;
	}

	public boolean isValid() {

		if(titles.size() != bounds.size()) {
			return false;
		}
		//
		for(List<String> row : rows) {
			if(row.size() != bounds.size()) {
				return false;
			}
		}
		//
		return true;
	}
}
