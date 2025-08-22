/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.util;

import org.eclipse.chemclipse.model.core.PeakType;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSettings;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public class PeakDetectorListUtil extends AbstractTemplateListUtil<PeakDetectorValidator> {

	public static final String EXAMPLE_SINGLE = createDemoSetting();
	public static final String EXAMPLE_MULTIPLE = "10.52 | 10.63 | VV | 103, 104, 108-110 | true | Reference | Identification; 10.71 | 10.76 | BB | 105, 106 | false | ";

	public PeakDetectorListUtil() {

		super(new PeakDetectorValidator());
	}

	private static String createDemoSetting() {

		DetectorSetting detectorSetting = new DetectorSetting();
		detectorSetting.setPositionStart(10.52d);
		detectorSetting.setPositionStop(10.63d);
		detectorSetting.setPeakType(PeakType.VV);
		detectorSetting.setTraces("103, 104, 108-110");
		detectorSetting.setOptimizeRange(true);
		detectorSetting.setReferenceIdentifier("");
		detectorSetting.setName("Styrene");
		detectorSetting.setPositionDirective(PositionDirective.RETENTION_TIME_MIN);

		DetectorSettings detectorSettings = new DetectorSettings();
		return detectorSettings.extractSettingString(detectorSetting);
	}
}