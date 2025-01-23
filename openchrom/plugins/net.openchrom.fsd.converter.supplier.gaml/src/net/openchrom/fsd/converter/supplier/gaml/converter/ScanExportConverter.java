/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.fsd.converter.supplier.gaml.converter;

import java.io.File;

import org.eclipse.chemclipse.fsd.converter.core.AbstractScanExportConverter;
import org.eclipse.chemclipse.fsd.converter.core.IScanExportConverter;
import org.eclipse.chemclipse.fsd.model.core.ISpectrumFSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class ScanExportConverter extends AbstractScanExportConverter implements IScanExportConverter {

	@Override
	public IProcessingInfo<File> convert(File file, ISpectrumFSD scan, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		processingInfo.addInfoMessage("GAML", "Export is not available");
		//
		return processingInfo;
	}
}