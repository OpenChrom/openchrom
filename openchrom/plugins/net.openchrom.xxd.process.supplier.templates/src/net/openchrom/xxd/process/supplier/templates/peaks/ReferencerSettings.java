/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

public class ReferencerSettings {

	private double startRetentionTime;
	private double stopRetentionTime;
	private String name = "";

	public ReferencerSettings(double startRetentionTime, double stopRetentionTime, String name) {
		this.startRetentionTime = startRetentionTime;
		this.stopRetentionTime = stopRetentionTime;
		this.name = name;
	}

	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public String getName() {

		return name;
	}
}
