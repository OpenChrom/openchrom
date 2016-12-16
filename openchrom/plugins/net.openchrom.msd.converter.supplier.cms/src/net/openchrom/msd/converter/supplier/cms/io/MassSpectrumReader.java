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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class MassSpectrumReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	/**
	 * The converter id is used in the extension point mechanism.
	 * See <extension point="org.eclipse.chemclipse.msd.converter.massSpectrumSupplier"> in plugin.xml.
	 */
	private static final String CONVERTER_ID = "net.openchrom.msd.process.supplier.cms";
	/**
	 * Pre-compile all patterns to be a little bit faster.
	 */
	private static final String RETENTION_INDICES_DELIMITER = ", ";
	private static final String SNUM = "([+-]?\\d+\\.?\\d*(?:[eE][+-]?\\d+)?)"; // matches any valid string representation of a number
	//
	private static final Pattern namePattern = Pattern.compile("^NAME:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern scanPattern = Pattern.compile("^SCAN:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern nameRetentionTimePattern = Pattern.compile("^RT:\\s*([+-]?\\d+\\.?\\d*(?:[eE][+-]?\\d+)?)(\\s*min)", Pattern.CASE_INSENSITIVE); // (rt: 10.818 min)
	private static final Pattern formulaPattern = Pattern.compile("^FORMULA:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern molweightPattern = Pattern.compile("^MW:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern synonymPattern = Pattern.compile("^SYNON(?:[YM]*)?:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern casNumberPattern = Pattern.compile("^CAS(?:NO|#)?:\\s*([0-9-]*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern databaseNamePattern = Pattern.compile("^DB(?:NO|#)?:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern referenceIdentifierPattern = Pattern.compile("^REFID:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern smilesPattern = Pattern.compile("^SMILES:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern retentionTimePattern = Pattern.compile("^RT:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern relativeRetentionTimePattern = Pattern.compile("^RRT:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern retentionIndexPattern = Pattern.compile("^RI:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern numPeaksPattern = Pattern.compile("^NUM PEAKS:\\s*([+-]?\\d+\\.?\\d*(?:[eE][+-]?\\d+)?)", Pattern.CASE_INSENSITIVE);
	private static final Pattern ionPattern = Pattern.compile("([+-]?\\d+\\.?\\d*(?:[eE][+-]?\\d+)?)[\\s,]+([+-]?\\d+\\.?\\d*(?:[eE][+-]?\\d+)?)");
	private static final Pattern sourcepPattern = Pattern.compile("^SOURCEP:\\s*" + SNUM, Pattern.CASE_INSENSITIVE);
	private static final Pattern spunitsPattern = Pattern.compile("^SPUNITS:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern sigunitsPattern = Pattern.compile("^SIGUNITS:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern tstampPattern = Pattern.compile("^TSTAMP:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern etimesPattern = Pattern.compile("^ETIMES:\\s*" + SNUM, Pattern.CASE_INSENSITIVE);
	private static final Pattern eenergyvPattern = Pattern.compile("^EENERGYV:\\s*" + SNUM, Pattern.CASE_INSENSITIVE);
	private static final Pattern ienergyvPattern = Pattern.compile("^IENERGYV:\\s*" + SNUM, Pattern.CASE_INSENSITIVE);
	private static final Pattern inamePattern = Pattern.compile("^INAME:\\s*(.*)", Pattern.CASE_INSENSITIVE);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectra massSpectra = parseFile(file);
		massSpectra.setConverterId(CONVERTER_ID);
		massSpectra.setName(file.getName());
		return massSpectra;
	}

	private IMassSpectra parseFile(File file) throws IOException {

		Charset charSet = Charset.forName("US-ASCII");
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
		InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, charSet);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line;
		int parseState = 0; // 0==searching for NAME or SCAN, 1==adding parameters & searching for NUM_PEAKS, 2==adding mass/signal pairs
		IMassSpectra massSpectra = new MassSpectra();
		Matcher fieldMatcher;
		ICalibratedVendorMassSpectrum massSpectrum = new CalibratedVendorMassSpectrum();
		int peakCount = 0, numPeaks = 0;
		boolean nameFile = false, scanFile = false;
		while((line = bufferedReader.readLine()) != null) {
			if((fieldMatcher = namePattern.matcher(line)).lookingAt()) { // found NAME record
				if(!scanFile) {
					nameFile = true;
				} else {
					System.out.println("got NAME record in a SCAN file");
					parseState = 0;
					continue; // while
				}
				if(2 == parseState) {
					if(0 < peakCount) { // got peaks, add spectrum
						massSpectra.addMassSpectrum(massSpectrum);
					} else { // got no peaks for the current spectrum, discard incomplete spectrum
						System.out.println("got no peaks from \"NAME: " + massSpectrum.getScanName() + "\"");
						parseState = 1;
					}
					parseState = 1;
				} else if(1 == parseState) { // got no peaks, discard incomplete spectrum
					System.out.println("got no NUM_PEAKS from \"NAME: " + massSpectrum.getScanName() + "\"");
					parseState = 1;
				} else if(0 == parseState) {
					parseState = 1;
				}
				massSpectrum = new CalibratedVendorMassSpectrum();
				String name = fieldMatcher.group(1).trim();
				massSpectrum.setScanName(name);
				massSpectrum.getLibraryInformation().setName(name);
			} // if found NAME record
			else if((fieldMatcher = scanPattern.matcher(line)).lookingAt()) { // found SCAN record
				if(!nameFile) {
					scanFile = true;
				} else {
					System.out.println("got SCAN record in a NAME file");
					parseState = 0;
					continue; // while
				}
				if(2 == parseState) {
					if(0 >= peakCount) { // got no peaks for the current spectrum, discard incomplete spectrum
						System.out.println("got no peaks from \"SCAN: " + massSpectrum.getScanName() + "\"");
						parseState = 1;
					} else { // got peaks, add spectrum
						massSpectra.addMassSpectrum(massSpectrum);
						parseState = 1;
					}
				} else if(1 == parseState) { // got no peaks, discard incomplete spectrum
					System.out.println("got no NUM_PEAKS from SCAN: " + massSpectrum.getScanName());
					parseState = 1;
				} else if(0 == parseState) {
					parseState = 1;
				}
				massSpectrum = new CalibratedVendorMassSpectrum();
				String name = fieldMatcher.group(1).trim();
				massSpectrum.setScanName(name);
				massSpectrum.getLibraryInformation().setName(name);
			} // else if found SCAN record
			else if(1 == parseState) {
				if((fieldMatcher = numPeaksPattern.matcher(line)).lookingAt()) { // found NUM PEAKS record
					peakCount = 0;
					numPeaks = (int)Double.parseDouble(fieldMatcher.group(1).trim());
					if(0 >= numPeaks) { // can't have negative or zero
						System.out.println("got illegal NUM PEAKS from \"" + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName() + "\" = " + numPeaks + "\"");
						parseState = 0;
					} else
						parseState = 2;
				} // if
				else if((fieldMatcher = formulaPattern.matcher(line)).lookingAt()) { // found FORMULA record
					massSpectrum.getLibraryInformation().setFormula(fieldMatcher.group(1).trim());
				} // else if FORMULA
				else if((fieldMatcher = sourcepPattern.matcher(line)).lookingAt()) { // found SOURCEP record
					double sourcep;
					sourcep = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < sourcep)
						massSpectrum.setSourcePressure(sourcep);
					else
						System.out.println("got negative or zero SOURCEP from \"" + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName() + "\" = " + sourcep + "\"");
				} // else if SOURCEP
				else if((fieldMatcher = spunitsPattern.matcher(line)).lookingAt()) { // found SPUNITS record
					massSpectrum.setSourcePressureUnits(fieldMatcher.group(1).trim());
				} // else if SPUNITS
				else if((fieldMatcher = sigunitsPattern.matcher(line)).lookingAt()) { // found SIGUNITS record
					massSpectrum.setSignalUnits(fieldMatcher.group(1).trim());
				} // else if SIGUNITS
				else if((fieldMatcher = casNumberPattern.matcher(line)).lookingAt()) { // found CAS record
					massSpectrum.getLibraryInformation().setCasNumber(fieldMatcher.group(1).trim());
				} // else if CAS
				else if((fieldMatcher = tstampPattern.matcher(line)).lookingAt()) { // found TSTAMP record
					massSpectrum.setTimeStamp(fieldMatcher.group(1).trim());
				} // else if TSTAMP
				else if((fieldMatcher = etimesPattern.matcher(line)).lookingAt()) { // found ETIMES record
					double etimes;
					etimes = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 <= etimes)
						massSpectrum.setEtimes(etimes);
					else
						System.out.println("got negative or zero ETIMES from \"" + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName() + "\" = " + etimes + "\"");
				} // else if ETIMES
				else if((fieldMatcher = eenergyvPattern.matcher(line)).lookingAt()) { // found EENERGYV record
					double eenergy;
					eenergy = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < eenergy)
						massSpectrum.setEenergy(eenergy);
					else
						System.out.println("got negative or zero EENERGYV from \"" + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName() + "\" = " + eenergy + "\"");
				} // else if EENERGYV
				else if((fieldMatcher = ienergyvPattern.matcher(line)).lookingAt()) { // found IENERGYV record
					double ienergy;
					ienergy = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < ienergy)
						massSpectrum.setIenergy(ienergy);
					else
						System.out.println("got negative or zero IENERGYV from \"" + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName() + "\" = " + ienergy + "\"");
				} // else if IENERGYV
				else if((fieldMatcher = inamePattern.matcher(line)).lookingAt()) { // found INAME record
					massSpectrum.setInstrumentName(fieldMatcher.group(1).trim());
				} // else if INAME
				else if((fieldMatcher = molweightPattern.matcher(line)).lookingAt()) { // found MW record
					double mweight;
					mweight = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < mweight)
						massSpectrum.getLibraryInformation().setMolWeight(mweight);
					else
						System.out.println("got negative or zero molecular weight from \"" + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName() + "\" = " + mweight + "\"");
				} // else if MW
				else if((fieldMatcher = synonymPattern.matcher(line)).lookingAt()) { // found SYNONYM record
					Set<String> synonyms = massSpectrum.getLibraryInformation().getSynonyms();
					if(null == synonyms) {
						synonyms = new HashSet<String>();
						synonyms.add(fieldMatcher.group(1).trim());
						massSpectrum.getLibraryInformation().setSynonyms(synonyms);
					} else {
						synonyms.add(fieldMatcher.group(1).trim());
					}
				} // else if SYNONYM
				else if((fieldMatcher = smilesPattern.matcher(line)).lookingAt()) { // found SMILES record
					massSpectrum.getLibraryInformation().setSmiles(fieldMatcher.group(1).trim());
				} // else if SMILES
				else if((fieldMatcher = databaseNamePattern.matcher(line)).lookingAt()) { // found DATABASE NAME record
					massSpectrum.getLibraryInformation().setDatabase(fieldMatcher.group(1).trim());
				} // else if DATABASE NAME
				else if((fieldMatcher = referenceIdentifierPattern.matcher(line)).lookingAt()) { // found REF ID record
					massSpectrum.getLibraryInformation().setReferenceIdentifier(fieldMatcher.group(1).trim());
				} // else if REF ID
				else if((fieldMatcher = retentionTimePattern.matcher(line)).lookingAt()) { // found RT record
					double retTime = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0 < retTime)
						massSpectrum.setRetentionTime((int)(retTime * AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
				} // else if RT
				else if((fieldMatcher = nameRetentionTimePattern.matcher(line)).lookingAt()) { // found NAME RTD record
					double retTime = (int)Double.parseDouble(fieldMatcher.group(1).trim());
					if(0 < retTime)
						massSpectrum.setRetentionTime((int)(retTime * AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
				} // else if NAME RT
				else if((fieldMatcher = relativeRetentionTimePattern.matcher(line)).lookingAt()) { // found REL RT record
					double retTime = (int)Double.parseDouble(fieldMatcher.group(1).trim());
					if(0 < retTime)
						massSpectrum.setRelativeRetentionTime((int)(retTime * AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
				} // else if REL RT
				else if((fieldMatcher = retentionIndexPattern.matcher(line)).lookingAt()) { // found RT INDEX record
					String retentionIndices = fieldMatcher.group(1).trim();
					extractRetentionIndices(massSpectrum, retentionIndices, RETENTION_INDICES_DELIMITER);
				} // else if RT INDEX
				else {
					System.out.println("Unrecognize line in file \"" + file.getName() + "\"");
					System.out.println("\tstate = " + parseState + ", ignored: \"" + line + "\"");
				}
			} // else if (1==parseState)
			else if(2 == parseState) {
				double mass;
				float signal;
				if((fieldMatcher = ionPattern.matcher(line)).find()) { // found mass/signal pair
					do {
						// Create the ion or peak and store it in mass spectrum.
						mass = Double.parseDouble(fieldMatcher.group(1));
						signal = Float.parseFloat(fieldMatcher.group(2));
						massSpectrum.addIonMeasurement(mass, signal);
						peakCount++;
					} while(fieldMatcher.find());
				} else {
					System.out.println("Unrecognize line in file \"" + file.getName() + "\"");
					System.out.println("\tstate = " + parseState + ", ignored: \"" + line + "\"");
				}
			} // else if (2==parseState)
			else {
				System.out.println("Unrecognize line in file \"" + file.getName() + "\"");
				System.out.println("\tstate = " + parseState + ", ignored: \"" + line + "\"");
			}
		} // while
		if(2 == parseState) {
			if(0 >= peakCount) { // got no peaks for the current spectrum, discard incomplete spectrum
				System.out.println("got no peaks from " + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName());
			} else { // got peaks, add spectrum
				massSpectra.addMassSpectrum(massSpectrum);
			}
		} else if(1 == parseState) { // got no peaks, discard incomplete spectrum
			System.out.println("got no NUM_PEAKS: from " + (nameFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + massSpectrum.getScanName());
		}
		bufferedReader.close();
		return massSpectra;
	}
}
