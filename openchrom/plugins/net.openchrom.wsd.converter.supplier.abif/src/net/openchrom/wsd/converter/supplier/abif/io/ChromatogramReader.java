/*******************************************************************************
 * Copyright (c) 2016 Matthias Mailänder, Dr. Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.abif.internal.support.ChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.internal.support.IChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorScan;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorScanSignalDAD;
import net.openchrom.wsd.converter.supplier.abif.model.VendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.VendorScan;
import net.openchrom.wsd.converter.supplier.abif.model.VendorScanSignalDAD;

public class ChromatogramReader extends AbstractChromatogramWSDReader {

	@Override
	public IChromatogramWSD read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readChromatogram(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readChromatogram(file, monitor);
	}

	private IChromatogramWSD readChromatogram(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		System.out.println("Please implement ABIF read.");
		IChromatogramArrayReader in = new ChromatogramArrayReader(file);
		if(!isValidFileFormat(in)) {
			return null;
		}
		/*
		 * Chromatogram
		 */
		IVendorChromatogram chromatogram = new VendorChromatogram();
		/*
		 * If the chromatogram shall be exportable, set the id otherwise its null or "".
		 */
		chromatogram.setConverterId("");
		/*
		 * Set the file name to the chromatogram.
		 */
		chromatogram.setFile(file);
		/*
		 * Add all scans.
		 */
		IVendorScan scan = new VendorScan();
		scan.setRetentionTime(500); // milliseconds, see interval
		IVendorScanSignalDAD scanSignal = new VendorScanSignalDAD();
		/*
		 * A
		 */
		scanSignal.setAbundance(490.8f);
		scanSignal.setWavelength(420);
		scan.addScanSignal(scanSignal);
		/*
		 * C
		 */
		scanSignal.setAbundance(490.8f);
		scanSignal.setWavelength(40);
		scan.addScanSignal(scanSignal);
		/*
		 * T
		 */
		scanSignal.setAbundance(490.8f);
		scanSignal.setWavelength(2350);
		scan.addScanSignal(scanSignal);
		/*
		 * G
		 */
		scanSignal.setAbundance(490.8f);
		scanSignal.setWavelength(594);
		scan.addScanSignal(scanSignal);
		//
		chromatogram.addScan(scan);
		//
		calculateScanIntervalAndDelay(chromatogram, monitor);
		return chromatogram;
	}

	private boolean isValidFileFormat(IChromatogramArrayReader in) {

		in.resetPosition();
		long test = in.read4BULongLE();
		/*
		 * There are different file names with different headers.
		 */
		System.out.println("MGC: " + test);
		if(test == 40000) {
			return true;
		}
		return false;
	}

	private void calculateScanIntervalAndDelay(IVendorChromatogram chromatogram, IProgressMonitor monitor) {

		/*
		 * Calculate the scanInterval and scanDelay.
		 */
		chromatogram.setScanInterval(400);
		chromatogram.setScanDelay(0);
		chromatogram.recalculateRetentionTimes();
	}
}
