/*******************************************************************************
 * Copyright (c) 2017, 2021 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.util.ArrayList;

public class CorrelationResults {

	private ArrayList<CorrelationResult> results = null;
	private String name;

	CorrelationResults(String nameString) {

		results = new ArrayList<CorrelationResult>();
		name = nameString;
	}

	public void addCorrelationResult(CorrelationResult result) {

		if(null != this.results) {
			result.reverseSort();
			results.add(result);
		}
	}

	public String getName() {

		return name;
	}

	public ArrayList<CorrelationResult> getResultsList() {

		return results;
	}
}
