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

/*
 * this class is used to hold a reference to the most recently generated
 * DecompositionResults object. Other classes can get a copy of of this
 * this reference by calling the static getLatestResults() method. It is used
 * when controls should display valid decomposition results when they
 * are first created and therefore were not able to respond to past events.
 */
public class DecompositionResultsHolder {

	private static DecompositionResults latestResults = null;

	public static DecompositionResults getLatestResults() {

		return DecompositionResultsHolder.latestResults;
	}

	public static void setLatestResults(DecompositionResults lastResults) {

		DecompositionResultsHolder.latestResults = lastResults;
	}
}
