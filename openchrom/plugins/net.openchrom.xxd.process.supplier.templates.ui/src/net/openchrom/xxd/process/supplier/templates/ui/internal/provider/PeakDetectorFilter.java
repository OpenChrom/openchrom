/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class PeakDetectorFilter extends ViewerFilter {

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
		if(element instanceof DetectorSetting setting) {
			PeakType detectorType = setting.getPeakType();
			String traces = setting.getTraces();
			String referenceIdenfifier = setting.getReferenceIdentifier();
			String name = setting.getName();
			//
			if(!caseSensitive) {
				searchText = searchText.toLowerCase();
				traces = traces.toLowerCase();
				referenceIdenfifier = referenceIdenfifier.toLowerCase();
				name = name.toLowerCase();
			}
			//
			if(traces.contains(searchText)) {
				return true;
			}
			//
			if(referenceIdenfifier.contains(searchText)) {
				return true;
			}
			//
			if(name.contains(searchText)) {
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
