/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.processor.supplier.tracecompare.model;

import java.util.Map;

import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.TrackModel_v1000;

public interface ISampleModel {

	String getSampleGroup();

	void setSampleGroup(String sampleGroup);

	String getSamplePath();

	void setSamplePath(String samplePath);

	Map<Integer, TrackModel_v1000> getTrackModels();

	void setTrackModels(Map<Integer, TrackModel_v1000> trackModels);
}
