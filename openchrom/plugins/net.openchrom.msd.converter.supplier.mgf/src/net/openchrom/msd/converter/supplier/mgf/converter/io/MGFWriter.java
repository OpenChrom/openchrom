/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

public class MGFWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append) throws FileNotFoundException, FileIsNotWriteableException, IOException {

	}

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append) throws FileNotFoundException, FileIsNotWriteableException, IOException {

	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum) throws IOException {

	}
}
