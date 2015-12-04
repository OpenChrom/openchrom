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
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFileReader;
import net.sf.jmgf.impl.MGFElementIterator;
import net.sf.jmgf.impl.MGFFileReaderImpl;

public class MGFReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		// IIonTransitionSettings ionTransitionSettings = new IonTransitionSettings();
		// double q1MZ = 172.4d;
		// double q3MZ = 123.3d;
		// double collisionEnergy = 15.0d;
		// double q1Resolution = 1.2d;
		// double q3Resolution = 1.2d;
		// IIonTransition ionTransition = ionTransitionSettings.getIonTransition(q1MZ, q3MZ, collisionEnergy, q1Resolution, q3Resolution, 0);
		//
		IMassSpectra massSpectra = new MassSpectra();
		MGFFileReader mgfFileReader = new MGFFileReaderImpl(file);
		MGFElementIterator iterator = mgfFileReader.getIterator();
		TransformerMGFElementIScanMSD transformer = new TransformerMGFElementIScanMSD();
		while(iterator.hasNext()) {
			MGFElement next = iterator.next();
			massSpectra.setName(next.getTitle());
			massSpectra.addMassSpectrum(transformer.transform(next));
		}
		mgfFileReader.close();
		return massSpectra;
	}
}
