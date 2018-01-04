/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock, Philip Wenig.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;
import net.openchrom.msd.converter.supplier.cms.model.IonMeasurement;

public class MassSpectrumReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(AbstractMassSpectraReader.class);
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
	private static final Pattern casNumberPattern = Pattern.compile("^CAS(?:NO|#)?:\\s*([0-9-]*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern commentPattern = Pattern.compile("^COMMENTS?:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern databaseNamePattern = Pattern.compile("^DB(?:NO|#)?:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern eenergyvPattern = Pattern.compile("^EENERGYV:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern emptyLinePattern = Pattern.compile("^$", Pattern.CASE_INSENSITIVE);
	private static final Pattern etimesPattern = Pattern.compile("^ETIMES:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern formulaPattern = Pattern.compile("^FORMULA:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern ienergyvPattern = Pattern.compile("^IENERGYV:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern inamePattern = Pattern.compile("^INAME:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern ionPattern = Pattern.compile(SNUM + "[\\s,]+" + SNUM);
	private static final Pattern molweightPattern = Pattern.compile("^MW:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern namePattern = Pattern.compile("^NAME:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern nameRetentionTimePattern = Pattern.compile("^RT:\\s*" + SNUM + "(\\s*min)", Pattern.CASE_INSENSITIVE); // (rt: 10.818 min)
	private static final Pattern numPeaksPattern = Pattern.compile("^NUM PEAKS:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern rescalePattern = Pattern.compile("^RESCALE:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern referenceIdentifierPattern = Pattern.compile("^REFID:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern relativeRetentionTimePattern = Pattern.compile("^RRT:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern retentionIndexPattern = Pattern.compile("^RI:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern retentionTimePattern = Pattern.compile("^RT:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern scanPattern = Pattern.compile("^SCAN:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern sigunitsPattern = Pattern.compile("^SIGUNITS:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern smilesPattern = Pattern.compile("^SMILES:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern sourcepPattern = Pattern.compile("^SOURCEP:\\s*" + SNUM + ".*", Pattern.CASE_INSENSITIVE); // allow ignored trailing comment
	private static final Pattern spunitsPattern = Pattern.compile("^SPUNITS:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern synonymPattern = Pattern.compile("^SYNON(?:[YM]*)?:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern tstampPattern = Pattern.compile("^TSTAMP:\\s*(.*)", Pattern.CASE_INSENSITIVE);
	private List<IIon> tempIIonList = null;
	private List<IIonMeasurement> tempIIonMeasurementList = null;
	private Set<String> libOrScanNames = null;

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		libOrScanNames = new HashSet<String>();
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
		ICalibratedVendorLibraryMassSpectrum massSpectrum = null;
		float rescaleValue = 0;
		int spectrumCount = 0;
		int peakCount = 0, numPeaks = 0;
		boolean libFile = false, scanFile = false;
		while((line = bufferedReader.readLine()) != null) {
			if((fieldMatcher = emptyLinePattern.matcher(line)).lookingAt()) { // found empty line
				continue; // while
			} else if((fieldMatcher = namePattern.matcher(line)).lookingAt()) { // found NAME record
				if(!scanFile) {
					libFile = true;
				} else {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got NAME record in a SCAN file");
					parseState = 0;
					continue; // while
				}
				if(2 == parseState) {
					if(0 < peakCount) { // got peaks, add spectrum
						spectrumCount++;
						massSpectrum.setScanNumber(spectrumCount);
						if(libFile) {
							Collections.sort(tempIIonList);
							for(IIon ion : tempIIonList) {
								massSpectrum.addIon(ion);
							}
							if(0 != rescaleValue) {
								rescale(massSpectrum, rescaleValue);
							}
							massSpectra.addMassSpectrum(massSpectrum);
						} else if(scanFile) {
							Collections.sort(tempIIonMeasurementList);
							for(IIonMeasurement ion : tempIIonMeasurementList) {
								((ICalibratedVendorMassSpectrum)massSpectrum).addIonMeasurement(ion.getMZ(), ion.getSignal());
							}
							if(0 != rescaleValue) {
								rescale(massSpectrum, rescaleValue);
							}
							massSpectra.addMassSpectrum(massSpectrum);
						} else { // got no peaks for the current spectrum, discard incomplete spectrum
							System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no peaks from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
							parseState = 1;
						}
					} else { // got no peaks for the current spectrum, discard incomplete spectrum
						System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no peaks from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
						parseState = 1;
					}
					parseState = 1;
				} else if(1 == parseState) { // got no peaks, discard incomplete spectrum
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no NUM_PEAKS from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
					parseState = 1;
				} else if(0 == parseState) {
					parseState = 1;
				}
				//
				String name = fieldMatcher.group(1).trim();
				if(!libOrScanNames.add(name)) {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got duplicate NAME record " + name);
					parseState = 0;
					continue; // while
				}
				massSpectrum = new CalibratedVendorLibraryMassSpectrum();
				tempIIonList = new ArrayList<IIon>();
				tempIIonMeasurementList = null;
				rescaleValue = 0;
				massSpectrum.getLibraryInformation().setName(name);
			} // if found NAME record
			else if((fieldMatcher = scanPattern.matcher(line)).lookingAt()) { // found SCAN record
				if(!libFile) {
					scanFile = true;
				} else {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got SCAN record in a NAME file");
					parseState = 0;
					continue; // while
				}
				if(2 == parseState) {
					if(0 >= peakCount) { // got no peaks for the current spectrum, discard incomplete spectrum
						System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no peaks from \"SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() + "\"");
						parseState = 1;
					} else { // got peaks, add spectrum
						spectrumCount++;
						massSpectrum.setScanNumber(spectrumCount);
						if(libFile) {
							Collections.sort(tempIIonList);
							for(IIon ion : tempIIonList) {
								massSpectrum.addIon(ion);
							}
							if(0 != rescaleValue) {
								rescale(massSpectrum, rescaleValue);
							}
							massSpectra.addMassSpectrum(massSpectrum);
						} else if(scanFile) {
							Collections.sort(tempIIonMeasurementList);
							for(IIonMeasurement ion : tempIIonMeasurementList) {
								((ICalibratedVendorMassSpectrum)massSpectrum).addIonMeasurement(ion.getMZ(), ion.getSignal());
							}
							if(0 != rescaleValue) {
								rescale(massSpectrum, rescaleValue);
							}
							massSpectra.addMassSpectrum(massSpectrum);
						} else { // got no peaks for the current spectrum, discard incomplete spectrum
							System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no peaks from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
							parseState = 1;
						}
						parseState = 1;
					}
				} else if(1 == parseState) { // got no peaks, discard incomplete spectrum
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no NUM_PEAKS from SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName());
					parseState = 1;
				} else if(0 == parseState) {
					parseState = 1;
				}
				//
				String name = fieldMatcher.group(1).trim();
				if(!libOrScanNames.add(name)) {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got duplicate SCAN record " + name);
					parseState = 0;
					continue; // while
				}
				massSpectrum = new CalibratedVendorMassSpectrum();
				tempIIonMeasurementList = new ArrayList<IIonMeasurement>();
				tempIIonList = null;
				rescaleValue = 0;
				((ICalibratedVendorMassSpectrum)massSpectrum).setScanName(name);
			} // else if found SCAN record
			else if(1 == parseState) {
				if((fieldMatcher = numPeaksPattern.matcher(line)).lookingAt()) { // found NUM PEAKS record
					peakCount = 0;
					numPeaks = (int)Double.parseDouble(fieldMatcher.group(1).trim());
					if(0 >= numPeaks) { // can't have negative or zero
						System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got illegal NUM PEAKS from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()) + "\" = " + numPeaks + "\"");
						parseState = 0;
					} else {
						parseState = 2;
					}
				} // if
				else if((fieldMatcher = rescalePattern.matcher(line)).lookingAt()) { // found RESCALE record
					float temp = Float.parseFloat(fieldMatcher.group(1).trim());
					if(0.0 != temp) {
						rescaleValue = temp;
					} else {
						System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got negative or zero RESCALE from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()) + "\" = " + temp + "\"");
					}
				} // else if RESCALE
				else if((fieldMatcher = sourcepPattern.matcher(line)).lookingAt()) { // found SOURCEP record
					double sourcep;
					sourcep = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < sourcep) {
						massSpectrum.setSourcePressure(sourcep);
					} else {
						System.out.println("got negative or zero SOURCEP from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()) + "\" = " + sourcep + "\"");
					}
				} // else if SOURCEP
				else if((fieldMatcher = spunitsPattern.matcher(line)).lookingAt()) { // found SPUNITS record
					massSpectrum.setSourcePressureUnits(fieldMatcher.group(1).trim());
				} // else if SPUNITS
				else if((fieldMatcher = sigunitsPattern.matcher(line)).lookingAt()) { // found SIGUNITS record
					massSpectrum.setSignalUnits(fieldMatcher.group(1).trim());
				} // else if SIGUNITS
				else if((fieldMatcher = tstampPattern.matcher(line)).lookingAt()) { // found TSTAMP record
					massSpectrum.setTimeStamp(fieldMatcher.group(1).trim());
				} // else if TSTAMP
				else if((fieldMatcher = etimesPattern.matcher(line)).lookingAt()) { // found ETIMES record
					double etimes;
					etimes = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 <= etimes) {
						massSpectrum.setEtimes(etimes);
					} else {
						System.out.println("got negative or zero ETIMES from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()) + "\" = " + etimes + "\"");
					}
				} // else if ETIMES
				else if((fieldMatcher = eenergyvPattern.matcher(line)).lookingAt()) { // found EENERGYV record
					double eenergy;
					eenergy = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < eenergy) {
						massSpectrum.setEenergy(eenergy);
					} else {
						System.out.println("got negative or zero EENERGYV from \"" + (libFile ? "NAME: " : (scanFile ? "SCAN: " : "UNKNOWN: ")) + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() + "\" = " + eenergy + "\"");
					}
				} // else if EENERGYV
				else if((fieldMatcher = ienergyvPattern.matcher(line)).lookingAt()) { // found IENERGYV record
					double ienergy;
					ienergy = Double.parseDouble(fieldMatcher.group(1).trim());
					if(0.0 < ienergy) {
						massSpectrum.setIenergy(ienergy);
					} else {
						System.out.println("got negative or zero IENERGYV from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()) + "\" = " + ienergy + "\"");
					}
				} // else if IENERGYV
				else if((fieldMatcher = inamePattern.matcher(line)).lookingAt()) { // found INAME record
					massSpectrum.setInstrumentName(fieldMatcher.group(1).trim());
				} // else if INAME
				else if((fieldMatcher = commentPattern.matcher(line)).lookingAt()) { // found COMMENT record
					List<String> comments = massSpectrum.getComments();
					if(null == comments) {
						comments = new ArrayList<String>();
						comments.add(fieldMatcher.group(1).trim());
						massSpectrum.setComments(comments);
					} else {
						comments.add(fieldMatcher.group(1).trim());
					}
				} // else if COMMENT
					// the following fields do not apply to CalibratedVendorMassSpectrum
				else if(!(massSpectrum instanceof ICalibratedVendorMassSpectrum)) {
					if((fieldMatcher = formulaPattern.matcher(line)).lookingAt()) { // found FORMULA record
						massSpectrum.getLibraryInformation().setFormula(fieldMatcher.group(1).trim());
					} // else if FORMULA
					else if((fieldMatcher = casNumberPattern.matcher(line)).lookingAt()) { // found CAS record
						massSpectrum.getLibraryInformation().setCasNumber(fieldMatcher.group(1).trim());
					} // else if CAS
					else if((fieldMatcher = molweightPattern.matcher(line)).lookingAt()) { // found MW record
						double mweight;
						mweight = Double.parseDouble(fieldMatcher.group(1).trim());
						if(0.0 < mweight) {
							massSpectrum.getLibraryInformation().setMolWeight(mweight);
						} else {
							System.out.println("got negative or zero molecular weight from \"NAME: " + massSpectrum.getLibraryInformation().getName() + "\" = " + mweight + "\"");
						}
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
						if(0 < retTime) {
							massSpectrum.setRetentionTime((int)(retTime * IChromatogramOverview.MINUTE_CORRELATION_FACTOR));
						}
					} // else if RT
					else if((fieldMatcher = nameRetentionTimePattern.matcher(line)).lookingAt()) { // found NAME RTD record
						double retTime = (int)Double.parseDouble(fieldMatcher.group(1).trim());
						if(0 < retTime) {
							massSpectrum.setRetentionTime((int)(retTime * IChromatogramOverview.MINUTE_CORRELATION_FACTOR));
						}
					} // else if NAME RT
					else if((fieldMatcher = relativeRetentionTimePattern.matcher(line)).lookingAt()) { // found REL RT record
						double retTime = (int)Double.parseDouble(fieldMatcher.group(1).trim());
						if(0 < retTime) {
							massSpectrum.setRelativeRetentionTime((int)(retTime * IChromatogramOverview.MINUTE_CORRELATION_FACTOR));
						}
					} // else if REL RT
					else if((fieldMatcher = retentionIndexPattern.matcher(line)).lookingAt()) { // found RT INDEX record
						String retentionIndices = fieldMatcher.group(1).trim();
						extractRetentionIndices(massSpectrum, retentionIndices, RETENTION_INDICES_DELIMITER);
					} // else if RT INDEX
				} // else if (libFile)
				else {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", unrecognized line in file \"" + file.getName() + "\"");
					System.out.println("\tstate == " + parseState + ", ignored: \"" + line + "\"");
				}
			} // else if (1==parseState)
			else if(2 == parseState) {
				double mass;
				float signal;
				if((fieldMatcher = ionPattern.matcher(line)).find()) { // found mass/signal pair
					if(massSpectrum instanceof ICalibratedVendorMassSpectrum) {
						do { // Create IonMeasurements and store them in mass spectrum
							mass = Double.parseDouble(fieldMatcher.group(1));
							signal = Float.parseFloat(fieldMatcher.group(2));
							assert (scanFile);
							tempIIonMeasurementList.add(new IonMeasurement(mass, signal));
							// ((ICalibratedVendorMassSpectrum)massSpectrum).addIonMeasurement(mass, signal);
							peakCount++;
						} while(fieldMatcher.find());
					} else {
						do { // Create Ions and store them in mass spectrum
							mass = Double.parseDouble(fieldMatcher.group(1));
							signal = Float.parseFloat(fieldMatcher.group(2));
							assert (libFile);
							try {
								tempIIonList.add(new Ion(mass, signal));
							} catch(AbundanceLimitExceededException e) {
								logger.warn(e);
							} catch(IonLimitExceededException e) {
								logger.warn(e);
							}
							peakCount++;
						} while(fieldMatcher.find());
					}
				} else {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", unrecognized line in file \"" + file.getName() + "\"");
					System.out.println("\tstate == " + parseState + ", ignored: \"" + line + "\"");
				}
			} // else if (2==parseState)
			else {
				if((fieldMatcher = commentPattern.matcher(line)).lookingAt()) { // found COMMENT record
					// comments are OK anywhere, this one does not get imported or saved
				} else {
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", unrecognized line in file \"" + file.getName() + "\"");
					System.out.println("\tstate == " + parseState + ", ignored: \"" + line + "\"");
				}
			}
		} // while
		if(2 == parseState) {
			if(0 >= peakCount) { // got no peaks for the current spectrum, discard incomplete spectrum
				System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no peaks from " + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
			} else { // got peaks, add spectrum
				spectrumCount++;
				massSpectrum.setScanNumber(spectrumCount);
				if(libFile) {
					Collections.sort(tempIIonList);
					for(IIon ion : tempIIonList) {
						massSpectrum.addIon(ion);
					}
					if(0 != rescaleValue) {
						rescale(massSpectrum, rescaleValue);
					}
					massSpectra.addMassSpectrum(massSpectrum);
				} else if(scanFile) {
					Collections.sort(tempIIonMeasurementList);
					for(IIonMeasurement ion : tempIIonMeasurementList) {
						((ICalibratedVendorMassSpectrum)massSpectrum).addIonMeasurement(ion.getMZ(), ion.getSignal());
					}
					if(0 != rescaleValue) {
						rescale(massSpectrum, rescaleValue);
					}
					massSpectra.addMassSpectrum(massSpectrum);
				} else { // got no peaks for the current spectrum, discard incomplete spectrum
					System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no peaks from \"" + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
					parseState = 1;
				}
			}
		} else if(1 == parseState) { // got no peaks, discard incomplete spectrum
			System.out.println((new Throwable().getStackTrace()[0].getLineNumber()) + ", got no NUM_PEAKS: from " + ((massSpectrum instanceof ICalibratedVendorMassSpectrum) ? "SCAN: " + ((ICalibratedVendorMassSpectrum)massSpectrum).getScanName() : "NAME: " + massSpectrum.getLibraryInformation().getName()));
		}
		bufferedReader.close();
		return massSpectra;
	}

	private void rescale(ICalibratedVendorLibraryMassSpectrum cvmSpectrum, float maxSig) {

		boolean maxIsInitialized = false;
		float maxSignal = -1;
		if(!(cvmSpectrum instanceof ICalibratedVendorMassSpectrum)) {
			for(IIon ion : cvmSpectrum.getIons()) {
				if(!maxIsInitialized) {
					maxSignal = ion.getAbundance();
					maxIsInitialized = true;
				} else {
					if(maxSignal < ion.getAbundance()) {
						maxSignal = ion.getAbundance();
					}
				}
			}
			// System.out.println("rescaling library " + cvmSpectrum.getLibraryInformation().getName() + ", " + maxSignal + "-->" + maxSig);
			for(IIon ion : cvmSpectrum.getIons()) {
				try {
					ion.setAbundance(maxSig / maxSignal * ion.getAbundance());
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				}
			}
		} else {
			for(IIonMeasurement peak : ((ICalibratedVendorMassSpectrum)cvmSpectrum).getIonMeasurements()) {
				if(!maxIsInitialized) {
					maxSignal = peak.getSignal();
					maxIsInitialized = true;
				} else {
					if(maxSignal < peak.getSignal()) {
						maxSignal = peak.getSignal();
					}
				}
			}
			// System.out.println("rescaling scan " + ((ICalibratedVendorMassSpectrum)cvmSpectrum).getScanName() + ", " + maxSignal + "-->" + maxSig);
			for(IIonMeasurement peak : ((ICalibratedVendorMassSpectrum)cvmSpectrum).getIonMeasurements()) {
				peak.setSignal(maxSig / maxSignal * peak.getSignal());
			}
		}
	}
}
