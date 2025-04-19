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

import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ReferenceModel_v1000;

public interface IProcessorModel {

	String getVersion();

	void setVersion(String version);

	String getDetectorType();

	void setDetectorType(String detectorType);

	String getImageDirectory();

	void setImageDirectory(String imageDirectory);

	String getSampleDirectory();

	void setSampleDirectory(String sampleDirectory);

	String getReferenceDirectory();

	void setReferenceDirectory(String referenceDirectory);

	String getCalculatedResult();

	void setCalculatedResult(String calculatedResult);

	String getGeneralNotes();

	void setGeneralNotes(String generalNotes);

	Map<String, ReferenceModel_v1000> getReferenceModels();

	void setReferenceModels(Map<String, ReferenceModel_v1000> referenceModels);
}