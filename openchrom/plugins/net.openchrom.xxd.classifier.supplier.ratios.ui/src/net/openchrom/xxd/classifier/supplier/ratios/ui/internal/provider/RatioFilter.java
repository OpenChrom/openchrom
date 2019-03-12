/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.openchrom.xxd.classifier.supplier.ratios.model.IPeakRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;

public class RatioFilter extends ViewerFilter {

	private String searchText;
	private boolean caseSensitive;

	public void setSearchText(String searchText, boolean caseSensitive) {

		this.searchText = searchText;
		this.caseSensitive = caseSensitive;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if(searchText == null || searchText.equals("")) {
			return true;
		}
		//
		if(element instanceof IPeakRatio) {
			IPeakRatio setting = (IPeakRatio)element;
			String name = setting.getName();
			String testCase = (setting instanceof TraceRatio) ? ((TraceRatio)setting).getTestCase() : "";
			//
			if(!caseSensitive) {
				searchText = searchText.toLowerCase();
				name = name.toLowerCase();
				testCase = testCase.toLowerCase();
			}
			//
			if(name.contains(searchText)) {
				return true;
			}
			//
			if(testCase.contains(searchText)) {
				return true;
			}
		}
		//
		return false;
	}
}
