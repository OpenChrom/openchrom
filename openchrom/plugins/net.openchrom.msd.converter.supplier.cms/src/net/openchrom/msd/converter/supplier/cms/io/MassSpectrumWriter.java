/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.exceptions.ReferenceMustNotBeNullException;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.ILibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.IMassSpectrumTarget;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.MassSpectrumTarget;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.RegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.preferences.PreferenceSupplier;

public class MassSpectrumWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	public static final String CRLF = "\r\n";
	public static final float NORMALIZATION_BASE = 1000.0f;
	//
	private static final Logger logger = Logger.getLogger(MassSpectrumWriter.class);
	//
	private static final String RT = "RT: ";
	private static final String RRT = "RRT: ";
	private static final String RI = "RI: ";
	private static final String NAME = "NAME: ";
	private static final String CASNO = "CASNO: ";
	private static final String SMILES = "SMILES: ";
	private static final String COMMENTS = "COMMENTS: ";
	private static final String NUM_PEAKS = "NUM PEAKS: ";
	private static final String FORMULA = "FORMULA: ";
	private static final String MW = "MW: ";
	private static final String DB = "DB: ";
	private static final String REFID = "REFID: ";
	// private static final String SOURCEP = "SOURCEP: ";
	// private static final String SPUNITS = "SPUNITS: ";
	// private static final String SIGUNITS = "SIGUNITS: ";
	// private static final String TSTAMP = "TSTAMP: ";
	// private static final String ETIMES = "ETIMES: ";
	// private static final String EENERGYV = "EENERGYV: ";
	// private static final String IENERGYV = "IENERGYV: ";
	// private static final String INAME = "INAME: ";
	//
	private DecimalFormat decimalFormat;
	private TargetExtendedComparator targetExtendedComparator;

	public MassSpectrumWriter() {
		decimalFormat = ValueFormat.getDecimalFormatEnglish();
		targetExtendedComparator = new TargetExtendedComparator(SortOrder.DESC);
	}

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		writeMassSpectrum(fileWriter, massSpectrum, monitor);
		fileWriter.close();
	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		writeMassSpectra(fileWriter, massSpectra, monitor);
		fileWriter.close();
	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException {

		IScanMSD optimizedMassSpectrum = getOptimizedMassSpectrum(massSpectrum);
		IIdentificationTarget identificationTarget = getIdentificationTarget(optimizedMassSpectrum);
		/*
		 * Write the fields
		 */
		fileWriter.write(getNameField(massSpectrum, identificationTarget) + CRLF);
		String synonyms = getSynonyms(optimizedMassSpectrum);
		if(synonyms != null && !synonyms.equals("")) {
			fileWriter.write(synonyms);
		}
		fileWriter.write(getCommentsField(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getRetentionTimeField(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getRelativeRetentionTimeField(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getRetentionIndexField(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getFormulaField(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getMWField(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getCasNumberField(identificationTarget) + CRLF);
		fileWriter.write(getSmilesField(identificationTarget) + CRLF);
		fileWriter.write(getDBField(identificationTarget) + CRLF);
		fileWriter.write(getReferenceIdentifierField(identificationTarget) + CRLF);
		fileWriter.write(getNumberOfPeaks(optimizedMassSpectrum) + CRLF);
		fileWriter.write(getIons(optimizedMassSpectrum));
		/*
		 * To separate the mass spectra correctly.
		 */
		fileWriter.write(CRLF);
		fileWriter.flush();
	}

	private String getSynonyms(IScanMSD massSpectrum) {

		StringBuilder builder = new StringBuilder();
		if(massSpectrum instanceof ILibraryMassSpectrum) {
			ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
			Set<String> synonyms = libraryMassSpectrum.getLibraryInformation().getSynonyms();
			if(synonyms.size() > 0) {
				for(String synonym : synonyms) {
					/*
					 * Set the synonym.
					 */
					builder.append("Synon: ");
					builder.append(synonym);
					builder.append(CRLF);
				}
			}
		}
		//
		return builder.toString();
	}

	/**
	 * Returns the mass spectra in the convenient AMDIS format.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	private String getIons(IScanMSD massSpectrum) {

		StringBuilder builder = new StringBuilder();
		List<IIon> ions = massSpectrum.getIons();
		for(IIon ion : ions) {
			/*
			 * Add each ion.
			 */
			builder.append(ion.getIon());
			builder.append(" ");
			builder.append(ion.getAbundance());
			builder.append(";");
			builder.append(CRLF);
		}
		return builder.toString();
	}

	/**
	 * Makes a deep copy of the mass spectrum, normalizes it and removes too low abundances.
	 * 
	 * @param massSpectrum
	 * @return {@link IScanMSD}
	 */
	protected IScanMSD getOptimizedMassSpectrum(IScanMSD massSpectrum) {

		IScanMSD optimizedMassSpectrum = getUnitOrHighMassResolutionCopy(massSpectrum);
		normalizeMassSpectrumOnDemand(optimizedMassSpectrum);
		removeLowIntensityIonsOnDemand(optimizedMassSpectrum);
		return optimizedMassSpectrum;
	}

	/**
	 * Removes the ions below the given minimum abundance.
	 * 
	 * @param normalizedMassSpectrum
	 * @param minimumAbundance
	 */
	protected void removeIonsWithAnTooLowAbundance(IScanMSD normalizedMassSpectrum, float minimumAbundance) {

		List<IIon> ionsToRemove = new ArrayList<IIon>();
		for(IIon ion : normalizedMassSpectrum.getIons()) {
			if(ion.getAbundance() < minimumAbundance) {
				ionsToRemove.add(ion);
			}
		}
		// Remove the selected ions.
		for(IIon ion : ionsToRemove) {
			normalizedMassSpectrum.removeIon(ion);
		}
	}

	/**
	 * Returns the name information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getNameField(IScanMSD massSpectrum, IIdentificationTarget identificationTarget) {

		/*
		 * The identifier is very important when tagging files
		 * for identification processes. Hence, if an identifier
		 * is available, set it.
		 */
		String field = NAME;
		String identification = "";
		String identifier = massSpectrum.getIdentifier();
		//
		if(identifier != null && !identifier.equals("")) {
			identification = identifier;
		} else if(identificationTarget != null) {
			identification = identificationTarget.getLibraryInformation().getName();
		}
		//
		if(identification.equals("")) {
			identification = "NO IDENTIFIER AVAILABLE";
		}
		//
		return field + identification;
	}

	/**
	 * This method returns the identification target or null if there is none.
	 * 
	 * @param massSpectrum
	 * @return
	 */
	protected IIdentificationTarget getIdentificationTarget(IScanMSD massSpectrum) {

		IIdentificationTarget identificationTarget = null;
		if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
			/*
			 * Library MS
			 */
			IRegularLibraryMassSpectrum libraryMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
			try {
				identificationTarget = new MassSpectrumTarget(libraryMassSpectrum.getLibraryInformation(), ComparisonResult.createNoMatchComparisonResult());
			} catch(ReferenceMustNotBeNullException e) {
				logger.warn(e);
			}
		} else if(massSpectrum instanceof IRegularMassSpectrum) {
			/*
			 * Scan/Chromatogram MS
			 */
			List<IMassSpectrumTarget> targets = massSpectrum.getTargets();
			Collections.sort(targets, targetExtendedComparator);
			if(targets.size() >= 1) {
				identificationTarget = targets.get(0);
			}
		}
		return identificationTarget;
	}

	/**
	 * Returns the CAS number information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getCasNumberField(IIdentificationTarget identificationTarget) {

		String field = CASNO;
		if(identificationTarget != null) {
			field += identificationTarget.getLibraryInformation().getCasNumber();
		}
		return field;
	}

	/**
	 * Returns the CAS number information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getSmilesField(IIdentificationTarget identificationTarget) {

		String field = SMILES;
		if(identificationTarget != null) {
			field += identificationTarget.getLibraryInformation().getSmiles();
		}
		return field;
	}

	/**
	 * Returns the comments information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getCommentsField(IScanMSD massSpectrum) {

		String field = COMMENTS;
		if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
			IRegularLibraryMassSpectrum regularMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
			field += regularMassSpectrum.getLibraryInformation().getComments();
		}
		return field;
	}

	/**
	 * Returns the retention time information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getRetentionTimeField(IScanMSD massSpectrum) {

		String field = RT;
		if(massSpectrum instanceof IRegularMassSpectrum) {
			IRegularMassSpectrum regularMassSpectrum = (IRegularMassSpectrum)massSpectrum;
			field += decimalFormat.format(regularMassSpectrum.getRetentionTime() / (1000.0d * 60.0d)); // RT in minutes
		} else {
			field += decimalFormat.format(0.0d);
		}
		return field;
	}

	/**
	 * Returns the retention time information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getRelativeRetentionTimeField(IScanMSD massSpectrum) {

		String field = RRT;
		if(massSpectrum instanceof IRegularMassSpectrum) {
			IRegularMassSpectrum regularMassSpectrum = (IRegularMassSpectrum)massSpectrum;
			field += decimalFormat.format(regularMassSpectrum.getRelativeRetentionTime() / (1000.0d * 60.0d)); // RRT in minutes
		} else {
			field += decimalFormat.format(0.0d);
		}
		return field;
	}

	/**
	 * Returns the retention index information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getRetentionIndexField(IScanMSD massSpectrum) {

		String field = RI;
		if(massSpectrum instanceof IRegularMassSpectrum) {
			IRegularMassSpectrum regularMassSpectrum = (IRegularMassSpectrum)massSpectrum;
			field += decimalFormat.format(regularMassSpectrum.getRetentionIndex());
		} else {
			field += decimalFormat.format(0.0d);
		}
		return field;
	}

	/**
	 * Returns the name information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getNumberOfPeaks(IScanMSD massSpectrum) {

		String field = NUM_PEAKS;
		field += massSpectrum.getNumberOfIons();
		return field;
	}

	/**
	 * Returns the formula information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getFormulaField(IScanMSD massSpectrum) {

		String field = FORMULA;
		if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
			IRegularLibraryMassSpectrum regularMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
			field += regularMassSpectrum.getLibraryInformation().getFormula();
		}
		return field;
	}

	/**
	 * Returns the MW information from the mass spectrum.
	 * 
	 * @param massSpectrum
	 * @return String
	 */
	protected String getMWField(IScanMSD massSpectrum) {

		String field = MW;
		if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
			IRegularLibraryMassSpectrum regularMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
			field += regularMassSpectrum.getLibraryInformation().getMolWeight();
		}
		return field;
	}

	protected String getDBField(IIdentificationTarget identificationTarget) {

		String field = DB;
		if(identificationTarget != null) {
			field += identificationTarget.getLibraryInformation().getDatabase();
		}
		return field;
	}

	protected String getReferenceIdentifierField(IIdentificationTarget identificationTarget) {

		String field = REFID;
		if(identificationTarget != null) {
			field += identificationTarget.getLibraryInformation().getReferenceIdentifier();
		}
		return field;
	}

	/**
	 * Writes the mass spectra with the given file writer.
	 * 
	 * @throws IOException
	 */
	private void writeMassSpectra(FileWriter fileWriter, IMassSpectra massSpectra, IProgressMonitor monitor) throws IOException {

		/*
		 * Get all mass spectra, test to null and append them with the given
		 * file writer.
		 */
		for(int i = 1; i <= massSpectra.size(); i++) {
			IScanMSD massSpectrum = massSpectra.getMassSpectrum(i);
			/*
			 * There must be at least one ion.
			 */
			if(massSpectrum != null && massSpectrum.getNumberOfIons() > 0) {
				writeMassSpectrum(fileWriter, massSpectrum, monitor);
			}
		}
	}

	private IScanMSD getUnitOrHighMassResolutionCopy(IScanMSD massSpectrum) {

		IScanMSD optimizedMassSpectrum;
		if(PreferenceSupplier.isUseUnitMassResolution()) {
			/*
			 * Unit Mass Resolution
			 */
			IExtractedIonSignal extractedIonSignal = massSpectrum.getExtractedIonSignal();
			optimizedMassSpectrum = getMassSpectrumCopy(massSpectrum, false);
			int startIon = extractedIonSignal.getStartIon();
			int stopIon = extractedIonSignal.getStopIon();
			for(int ion = startIon; ion <= stopIon; ion++) {
				try {
					optimizedMassSpectrum.addIon(new Ion(ion, extractedIonSignal.getAbundance(ion)));
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		} else {
			/*
			 * High Mass Resolution
			 */
			optimizedMassSpectrum = getMassSpectrumCopy(massSpectrum, true);
		}
		return optimizedMassSpectrum;
	}

	private void normalizeMassSpectrumOnDemand(IScanMSD massSpectrum) {

		if(PreferenceSupplier.isNormalizeIntensities()) {
			massSpectrum.normalize(NORMALIZATION_BASE);
		}
	}

	private void removeLowIntensityIonsOnDemand(IScanMSD massSpectrum) {

		if(PreferenceSupplier.isRemoveIntensitiesLowerThanOne()) {
			removeIonsWithAnTooLowAbundance(massSpectrum, 1.0f);
		}
	}

	private IScanMSD getMassSpectrumCopy(IScanMSD massSpectrum, boolean copyIons) {

		IRegularLibraryMassSpectrum massSpectrumCopy = new RegularLibraryMassSpectrum();
		massSpectrumCopy.setRetentionTime(massSpectrum.getRetentionTime());
		massSpectrumCopy.setRelativeRetentionTime(massSpectrum.getRelativeRetentionTime());
		massSpectrumCopy.setRetentionIndex(massSpectrum.getRetentionIndex());
		if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
			IRegularLibraryMassSpectrum regularMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
			massSpectrumCopy.setLibraryInformation(regularMassSpectrum.getLibraryInformation());
		}
		massSpectrumCopy.getTargets().addAll(massSpectrum.getTargets());
		//
		if(copyIons) {
			for(IIon ion : massSpectrum.getIons()) {
				try {
					massSpectrumCopy.addIon(new Ion(ion.getIon(), ion.getAbundance()));
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		}
		//
		return massSpectrumCopy;
	}
}
