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
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacement;

public class NameReplacementsFilter extends ViewerFilter {

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

		if(element instanceof NameReplacement setting) {

			if(!caseSensitive) {
				searchText = searchText.toLowerCase();
			}

			String name = caseSensitive ? setting.getName() : setting.getName().toLowerCase();
			if(name.contains(searchText)) {
				return true;
			}

			String synonym = caseSensitive ? setting.getSynonym() : setting.getSynonym().toLowerCase();
			if(synonym.contains(searchText)) {
				return true;
			}
		}

		return false;
	}
}
