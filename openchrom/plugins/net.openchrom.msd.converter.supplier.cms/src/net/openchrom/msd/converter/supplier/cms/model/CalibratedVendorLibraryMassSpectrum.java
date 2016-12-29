/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

public class CalibratedVendorLibraryMassSpectrum extends AbstractRegularLibraryMassSpectrum implements ICalibratedVendorLibraryMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 788113431263082689L;
	private static final Logger logger = Logger.getLogger(CalibratedVendorLibraryMassSpectrum.class);
	//
	private List<String> comments; // this implementation preserves the order in which the comments were first read
	private double sourcePressure = -1d;
	private String sourcePressureUnits;
	private String signalUnits;
	private String timeStamp;
	private String instrumentName;
	private double eEnergyV = -1d;
	private double iEnergyV = -1d;
	private double eTimeS = -1d;

	public CalibratedVendorLibraryMassSpectrum() {
		/*
		 * Initialize the values.
		 */
		comments = null;
		this.getLibraryInformation().setMolWeight(-1d);
	}

	public double getSourcePressure() {

		return sourcePressure;
	}

	public double getSourcePressure(String units) {

		units = units.toLowerCase();
		if(units.equals(sourcePressureUnits.toLowerCase()))
			return sourcePressure;
		else if("torr".equals(units) & "mbar".equals(sourcePressureUnits.toLowerCase())) {
			return sourcePressure * 0.75006167382;
		} else if("mbar".equals(units) & "torr".equals(sourcePressureUnits.toLowerCase())) {
			return sourcePressure * 1.3332237;
		} else
			return 0d;
	}

	public void setSourcePressure(double pressure) {

		sourcePressure = pressure;
	}

	public String getSourcePressureUnits() {

		return sourcePressureUnits;
	}

	public void setSourcePressureUnits(String spunits) {

		sourcePressureUnits = spunits;
	}

	public String getSignalUnits() {

		return signalUnits;
	}

	public void setSignalUnits(String sigunits) {

		signalUnits = sigunits;
	}

	public String getTimeStamp() {

		return timeStamp;
	}

	public void setTimeStamp(String tstamp) {

		timeStamp = tstamp;
	}

	public String getInstrumentName() {

		return instrumentName;
	}

	public void setInstrumentName(String iname) {

		instrumentName = iname;
	}

	public double getEtimes() {

		return eTimeS;
	}

	public double getEenergy() {

		return eEnergyV;
	}

	public double getIenergy() {

		return iEnergyV;
	}

	public void setEtimes(double etimes) {

		eTimeS = etimes;
	}

	public void setEenergy(double eenergy) {

		eEnergyV = eenergy;
	}

	public void setIenergy(double ienergy) {

		iEnergyV = ienergy;
	}

	public List<String> getComments() {

		return comments;
	}

	public void setComments(List<String> comments) {

		if(comments != null) {
			this.comments = comments;
		}
	}

	@Override
	public ICalibratedVendorLibraryMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		/*
		 * The method super.clone() is not used here to avoid removing the mass
		 * fragments from the mass spectrum and to add freshly created ones
		 * again.
		 */
		ICalibratedVendorLibraryMassSpectrum massSpectrum = (ICalibratedVendorLibraryMassSpectrum)super.clone();
		IIon defaultIon;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(IIon ion : getIons()) {
			try {
				defaultIon = new Ion(ion.getIon(), ion.getAbundance());
				massSpectrum.addIon(defaultIon);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			}
		}
		return massSpectrum;
	}
}
