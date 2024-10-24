/*******************************************************************************
 * Copyright (c) 2016, 2024 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - refactor m/z and abundance limit
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.support.text.ValueFormat;

public class CalibratedVendorLibraryMassSpectrum extends AbstractRegularLibraryMassSpectrum implements ICalibratedVendorLibraryMassSpectrum, Comparable<ICalibratedVendorLibraryMassSpectrum> {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 5981872973320440739L;
	//
	private DecimalFormat decimalFormatScaleFactor = ValueFormat.getDecimalFormatEnglish("0.0#####");
	private List<String> comments; // this implementation preserves the order in which the comments were first read
	private double sourcePressure;
	private String sourcePressureUnits;
	private String signalUnits;
	private String timeStamp;
	private String instrumentName;
	private double eEnergyV;
	private double iEnergyV;
	private double eTimeS;
	protected double value2Norm; // ignored in compareTo()
	protected boolean valid2Norm; // ignored in compareTo()
	private boolean isSelected; // true if this spectrum is selected for some purpose, ignored in compareTo()
	private double scaleFactor; // used for dynamic rescaling, ignored in compareTo()

	public CalibratedVendorLibraryMassSpectrum() {

		/*
		 * Initialize the values.
		 */
		this.getLibraryInformation().setMolWeight(-1d);
		comments = new ArrayList<String>();
		this.sourcePressure = -1d;
		this.sourcePressureUnits = "";
		this.signalUnits = "";
		this.timeStamp = "";
		this.instrumentName = "";
		this.eEnergyV = -1d;
		this.iEnergyV = -1d;
		this.eTimeS = -1d;
		this.valid2Norm = false;
		this.isSelected = false;
		this.scaleFactor = 1d;
	}

	/**
	 * sorts the ion list or measurement list into increasing MZ order
	 */
	// @Override
	// public void sortMZ() {
	//
	// List<IIon> sortedList = new ArrayList<>(getIons());
	// Collections.sort(sortedList); // uses AbstractIon.compareTo(IIon)
	// setIons(sortedList);
	// }
	@Override
	/**
	 * calculates 2-norm of abundance
	 */
	public double get2Norm() {

		if(!valid2Norm) {
			value2Norm = 0;
			for(IIon ion : this.getIons()) {
				value2Norm += ion.getAbundance() * ion.getAbundance();
			}
			value2Norm = java.lang.StrictMath.sqrt(value2Norm);
			valid2Norm = true;
		}
		return value2Norm;
	}

	@Override
	public int compareTo(ICalibratedVendorLibraryMassSpectrum spectrum) {

		int result;
		if(!(spectrum instanceof ICalibratedVendorMassSpectrum)) { // fields for CalibratedVendorLibraryMassSpectrum only
			// is library file
			result = this.getLibraryInformation().getName().compareTo(spectrum.getLibraryInformation().getName());
			if(0 != result) {
				return result;
			}
			result = this.getLibraryInformation().getCasNumber().compareTo(spectrum.getLibraryInformation().getCasNumber());
			if(0 != result) {
				return result;
			}
			result = this.getLibraryInformation().getFormula().compareTo(spectrum.getLibraryInformation().getFormula());
			if(0 != result) {
				return result;
			}
			result = (int)java.lang.StrictMath.signum(this.getLibraryInformation().getMolWeight() - spectrum.getLibraryInformation().getMolWeight());
			if(0 != result) {
				return result;
			}
			result = this.getLibraryInformation().getSynonyms().size() - spectrum.getLibraryInformation().getSynonyms().size();
			if(0 != result) {
				return result;
			}
			if(0 < this.getLibraryInformation().getSynonyms().size()) {
				String set1[] = new String[0];
				String set2[] = new String[0];
				set1 = this.getLibraryInformation().getSynonyms().toArray(set1);
				set2 = spectrum.getLibraryInformation().getSynonyms().toArray(set2);
				for(int i = 0; i < this.getLibraryInformation().getSynonyms().size(); i++) {
					result = set1[i].compareTo(set2[i]);
					if(0 != result) {
						return result;
					}
				}
			}
			result = this.getNumberOfIons() - spectrum.getNumberOfIons();
			if(0 != result) {
				return result;
			}
			if(0 < this.getNumberOfIons()) {
				IIon set1[] = new IIon[this.getNumberOfIons()];
				IIon set2[] = new IIon[this.getNumberOfIons()];
				set1 = this.getIons().toArray(set1);
				set2 = spectrum.getIons().toArray(set2);
				for(int i = 0; i < this.getNumberOfIons(); i++) {
					result = (int)java.lang.StrictMath.signum(set1[i].getIon() - set2[i].getIon());
					if(0 != result) {
						return result;
					}
					result = (int)java.lang.StrictMath.signum(set1[i].getAbundance() - set2[i].getAbundance());
					if(0 != result) {
						return result;
					}
				}
			}
		}
		// fields for both CalibratedVendorLibraryMassSpectrum and CalibratedVendorMassSpectrum
		result = this.comments.size() - spectrum.getComments().size();
		if(0 != result) {
			return result;
		}
		if(0 < this.comments.size()) {
			String set1[] = new String[0];
			String set2[] = new String[0];
			set1 = this.getComments().toArray(set1);
			set2 = spectrum.getComments().toArray(set2);
			for(int i = 0; i < this.getComments().size(); i++) {
				result = set1[i].compareTo(set2[i]);
				if(0 != result) {
					return result;
				}
			}
		}
		result = (int)java.lang.StrictMath.signum(this.getEenergy() - spectrum.getEenergy());
		if(0 != result) {
			return result;
		}
		result = (int)java.lang.StrictMath.signum(this.getIenergy() - spectrum.getIenergy());
		if(0 != result) {
			return result;
		}
		result = (int)java.lang.StrictMath.signum(this.getEtimes() - spectrum.getEtimes());
		if(0 != result) {
			return result;
		}
		result = (int)java.lang.StrictMath.signum(this.getSourcePressure() - spectrum.getSourcePressure());
		if(0 != result) {
			return result;
		}
		result = this.getInstrumentName().compareTo(spectrum.getInstrumentName());
		if(0 != result) {
			return result;
		}
		result = this.getSignalUnits().compareTo(spectrum.getSignalUnits());
		if(0 != result) {
			return result;
		}
		result = this.getSourcePressureUnits().compareTo(spectrum.getSourcePressureUnits());
		if(0 != result) {
			return result;
		}
		result = this.getTimeStamp().compareTo(spectrum.getTimeStamp());
		if(0 != result) {
			return result;
		}
		return 0;
	}

	@Override
	public double getSourcePressure() {

		return sourcePressure;
	}

	@Override
	public boolean isSelected() {

		return isSelected;
	}

	@Override
	public void setSelected(boolean isSelected) {

		this.isSelected = isSelected;
	}

	@Override
	public double getSourcePressure(String units) {

		units = units.toLowerCase();
		if(units.equals(sourcePressureUnits.toLowerCase())) {
			return sourcePressure;
		} else if("torr".equals(units) & "mbar".equals(sourcePressureUnits.toLowerCase())) {
			return sourcePressure * 0.75006167382;
		} else if("mbar".equals(units) & "torr".equals(sourcePressureUnits.toLowerCase())) {
			return sourcePressure * 1.3332237;
		} else {
			return 0d;
		}
	}

	@Override
	public void setSourcePressure(double pressure) {

		sourcePressure = pressure;
	}

	@Override
	public double getScaleFactor() {

		return scaleFactor;
	}

	@Override
	public void setScaleFactor(double scaleFactor) {

		this.scaleFactor = scaleFactor;
	}

	@Override
	public String getSourcePressureUnits() {

		return sourcePressureUnits;
	}

	@Override
	public void setSourcePressureUnits(String spunits) {

		sourcePressureUnits = spunits;
	}

	@Override
	public String getSignalUnits() {

		return signalUnits;
	}

	@Override
	public void setSignalUnits(String sigunits) {

		signalUnits = sigunits;
	}

	@Override
	public String getTimeStamp() {

		return timeStamp;
	}

	@Override
	public void setTimeStamp(String tstamp) {

		timeStamp = tstamp;
	}

	@Override
	public String getInstrumentName() {

		return instrumentName;
	}

	@Override
	public void setInstrumentName(String iname) {

		instrumentName = iname;
	}

	@Override
	public double getEtimes() {

		return eTimeS;
	}

	@Override
	public double getEenergy() {

		return eEnergyV;
	}

	@Override
	public double getIenergy() {

		return iEnergyV;
	}

	@Override
	public String makeNameString() {

		StringBuilder nameString = new StringBuilder();
		if(1d != getScaleFactor()) {
			nameString.append("[");
			nameString.append(decimalFormatScaleFactor.format(getScaleFactor()));
			nameString.append("]");
		}
		nameString.append(getLibraryInformation().getName());
		return nameString.toString();
	}

	@Override
	public void setEtimes(double etimes) {

		eTimeS = etimes;
	}

	@Override
	public void setEenergy(double eenergy) {

		eEnergyV = eenergy;
	}

	@Override
	public void setIenergy(double ienergy) {

		iEnergyV = ienergy;
	}

	@Override
	public List<String> getComments() {

		return comments;
	}

	@Override
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
			defaultIon = new Ion(ion.getIon(), ion.getAbundance());
			massSpectrum.addIon(defaultIon);
		}
		return massSpectrum;
	}
}