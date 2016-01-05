/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
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

	private Map<String, String> elements = new LinkedHashMap<String, String>();
	private List<Peak> peaks;

	public MGFElementBean() {
	}

	public synchronized void addElement(String identifier, String value) {

		elements.put(identifier, value);
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof MGFElement)) {
			return false;
		}
		MGFElementBean other = (MGFElementBean)obj;
		if(elements == null) {
			if(other.elements != null) {
				return false;
			}
		} else if(!elements.equals(other.elements)) {
			return false;
		}
		if(peaks == null) {
			if(other.peaks != null) {
				return false;
			}
		} else if(!peaks.equals(other.peaks)) {
			return false;
		}
		return true;
	}

	@Override
	public synchronized String getElement(final Identifier element) {

		return getElement(element.toString());
	}

	@Override
	public synchronized String getElement(final String ident) {

		for(final Entry<String, String> e : elements.entrySet()) {
			if(e.getKey().toUpperCase().equals(ident)) {
				return e.getValue();
			}
		}
		return ELEMENT_NA;
	}

	@Override
	public synchronized Map<String, String> getElements() {

		return elements;
	}

	@Override
	public synchronized List<Peak> getPeaks() {

		return peaks;
	}

	@Override
	public String getTitle() {

		return getElement(MGFElement.Identifier.TITLE);
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((peaks == null) ? 0 : peaks.hashCode());
		return result;
	}

	public synchronized void setElements(final Map<String, String> elements) {

		this.elements = elements;
	}

	public synchronized void setPeaks(final List<Peak> peaks) {

		this.peaks = peaks;
	}

	@Override
	public String toString() {

		return getTitle();
	}
}
