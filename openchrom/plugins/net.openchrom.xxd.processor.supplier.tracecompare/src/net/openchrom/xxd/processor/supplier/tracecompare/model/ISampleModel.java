/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
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
