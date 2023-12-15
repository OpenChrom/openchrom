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
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;

public class StandardsAssignerFilter extends ViewerFilter {

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
		if(element instanceof AssignerStandard setting) {
			String name = setting.getName();
			String concentrationUnit = setting.getConcentrationUnit();
			String tracesIdentification = setting.getTracesIdentification();
			//
			if(!caseSensitive) {
				searchText = searchText.toLowerCase();
				name = name.toLowerCase();
				concentrationUnit = concentrationUnit.toLowerCase();
				tracesIdentification = tracesIdentification.toLowerCase();
			}
			//
			if(name.contains(searchText)) {
				return true;
			}
			//
			if(concentrationUnit.contains(searchText)) {
				return true;
			}
			//
			if(tracesIdentification.contains(searchText)) {
				return true;
			}
		}
		//
		return false;
	}
}
