/*******************************************************************************
 * Copyright (c) 2016, 2026 Walter Whitlock, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;

// public interface ICalibratedVendorLibraryMassSpectrum extends IRegularLibraryMassSpectrum {
public interface ICalibratedVendorLibraryMassSpectrum extends IRegularLibraryMassSpectrum {

	public int compareTo(ICalibratedVendorLibraryMassSpectrum spectrum);

	double get2Norm();

	/**
	 * Returns the list of comments or an empty list.
	 *
	 * @return {@link Set}
	 */
	List<String> getComments();

	double getEenergy();

	double getEtimes();

	double getIenergy();

	String getInstrumentName();

	double getScaleFactor();

	String getSignalUnits();

	double getSourcePressure();

	double getSourcePressure(String ppUnits);

	String getSourcePressureUnits();

	String getTimeStamp();

	boolean isSelected();

	String makeNameString();

	void setComments(List<String> comments);

	void setEenergy(double eenergy);

	void setEtimes(double etimes);

	void setIenergy(double ienergy);

	void setInstrumentName(String instrumentName);

	void setScaleFactor(double scaleFactor);

	void setSelected(boolean isSelected);

	void setSignalUnits(String signalUnits);

	void setSourcePressure(double sourcePressure);

	void setSourcePressureUnits(String sourcePressureUnits);

	void setTimeStamp(String timeStamp);
	// void sortMZ();
}
