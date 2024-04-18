/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.internal.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.openchrom.xxd.base.model.Suspect;

public class SuspectListFilter extends ViewerFilter {

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
		if(!caseSensitive) {
			searchText = searchText.toLowerCase();
		}
		//
		if(element instanceof Suspect suspect) {
			String name = suspect.getName();
			if(!name.isEmpty() && !name.isBlank()) {
				if(!caseSensitive) {
					name = name.toLowerCase();
				}
				//
				if(name.contains(searchText)) {
					return true;
				}
			}
		}
		//
		return false;
	}
}