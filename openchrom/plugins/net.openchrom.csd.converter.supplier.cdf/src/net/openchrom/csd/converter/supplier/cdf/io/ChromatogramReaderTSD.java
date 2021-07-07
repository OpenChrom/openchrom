/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 * 
 * All rights reserved. This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.tsd.converter.core.IImportConverterTSD;
import org.eclipse.chemclipse.tsd.model.core.IChromatogramTSD;
import org.eclipse.chemclipse.tsd.model.core.IScanTSD;
import org.eclipse.chemclipse.tsd.model.core.ScanTSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.csd.converter.supplier.cdf.model.VendorChromatogramTSD;
import net.openchrom.csd.converter.supplier.cdf.preferences.PreferenceSupplier;

public class ChromatogramReaderTSD implements IImportConverterTSD {

	private static final Logger logger = Logger.getLogger(ChromatogramReaderTSD.class);

	@Override
	public IProcessingInfo<IChromatogramTSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramTSD> processingInfo = new ProcessingInfo<>();
		try {
			IChromatogramTSD chromatogramTSD = readChromatogram(file, monitor);
			processingInfo.setProcessingResult(chromatogramTSD);
		} catch(Exception e) {
			logger.warn(e);
		}
		return processingInfo;
	}

	@Override
	public IChromatogramTSD convert(InputStream inputStream, IProgressMonitor monitor) throws IOException {

		return null; // Not needed yet.
	}

	@Override
	public IChromatogramOverview convertOverview(InputStream inputStream, IProgressMonitor monitor) throws IOException {

		return null; // Not needed yet.
	}

	private IChromatogramTSD readChromatogram(File file, IProgressMonitor monitor) throws IOException {

		/*
		 * GCxGC
		 */
		int modulationTime = PreferenceSupplier.getModulationTime2D();
		ChromatogramReaderCSD chromatogramReader = new ChromatogramReaderCSD();
		IChromatogramCSD chromatogramCSD = chromatogramReader.read(file, monitor);
		//
		IChromatogramTSD chromatogram = new VendorChromatogramTSD();
		int offset = 0;
		List<Float> signals = new ArrayList<>();
		//
		for(IScan scan : chromatogramCSD.getScans()) {
			int retentionTime = scan.getRetentionTime();
			float intensity = scan.getTotalSignal();
			//
			int delta = retentionTime - offset;
			if(delta < modulationTime) {
				float signal = intensity;
				signals.add(signal);
			} else {
				float signal = intensity;
				signals.add(signal);
				IScanTSD scanTSD = new ScanTSD();
				scanTSD.setRetentionTime(retentionTime);
				scanTSD.setSignals(getSignals(signals));
				chromatogram.addScan(scanTSD);
				offset = retentionTime;
				signals = new ArrayList<>();
			}
		}
		//
		return chromatogram;
	}

	private float[] getSignals(List<Float> signals) {

		float[] array = new float[signals.size()];
		for(int i = 0; i < signals.size(); i++) {
			array[i] = signals.get(i);
		}
		//
		return array;
	}
}