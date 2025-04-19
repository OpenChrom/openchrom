/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.targets.TargetSupport;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.support.ScanSupport;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class ChromatogramExportNamedTraces extends AbstractChromatogramExportConverter implements IChromatogramExportConverter {

	private static final Logger logger = Logger.getLogger(ChromatogramExportNamedTraces.class);
	private static final String DESCRIPTION = "Named Traces Export";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages() && chromatogram instanceof IChromatogramMSD chromatogramMSD) {
			/*
			 * Settings
			 */
			int maxCopyTraces = PreferenceSupplier.getPeakExportNumberTraces();
			/*
			 * Export
			 */
			try (PrintWriter printWriter = new PrintWriter(file)) {
				for(IPeakMSD peak : chromatogramMSD.getPeaks()) {
					String name = TargetSupport.getBestTargetLibraryField(peak);
					if(!name.isEmpty()) {
						IScanMSD scanMSD = peak.getPeakModel().getPeakMassSpectrum();
						String traces = ScanSupport.extractTracesText(scanMSD, maxCopyTraces);
						if(!traces.isEmpty()) {
							printWriter.print(name);
							printWriter.print(" | ");
							printWriter.println(traces);
						}
					}
				}
				printWriter.flush();
				processingInfo.setProcessingResult(file);
			} catch(FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The export file couldn't be found.");
			}
		}
		return processingInfo;
	}
}
