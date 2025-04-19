/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - API adjustments
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;

import net.openchrom.xxd.process.supplier.templates.comparator.DetectorComparator;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;

public class ProcessDetectorSettings {

	private IProcessingInfo<?> processingInfo;
	private IChromatogram chromatogram;
	private List<DetectorSetting> detectorSettings = new ArrayList<>();

	public ProcessDetectorSettings(IProcessingInfo<?> processingInfo, IChromatogram chromatogram, PeakDetectorSettings peakDetectorSettings) {

		this.processingInfo = processingInfo;
		this.chromatogram = chromatogram;
		this.detectorSettings.addAll(peakDetectorSettings.getDetectorSettingsList());
		//
		if(PreferenceSupplier.isDetectorSettingsSort()) {
			Collections.sort(detectorSettings, new DetectorComparator()); // SORT OK
		}
	}

	public IProcessingInfo<?> getProcessingInfo() {

		return processingInfo;
	}

	public IChromatogram getChromatogram() {

		return chromatogram;
	}

	public List<DetectorSetting> getDetectorSettings() {

		return detectorSettings;
	}
}
