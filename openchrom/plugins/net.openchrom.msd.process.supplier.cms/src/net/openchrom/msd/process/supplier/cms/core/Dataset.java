/*******************************************************************************
 * Copyright (c) 2016 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * whitlow - initial API and implementation
*******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

class Dataset {
	LibIon libions[];
	private int libIonsCount; // counts # ions read from library file, then holds # library ions that matched with scan ions
	private int libCompCount; // 1 less than the number of components contained in the library file
	private int usedCompCount; // how many components are used in the LS fit
	ScanIon scanions[];
	private int scanIonsCount; // counts # ions read from scan file
	private int scanIonsUsed; // hold # ions from scan file that matched with library ions
	LibComponent libComps[]; // keep track of library component information
	private SortedSet<String> compNameSet; // used to help ensure no duplicate library components
	private boolean matched; // false and then set true after executing matchIons() 
	
Dataset() {
	libions = new LibIon[10];
	scanions = new ScanIon[10];
	libComps = new LibComponent[10];
	libIonsCount = 0;
	scanIonsCount = 0;
	libCompCount = 0;
	scanIonsUsed = -1;
	compNameSet = new ConcurrentSkipListSet<String>(String.CASE_INSENSITIVE_ORDER);
	matched = false;
}

int getUsedLibIonCount() throws InvalidLibIonCountException {
	if (!matched) throw new InvalidLibIonCountException();
	return libIonsCount;
}

int getUsedScanIonCount() throws InvalidScanIonCountException {
	if (!matched) throw new InvalidScanIonCountException();
	return scanIonsUsed;
}

void addLibIon(double mass, double abundance, int compIndex) throws InvalidComponentIndex{
	if (0 > compIndex) {
		throw new InvalidComponentIndex(compIndex);
	}
	if (libions.length <= libIonsCount) {
		libions = Arrays.copyOf(libions, 2*libions.length);
	}
	libions[libIonsCount] = new LibIon(mass, abundance, compIndex, libComps[compIndex]);
	libIonsCount++;
}

int addNewComponent(IRegularLibraryMassSpectrum compLib) throws DuplicateCompNameException {
	String compName = compLib.getLibraryInformation().getName();
	if (!compNameSet.add(compName)) {
		throw new DuplicateCompNameException(compName);
	}
	if (libComps.length <= libCompCount) {
		libComps = Arrays.copyOf(libComps, 2*libComps.length);
	}
	libComps[libCompCount] = new LibComponent(compLib);
	return libCompCount++;
}

boolean addComponent(String compName) {
	if (!compNameSet.add(compName)) {
		return false;
	}
	libCompCount++;
	return true;
}

int getCompCount() throws InvalidGetCompCountException {
	if (0 > libCompCount)
		throw new InvalidGetCompCountException(libCompCount+1);
	return libCompCount;
}

int getUsedCompCount() throws InvalidGetCompCountException {
	if (0 >= usedCompCount)
		throw new InvalidGetCompCountException(usedCompCount);
	return usedCompCount;
}

int getIonsInScan() {
	return scanIonsCount;
}
//LibIon getLibIon(int index) {
//	if (!matched)
//		throw new InvalidGetLibIonException(index);
//	return libions[index];
//}

//ScanIon getScanIon(int index) {
//	if (!matched)
//		throw new InvalidGetScanIonException(index);
//	return scanions[index];
//}

void addScanIon(double mass, double abundance, ICalibratedVendorMassSpectrum scan) {
	if (scanions.length <= scanIonsCount) {
		scanions = Arrays.copyOf(scanions, 2*scanions.length);
	}
	scanions[scanIonsCount] = new ScanIon(mass, abundance, scan, scanIonsCount);
	scanIonsCount++;
}

void matchIons(double massTol) throws NoLibIonsException, NoScanIonsException{
	int i, j, rowCount;
	
	if (0 >= libIonsCount)
		throw new NoLibIonsException(libIonsCount);
	if (0 >= scanIonsCount)
		throw new NoScanIonsException(libIonsCount);
	
	Arrays.sort(this.libions, 0, libIonsCount);
	Arrays.sort(this.scanions, 0, scanIonsCount);
	
	i = 0; //index into libIOns[]
	rowCount = 0;
	for (j = 0; j<scanIonsCount; j++) {
		while ((i < libIonsCount)
				&& (libions[i].massLess(scanions[j].ionMass, massTol))) { // skip over library ion masses that are less than current scan ion mass
			i++;
		}
		if ((i < libIonsCount)
				&& (libions[i].massEqual(scanions[j].ionMass, massTol))) { // test if library ion mass == current scan ion mass
			do { // mark ions having equal masses
				libions[i].setMark();
				libions[i].ionMassIndex = rowCount;
				scanions[j].setMark();
				scanions[j].ionMassIndex = rowCount;
				i++;
			} while ((i < libIonsCount)
				&& (libions[i].massEqual(scanions[j].ionMass, massTol)));
			rowCount++;
		}//if
	} //for
	
	j = 0;
	for (i = 0; i<libIonsCount; i++) { // save only marked library ions
		if (libions[i].mark) {
			libions[j] = libions[i];
			j++;
		}
		//else {
		//	System.out.println("eliminating ion ("
		//			+ libions[i].ionMass +"," 
		//			+ libions[i].ionAbundance +"), component "
		//			+ libions[i].componentRef.libraryRef.getLibraryInformation().getName());
		//}
	}
	libIonsCount = j;
	libions = Arrays.copyOf(libions, libIonsCount);
	
	j = 0;
	for (i = 0; i<scanIonsCount; i++) { // save only marked scan ions
		if (scanions[i].mark) {
			scanions[j] = scanions[i];
			j++;
		}
	}
	scanIonsUsed = j;
	scanions = Arrays.copyOf(scanions, scanIonsUsed);
	
	for (i = 0; i<libIonsCount; i++) { // identify library components we actually use
		//libComps[libions[i].ionCompIndex].mark = true;
		libions[i].componentRef.mark = true;
	}
	j = 0;
	for (i = 0; i<libCompCount; i++) { // consolidate library components we actually use
		if (libComps[i].mark) {
			libComps[j] = libComps[i];
			libComps[j].componentIndex = j;
			j++;
		}
		else {
			System.out.println("Eliminating library component \""
					+ libComps[i].libraryRef.getLibraryInformation().getName() 
					+ "\", no ions are present in the scan");
		}
	}
	usedCompCount = j;
	libComps = Arrays.copyOf(libComps, usedCompCount);
	matched = true;
}

} //class