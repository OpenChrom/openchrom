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

import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFileReader;
import net.sf.jmgf.impl.MGFElementIterator;
import net.sf.jmgf.impl.MGFFileReaderImpl;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

public class MGFReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MGFReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectra result = new MassSpectra();
		MGFFileReader reader = new MGFFileReaderImpl(file);
		MGFElementIterator iterator = reader.getIterator();
		try {
			TransformerMGFElementIScanMSD transformer = new TransformerMGFElementIScanMSD();
			while(iterator.hasNext()) {
				MGFElement next = iterator.next();
				result.setName(next.getTitle());
				result.addMassSpectrum(transformer.transform(next));
			}
			return result;
		} catch(IonLimitExceededException e) {
			logger.warn(e);
			// for testing
			e.printStackTrace();
			return null;
		} catch(AbundanceLimitExceededException e) {
			// for testing
			e.printStackTrace();
			logger.warn(e);
			return null;
		} finally {
			reader.close();
		}
	}
}
