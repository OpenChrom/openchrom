/*******************************************************************************
 * Copyright (c) 2019, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.qual;

public enum PeakQuality implements ILabel {

	VERY_GOOD("++"), //
	GOOD("+"), //
	ACCEPTABLE("~"), //
	BAD("-"), //
	VERY_BAD("--"), //
	NONE("");

	private String label = "";

	private PeakQuality(String label) {

		this.label = label;
	}

	public String label() {

		return label;
	}
}
