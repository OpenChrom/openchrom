/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.converter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.nmr.converter.core.AbstractScanExportConverter;
import org.eclipse.chemclipse.nmr.converter.core.IScanExportConverter;
import org.eclipse.chemclipse.processing.core.IMessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;

public class ScanExportConverter extends AbstractScanExportConverter<File> implements IScanExportConverter {

	@Override
	public void convert(OutputStream stream, IComplexSignalMeasurement<?> measurement, IMessageConsumer messageConsumer, IProgressMonitor monitor) throws IOException {

	}
}