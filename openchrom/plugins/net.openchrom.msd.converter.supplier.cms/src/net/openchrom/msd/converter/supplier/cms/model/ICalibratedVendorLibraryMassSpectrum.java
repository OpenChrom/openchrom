/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
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

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

//public interface ICalibratedVendorLibraryMassSpectrum  extends IRegularLibraryMassSpectrum {
public interface ICalibratedVendorLibraryMassSpectrum  extends IRegularLibraryMassSpectrum, IScanMSD {

	List<String> getComments();

	double getEenergy();

	double getEtimes();

	double getIenergy();

	String getInstrumentName();

	String getSignalUnits();

	double getSourcePressure();

	String getSourcePressureUnits();

	String getTimeStamp();

	void setComments(List<String> comments);

	void setEenergy(double eenergy);

	void setEtimes(double etimes);

	void setIenergy(double ienergy);

	void setInstrumentName(String instrumentName);

	void setSignalUnits(String signalUnits);

	void setSourcePressure(double sourcePressure);

	void setSourcePressureUnits(String sourcePressureUnits);

	void setTimeStamp(String timeStamp);

}
