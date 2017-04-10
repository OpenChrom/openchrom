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

import org.eclipse.chemclipse.msd.model.core.IScanMSD;

/*
 * this class is used to hold a reference to the most recently selected
 * IScanMSD object. Other classes can get a copy of of this
 * this reference by calling the static getLatestResults() method. It is used
 * when controls should display valid results when they
 * are first created and therefore were not able to respond to past events.
 */
public class MassSpectraHolder {

	private static IScanMSD latestResults = null;

	public static IScanMSD getLatestResults() {

		return latestResults;
	}

	public static void setLatestResults(IScanMSD latestResults) {

		MassSpectraHolder.latestResults = latestResults;
	}
}
