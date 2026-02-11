/*******************************************************************************
 * Copyright (c) 2009, 2026 Tasktop Technologies, Polarion Software and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

/**
 * @author David Green
 * @author Igor Burilo
 */
public class Overview {

	protected String summary;

	/**
	 * A description providing detailed information about the item. Newlines can be used to format the text into
	 * multiple paragraphs if necessary. Text must fit into an area 320x240, otherwise it will be truncated in the UI.
	 * More lengthy descriptions can be provided on a web page if required, see @url.
	 */
	public String getSummary() {

		return summary;
	}

	public void setSummary(String summary) {

		this.summary = summary;
	}

}
