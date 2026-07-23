/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Lorenz Gerber - adjust failed parsing behaviour
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.arw.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.support.ChromatogramSupport;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.core.interpolation.ScanRasterizer;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.arw.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.arw.model.VendorChromatogram;
import net.openchrom.wsd.converter.supplier.arw.model.VendorScan;
import net.openchrom.wsd.converter.supplier.arw.model.VendorScanSignal;
import net.openchrom.wsd.converter.supplier.arw.preferences.PreferenceSupplier;

public class ChromatogramReader extends AbstractChromatogramWSDReader {

	private static final float INVALID_WAVELENGTH = -1.0f;
	private static final String DELIMITER = "\t";
	private static final String WAVELENGTH = "Wavelength";
	private static final String TIME = "Time";

	@Override
	public IChromatogramWSD read(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file, monitor);
	}

	private IChromatogramWSD readChromatogram(File file, IProgressMonitor monitor) throws IOException {

		/*
		 * It's a simple text format:
		 * ---
		 * Wavelength 190.2906 190.8967 191.5017 192.1068 ...
		 * Time
		 * 0 0 0 0 0 ...
		 * 0.0008333334 -0.0016962 -0.0009046 -0.000166 -0.000495 ...
		 * ...
		 */
		IVendorChromatogram chromatogram = new VendorChromatogram();
		chromatogram.setFile(file);
		chromatogram.setConverterId("");
		/*
		 * Parse via a buffered reader.
		 */
		List<Float> wavelengths = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while((line = bufferedReader.readLine()) != null && !monitor.isCanceled()) {
				if(line.startsWith(WAVELENGTH)) {
					/*
					 * Wavelengths
					 */
					String[] values = line.split(DELIMITER);
					for(int i = 1; i < values.length; i++) {
						wavelengths.add(extractWavelength(values[i]));
					}
				} else if(line.startsWith(TIME)) {
					/*
					 * Time
					 * skip the next line, it seems to be 0.
					 */
					bufferedReader.readLine();
				} else {
					/*
					 * Scans
					 */
					if(!wavelengths.isEmpty()) {
						String[] values = line.split(DELIMITER);
						if(values.length >= 1) {
							try {
								IScanWSD scan = new VendorScan();
								int retentionTime = (int)(Double.parseDouble(values[0]) * IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
								scan.setRetentionTime(retentionTime);
								/*
								 * Wavelengths
								 */
								int index = 1;
								for(float wavelength : wavelengths) {
									if(wavelength != INVALID_WAVELENGTH) {
										float intensity = extractIntensity(values[index]);
										if(!Float.isNaN(intensity)) {
											IScanSignalWSD scanSignal = new VendorScanSignal();
											scanSignal.setWavelength(wavelength);
											scanSignal.setAbsorbance(intensity);
											scan.addScanSignal(scanSignal);
										}
									}
									index++;
								}
								/*
								 * Add the scan.
								 */
								chromatogram.addScan(scan);
							} catch(NumberFormatException e) {
								/*
								 * Don't add the scan.
								 */
							}
						}
					}
				}
			}
		}
		/*
		 * Set scan delay and interval
		 */
		if(PreferenceSupplier.isNormalizeScans()) {
			int steps = PreferenceSupplier.getNormalizationSteps();
			ScanRasterizer.normalize(chromatogram, steps);
		}
		ChromatogramSupport.calculateScanIntervalAndDelay(chromatogram);
		return chromatogram;
	}

	private float extractIntensity(String value) {

		try {
			return Float.parseFloat(value);
		} catch(NumberFormatException e) {
			return Float.NaN;
		}
	}

	private float extractWavelength(String value) {

		try {
			return Float.parseFloat(value);
		} catch(NumberFormatException e) {
			return INVALID_WAVELENGTH;
		}
	}
}
