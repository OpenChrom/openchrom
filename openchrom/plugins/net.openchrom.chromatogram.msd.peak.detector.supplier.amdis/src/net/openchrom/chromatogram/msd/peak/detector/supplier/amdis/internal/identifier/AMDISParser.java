/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christoph L�ubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.internal.identifier;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.eclipse.chemclipse.model.core.IPeaks;
import org.eclipse.chemclipse.msd.converter.peak.PeakConverterMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.core.DefaultProcessingResult;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.processing.core.IProcessingResult;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.support.PeakProcessorSupport;

public class AMDISParser {

	private static final int WAIT_TIMEOUT_ELU = Integer.parseInt(System.getProperty("chemclipse.amdis.timeout.elu", "60"));
	private static final int WAIT_TIMEOUT_COMPLETE = Integer.parseInt(System.getProperty("chemclipse.amdis.timeout.complete", "5"));
	private static final int WATCH_WINDOW = Integer.parseInt(System.getProperty("chemclipse.amdis.watch.window", "1"));
	private static final int WAIT_MS = 100;
	private static final int CHECK_WINDOW_SIZE = (int)(TimeUnit.SECONDS.toMillis(WATCH_WINDOW) / WAIT_MS);
	private final File eluFile;
	private final File finFile;
	private final File resFile;

	public AMDISParser(File fileChromatogram) {

		eluFile = getFile(fileChromatogram, "ELU");
		finFile = getFile(fileChromatogram, "FIN");
		resFile = getFile(fileChromatogram, "RES");
	}

	public IProcessingResult<IPeaks<IPeakMSD>> parse(IProgressMonitor monitor) throws InterruptedException {

		DefaultProcessingResult<IPeaks<IPeakMSD>> result = new DefaultProcessingResult<>();
		try {
			SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
			if(!waitForFile(eluFile, WAIT_TIMEOUT_ELU, TimeUnit.SECONDS, subMonitor.split(10, SubMonitor.SUPPRESS_NONE))) {
				throw new InterruptedException("AMDIS does not created required file within the time bounds");
			}
			if(!waitForFileComplete(eluFile, WAIT_TIMEOUT_COMPLETE, TimeUnit.MINUTES, subMonitor.split(10, SubMonitor.SUPPRESS_NONE))) {
				throw new InterruptedException("AMDIS does not finished writing file within the time bounds");
			}
			IProcessingInfo<IPeaks<IPeakMSD>> peaksResult = PeakConverterMSD.convert(eluFile, PeakProcessorSupport.PEAK_CONVERTER_ID, subMonitor.split(70));
			if(peaksResult == null) {
				result.addErrorMessage(PreferenceSupplier.IDENTIFIER, "PeakParser returned no result");
				return result;
			}
			Object processingResult = peaksResult.getProcessingResult();
			if(processingResult instanceof IPeaks) {
				result.setProcessingResult(peaksResult.getProcessingResult());
			}
			for(IProcessingMessage message : peaksResult.getMessages()) {
				result.addMessage(message);
			}
		} catch(IOException e) {
			result.addErrorMessage(PreferenceSupplier.IDENTIFIER, "Reading data failed: " + e);
		} finally {
			// delete files produced by amdis...
			eluFile.delete();
			finFile.delete();
			resFile.delete();
		}
		return result;
	}

	private boolean waitForFileComplete(File file, long timeout, TimeUnit unit, IProgressMonitor monitor) throws IOException, InterruptedException {

		long millis = unit.toMillis(timeout);
		long start = System.currentTimeMillis();
		long[] size = new long[CHECK_WINDOW_SIZE];
		int index = 0;
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Wait for file " + file.getName() + " to complete", (int)(millis / WAIT_MS));
		while(millis > System.currentTimeMillis() - start) {
			Thread.sleep(WAIT_MS);
			subMonitor.worked(1);
			if(index < CHECK_WINDOW_SIZE) {
				long length = file.length();
				if(length > 0) {
					size[index] = length;
					index++;
				}
				continue;
			}
			if(fileSizeHasChanged(size)) {
				index = 0;
				continue;
			}
			return true;
		}
		return false;
	}

	private boolean fileSizeHasChanged(long[] size) {

		long compare = size[0];
		for(int i = 1; i < size.length; i++) {
			if(compare != size[i]) {
				return true;
			}
		}
		return false;
	}

	private boolean waitForFile(File file, long timeout, TimeUnit unit, IProgressMonitor monitor) throws IOException, InterruptedException {

		long start = System.currentTimeMillis();
		long millis = unit.toMillis(timeout);
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Wait for file " + file.getName() + " to appear", (int)(millis / WAIT_MS));
		while(millis > System.currentTimeMillis() - start && !file.exists()) {
			Thread.sleep(WAIT_MS);
			subMonitor.worked(1);
		}
		return file.exists();
	}

	private File getFile(File fileChromatogram, String ext) {

		File file = new File(fileChromatogram.getParent() + File.separator + fileChromatogram.getName().replace(".CDF", "." + ext).toUpperCase());
		if(file.exists()) {
			file.delete();
		}
		file.deleteOnExit();
		return file;
	}
}
