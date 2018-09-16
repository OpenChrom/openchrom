/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.pcr.report.supplier.misc.core;

import java.io.File;

import org.eclipse.chemclipse.pcr.converter.core.AbstractPlateImportConverter;
import org.eclipse.chemclipse.pcr.converter.core.IPlateImportConverter;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class PCRImportConverter extends AbstractPlateImportConverter implements IPlateImportConverter {

	private static final String DESCRIPTION = "PCR Report Converter";

	@Override
	public IProcessingInfo convert(File file, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = super.validate(file);
		processingInfo.addErrorMessage(DESCRIPTION, "The export converter doesn't support reading files.");
		return processingInfo;
	}
}
