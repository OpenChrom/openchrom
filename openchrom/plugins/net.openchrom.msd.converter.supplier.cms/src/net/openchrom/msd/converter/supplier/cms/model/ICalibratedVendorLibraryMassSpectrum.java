/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

// public interface ICalibratedVendorLibraryMassSpectrum extends IRegularLibraryMassSpectrum {
public interface ICalibratedVendorLibraryMassSpectrum extends IRegularLibraryMassSpectrum, IScanMSD {

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

	String getSignalUnits();

	double getSourcePressure();

	double getSourcePressure(String ppUnits);

	String getSourcePressureUnits();

	String getTimeStamp();

	boolean isSelected();

	void setComments(List<String> comments);

	void setEenergy(double eenergy);

	void setEtimes(double etimes);

	void setIenergy(double ienergy);

	void setInstrumentName(String instrumentName);

	void setSelected(boolean isSelected);

	void setSignalUnits(String signalUnits);

	void setSourcePressure(double sourcePressure);

	void setSourcePressureUnits(String sourcePressureUnits);

	void setTimeStamp(String timeStamp);
	// void sortMZ();
}
