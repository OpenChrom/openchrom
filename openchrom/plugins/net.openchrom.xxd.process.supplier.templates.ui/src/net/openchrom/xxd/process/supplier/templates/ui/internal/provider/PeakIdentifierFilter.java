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

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class PeakIdentifierFilter extends ViewerFilter {

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
		if(element instanceof IdentifierSetting setting) {
			String name = setting.getName();
			String cas = setting.getCasNumber();
			String comments = setting.getComments();
			String contributor = setting.getContributor();
			String reference = setting.getReference();
			String traces = setting.getTraces();
			String referenceIdentifier = setting.getReferenceIdentifier();
			//
			if(!caseSensitive) {
				searchText = searchText.toLowerCase();
				name = name.toLowerCase();
				cas = cas.toLowerCase();
				comments = comments.toLowerCase();
				contributor = contributor.toLowerCase();
				reference = reference.toLowerCase();
				traces = traces.toLowerCase();
				referenceIdentifier = referenceIdentifier.toLowerCase();
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
			if(comments.contains(searchText)) {
				return true;
			}
			//
			if(contributor.contains(searchText)) {
				return true;
			}
			//
			if(reference.contains(searchText)) {
				return true;
			}
			//
			if(traces.contains(searchText)) {
				return true;
			}
			//
			if(referenceIdentifier.contains(searchText)) {
				return true;
			}
		}
		//
		return false;
	}
}
