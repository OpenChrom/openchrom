/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jmgf.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;

public class MGFElementBean implements MGFElement {

	private List<Peak> peaks;
	private Map<String, String> elements = new LinkedHashMap<String, String>();

	public MGFElementBean() {

	}

	
	public String getElement(final Identifier element) {

		return getElement(element.toString());
	}

	
	public String getElement(final String ident) {

		for(final Entry<String, String> e : elements.entrySet()) {
			if(e.getKey().toUpperCase().equals(ident)) {
				return e.getValue();
			}
		}
		return ELEMENT_NA;
	}

	public synchronized Map<String, String> getElements() {

		return elements;
	}

	
	public synchronized List<Peak> getPeaks() {

		return peaks;
	}

	
	public String getTitle() {

		return getElement(MGFElement.Identifier.TITLE);
	}

	public synchronized void setElements(final Map<String, String> elements) {

		this.elements = elements;
	}

	public synchronized void setPeaks(final List<Peak> peaks) {

		this.peaks = peaks;
	}

	
	public String toString() {

		return getTitle();
	}
}
