/*******************************************************************************
 * Copyright (c) 2008, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings;

public interface IProcessSettings {

	float getMinSignalToNoiseRatio();

	void setMinSignalToNoiseRatio(float minSignalToNoiseRatio);

	float getMinLeading();

	void setMinLeading(float minLeading);

	float getMaxLeading();

	void setMaxLeading(float maxLeading);

	float getMinTailing();

	void setMinTailing(float minTailing);

	float getMaxTailing();

	void setMaxTailing(float maxTailing);

	ModelPeakOption getModelPeakOption();

	void setModelPeakOption(ModelPeakOption modelPeakOption);
}
