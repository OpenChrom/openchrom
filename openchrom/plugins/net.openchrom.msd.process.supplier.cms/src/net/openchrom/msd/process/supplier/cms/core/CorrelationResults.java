/*******************************************************************************
 * Copyright (c) 2017 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.util.ArrayList;

import org.eclipse.chemclipse.logging.core.Logger;

public class CorrelationResults {

	private static final Logger logger = Logger.getLogger(CorrelationResults.class);
	//
	private ArrayList<CorrelationResult> results = null;
	private boolean hasETimes; // true if all results have a valid ETIMES field
	private boolean usingETimes; // if true, use etimes for x-axis
	private String name;

	CorrelationResults(String nameString) {
		results = new ArrayList<CorrelationResult>();
		name = nameString;
		hasETimes = true;
	}

	public void addResult(CorrelationResult result) {

		if(null != this.results) {
			result.reverseSort();
			results.add(result);
			if(hasETimes) {
				hasETimes = (0 <= result.getTestSpectrum().getEtimes());
			}
		}
	}

	public boolean hasETimes() {

		return hasETimes;
	}

	public boolean isUsingETimes() {

		return usingETimes;
	}

	public String getName() {

		return name;
	}

	public ArrayList<CorrelationResult> getResultsList() {

		return results;
	}

	public void setUsingETimes(boolean usingETimes) {

		this.usingETimes = usingETimes && this.hasETimes;
	}
}
