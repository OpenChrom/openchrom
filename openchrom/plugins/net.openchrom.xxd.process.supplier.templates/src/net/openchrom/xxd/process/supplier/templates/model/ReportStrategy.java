/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

public enum ReportStrategy {
	ALL("All"), //
	BEST_MATCH("Best Match"), //
	LARGEST_AREA("Largest Area"), //
	SMALLEST_AREA("Smallest Area");

	private String description = "";

	private ReportStrategy(String description) {

		this.description = description;
	}

	public String getDescription() {

		return description;
	}
}
