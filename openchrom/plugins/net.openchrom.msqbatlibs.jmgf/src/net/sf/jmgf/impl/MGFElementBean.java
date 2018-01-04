/*******************************************************************************
 * Copyright (c) 2016, 2018 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;

public class MGFElementBean implements MGFElement {

	public final static short MS_LEVEL_UNKNOWN = 0;
	private Map<String, String> tags = new LinkedHashMap<>();
	private List<Peak> peaks = new ArrayList<>();
	private short msLevel = MS_LEVEL_UNKNOWN;

	public MGFElementBean() {
	}

	@Override
	public short getMSLevel() {

		return msLevel;
	}

	public synchronized void addTags(String identifier, String value) {

		tags.put(identifier, value);
	}

	@Override
	public synchronized List<Peak> getPeaks() {

		return peaks;
	}

	@Override
	public synchronized String getTag(final String ident) {

		for(final Entry<String, String> e : tags.entrySet()) {
			if(e.getKey().toUpperCase().equals(ident)) {
				return e.getValue();
			}
		}
		return TAG_NA;
	}

	@Override
	public synchronized Map<String, String> getTags() {

		return tags;
	}

	@Override
	public synchronized String getTag(final Identifier element) {

		return getTag(element.toString());
	}

	@Override
	public synchronized String getTitle() {

		return getTag(MGFElement.Identifier.TITLE);
	}

	public void setMSLevel(short msLevel) {

		this.msLevel = msLevel;
	}

	public synchronized MGFElementBean setPeaks(List<Peak> peaks) {

		this.peaks = peaks;
		return this;
	}

	public synchronized void setTags(final Map<String, String> tags) {

		this.tags = tags;
	}

	@Override
	public String toString() {

		return getTitle();
	}
}
