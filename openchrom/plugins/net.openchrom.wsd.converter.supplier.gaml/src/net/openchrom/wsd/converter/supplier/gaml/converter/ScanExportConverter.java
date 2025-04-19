/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.gaml.converter;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.core.AbstractScanExportConverter;
import org.eclipse.chemclipse.wsd.converter.core.IScanExportConverter;
import org.eclipse.chemclipse.wsd.model.core.ISpectrumWSD;
import org.eclipse.core.runtime.IProgressMonitor;

public class ScanExportConverter extends AbstractScanExportConverter implements IScanExportConverter {

	@Override
	public IProcessingInfo<File> convert(File file, ISpectrumWSD scan, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		processingInfo.addInfoMessage("GAML", "Export is not available");
		//
		return processingInfo;
	}
}