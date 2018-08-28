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

public class DetectorSettings {

	/*
	 * Setting: includeBackground
	 * => false: BV or VB
	 * => true: VV
	 */
	private double startRetentionTime;
	private double stopRetentionTime;
	private boolean includeBackground = false;

	public DetectorSettings(double startRetentionTime, double stopRetentionTime, boolean includeBackground) {
		this.startRetentionTime = startRetentionTime;
		this.stopRetentionTime = stopRetentionTime;
		this.includeBackground = includeBackground;
	}

	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public boolean isIncludeBackground() {

		return includeBackground;
	}
}
