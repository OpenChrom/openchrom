/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;

public class PeakReviewFilter extends ViewerFilter {

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
		if(element instanceof ReviewSetting) {
			ReviewSetting setting = (ReviewSetting)element;
			String name = setting.getName();
			String cas = setting.getCasNumber();
			String traces = setting.getTraces();
			PeakType detectorType = setting.getDetectorType();
			//
			if(!caseSensitive) {
				searchText = searchText.toLowerCase();
				name = name.toLowerCase();
				cas = cas.toLowerCase();
				traces = traces.toLowerCase();
			}
			//
			if(name.contains(searchText)) {
				return true;
			}
			//
			if(cas.contains(searchText)) {
				return true;
			}
			//
			if(traces.contains(searchText)) {
				return true;
			}
			//
			if(detectorType != null) {
				if(caseSensitive) {
					if(detectorType.name().contains(searchText)) {
						return true;
					}
				} else {
					if(detectorType.name().toLowerCase().contains(searchText)) {
						return true;
					}
				}
			}
		}
		//
		return false;
	}
}
