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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

import java.util.List;

public interface IProcessorModel {

	String getVersion();

	void setVersion(String version);

	String getReferenceChromatogramPath();

	void setReferenceChromatogramPath(String referenceChromatogramPath);

	String getIsotopeChromatogramPath();

	void setIsotopeChromatogramPath(String isotopeChromatogramPath);

	IProcessorSettings getProcessorSettings();

	void setProcessorSettings(IProcessorSettings processorSettings);

	String getNotes();

	void setNotes(String notes);

	String getDescription();

	void setDescription(String description);

	List<IScanMarker> getScanMarker();

	void setScanMarker(List<IScanMarker> scanMarker);
}