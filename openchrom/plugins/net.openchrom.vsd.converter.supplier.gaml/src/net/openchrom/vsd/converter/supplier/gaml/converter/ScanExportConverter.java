/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.vsd.converter.supplier.gaml.converter;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.vsd.converter.core.AbstractScanExportConverter;
import org.eclipse.chemclipse.vsd.converter.core.IScanExportConverter;
import org.eclipse.chemclipse.vsd.model.core.ISpectrumVSD;
import org.eclipse.core.runtime.IProgressMonitor;

public class ScanExportConverter extends AbstractScanExportConverter implements IScanExportConverter {

	@Override
	public IProcessingInfo<File> convert(File file, ISpectrumVSD scan, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		processingInfo.addInfoMessage("GAML", "Export is not available");
		//
		return processingInfo;
	}
}