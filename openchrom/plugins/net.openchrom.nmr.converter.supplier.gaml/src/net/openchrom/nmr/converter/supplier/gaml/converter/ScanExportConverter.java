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
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.gaml.converter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.nmr.converter.core.AbstractScanExportConverter;
import org.eclipse.chemclipse.processing.core.IMessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;

public class ScanExportConverter extends AbstractScanExportConverter<File> {

	@Override
	public void convert(OutputStream stream, IComplexSignalMeasurement<?> measurement, IMessageConsumer messageConsumer, IProgressMonitor monitor) throws IOException {

	}
}