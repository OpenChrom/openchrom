/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.muf.converter.model;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;

public interface ISpectraMultiFileMassSpectra extends IMassSpectra, ITaxonomicInformation, ICultivationConditions {

	String getType();

	void setType(String type);

	String getSampleConcentration();

	void setSampleConcentration(String concentration);

	String getSampleTreatment();

	void setSampleTreatment(String treatment);

	String getExtraInformation();

	void setExtraInformation(String extraInfo);

	String getLaserParameters();

	void setLaserParameters(String laserParameters);

	String getCalibrationInformation();

	void setCalibrationInformation(String calibrationInfo);

	String getMeasurementMethod();

	void setMeasurementMethod(String measurementMethod);

	String getCustomerInformation();

	void setCustomerInformation(String customerInfo);

	String getSpectrumPath();

	void setSpectrumPath(String path);

	int getClassAssignment(); // QA

	void setClassAssignment(int classAssignment);

	String getPeakTableInformation();

	void setPeakTableInformation(String peakTableInfo);
}
