/*******************************************************************************
 * Copyright (c) 2014, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.arw.io;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.csd.converter.io.AbstractChromatogramCSDReader;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.support.ChromatogramSupport;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.csd.converter.supplier.arw.io.support.ChromatogramArrayReader;
import net.openchrom.csd.converter.supplier.arw.io.support.IChromatogramArrayReader;
import net.openchrom.csd.converter.supplier.arw.model.IVendorChromatogram;
import net.openchrom.csd.converter.supplier.arw.model.IVendorScan;
import net.openchrom.csd.converter.supplier.arw.model.VendorChromatogram;
import net.openchrom.csd.converter.supplier.arw.model.VendorScan;

public class ChromatogramReader extends AbstractChromatogramCSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);
	private static final Pattern scanPattern = Pattern.compile("(.*)(\t)(.*)");

	@Override
	public IChromatogramCSD read(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file, monitor);
	}

	private IChromatogramCSD readChromatogram(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = new VendorChromatogram();
		chromatogram.setFile(file);
		chromatogram.setConverterId("");

		IChromatogramArrayReader in = new ChromatogramArrayReader(file);
		String input = in.readBytesAsString(in.getLength());
		Matcher matcher = scanPattern.matcher(input);
		while(matcher.find() && !monitor.isCanceled()) {
			String retentionTimeInMinutes = matcher.group(1).replace(",", ".");
			String abundance = matcher.group(3).replace(",", ".");
			try {
				int retentionTime = (int)(Double.valueOf(retentionTimeInMinutes) * IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				float totalSignal = Float.valueOf(abundance);
				IVendorScan scan = new VendorScan();
				scan.setRetentionTime(retentionTime);
				scan.setTotalSignal(totalSignal);
				chromatogram.addScan(scan);
			} catch(NumberFormatException e) {
				logger.warn(e);
			}
		}
		/*
		 * Set scan delay and interval
		 */
		calculateScanIntervalAndDelay(chromatogram);

		return chromatogram;
	}

	private void calculateScanIntervalAndDelay(IChromatogramCSD chromatogram) {

		ChromatogramSupport.calculateScanIntervalAndDelay(chromatogram);
	}
}