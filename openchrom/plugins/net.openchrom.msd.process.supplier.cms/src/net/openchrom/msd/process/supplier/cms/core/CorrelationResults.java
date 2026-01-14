/*******************************************************************************
 * Copyright (c) 2017, 2026 Walter Whitlock, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

		results = new ArrayList<>();
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
