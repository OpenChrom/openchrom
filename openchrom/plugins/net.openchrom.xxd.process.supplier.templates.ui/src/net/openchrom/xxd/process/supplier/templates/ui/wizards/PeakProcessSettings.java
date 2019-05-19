/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.List;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;

@SuppressWarnings("rawtypes")
public class PeakProcessSettings {

	private IProcessingInfo processingInfo;
	private IChromatogramSelection chromatogramSelection;
	private PeakDetectorSettings peakDetectorSettings;
	//
	private int index;
	//

	public PeakProcessSettings(IProcessingInfo processingInfo, IChromatogramSelection chromatogramSelection, PeakDetectorSettings peakDetectorSettings) {
		this.processingInfo = processingInfo;
		this.chromatogramSelection = chromatogramSelection;
		this.peakDetectorSettings = peakDetectorSettings;
	}

	public IProcessingInfo getProcessingInfo() {

		return processingInfo;
	}

	public IChromatogramSelection getChromatogramSelection() {

		return chromatogramSelection;
	}

	public PeakDetectorSettings getPeakDetectorSettings() {

		return peakDetectorSettings;
	}

	public void decreaseSelection() {

		if(index > 0) {
			index--;
		}
	}

	public void increaseSelection() {

		if(index < peakDetectorSettings.getDetectorSettings().size()) {
			index++;
		}
	}

	public DetectorSetting getSelectedDetectorSetting() {

		List<DetectorSetting> settings = peakDetectorSettings.getDetectorSettings();
		if(index >= 0 && index < settings.size()) {
			return settings.get(index);
		} else {
			return null;
		}
	}
}
