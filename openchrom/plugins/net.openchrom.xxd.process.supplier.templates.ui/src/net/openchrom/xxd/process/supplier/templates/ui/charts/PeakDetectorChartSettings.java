/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.charts;

import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.PeakChartSettings;

public class PeakDetectorChartSettings extends PeakChartSettings {

	private int deltaRetentionTimeLeft = 0;
	private int deltaRetentionTimeRight = 0;
	private boolean replacePeak = false;
	private int replacePeakDelta = 5000; // 5 Seconds

	public int getDeltaRetentionTimeLeft() {

		return deltaRetentionTimeLeft;
	}

	public void setDeltaRetentionTimeLeft(int deltaRetentionTimeLeft) {

		this.deltaRetentionTimeLeft = deltaRetentionTimeLeft;
	}

	public int getDeltaRetentionTimeRight() {

		return deltaRetentionTimeRight;
	}

	public void setDeltaRetentionTimeRight(int deltaRetentionTimeRight) {

		this.deltaRetentionTimeRight = deltaRetentionTimeRight;
	}

	public boolean isReplacePeak() {

		return replacePeak;
	}

	public void setReplacePeak(boolean replacePeak) {

		this.replacePeak = replacePeak;
	}

	public int getReplacePeakDelta() {

		return replacePeakDelta;
	}

	public void setReplacePeakDelta(int replacePeakDelta) {

		this.replacePeakDelta = replacePeakDelta;
	}
}