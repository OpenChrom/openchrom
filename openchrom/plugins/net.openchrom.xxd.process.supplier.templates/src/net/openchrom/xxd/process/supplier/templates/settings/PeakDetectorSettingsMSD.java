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
package net.openchrom.xxd.process.supplier.templates.settings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorMSDSettings;

import net.openchrom.xxd.process.supplier.templates.peaks.DetectorSettings;

public class PeakDetectorSettingsMSD implements IPeakDetectorMSDSettings {

	private List<DetectorSettings> detectorSettings = new ArrayList<>();

	public List<DetectorSettings> getDetectorSettings() {

		return detectorSettings;
	}
}
