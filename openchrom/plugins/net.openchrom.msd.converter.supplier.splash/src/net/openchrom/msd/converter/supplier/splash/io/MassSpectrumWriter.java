/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.splash.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import edu.ucdavis.fiehnlab.spectra.hash.core.Splash;
import edu.ucdavis.fiehnlab.spectra.hash.core.SplashFactory;
import edu.ucdavis.fiehnlab.spectra.hash.core.types.Ion;
import edu.ucdavis.fiehnlab.spectra.hash.core.types.SpectraType;
import edu.ucdavis.fiehnlab.spectra.hash.core.types.SpectrumImpl;

/**
 * Writes a spectrum hash (splash) into a text file using the reference implementation.
 * 
 * Wohlgemuth, G., Mehta, S., Mejia, R. et al. SPLASH, a hashed identifier for mass spectra.
 * Nat Biotechnol 34, 1099–1101 (2016). https://doi.org/10.1038/nbt.3689
 */
public class MassSpectrumWriter implements IMassSpectraWriter {

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException {

		MassSpectra massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		hashMassSpectrum(massSpectra, fileWriter);
	}

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		MassSpectra massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		hashMassSpectrum(massSpectra, fileWriter);
	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		hashMassSpectrum(massSpectra, fileWriter);
	}

	private void hashMassSpectrum(IMassSpectra massSpectra, FileWriter fileWriter) throws IOException {

		if(massSpectra != null) {
			for(IScanMSD massSpectrum : massSpectra.getList()) {
				List<Ion> ionList = new ArrayList<Ion>();
				List<IIon> ions = massSpectrum.getIons();
				for(IIon ion : ions) {
					double mz = ion.getIon();
					double intensity = ion.getAbundance();
					ionList.add(new Ion(mz, intensity));
				}
				SpectrumImpl splashSpectrum = new SpectrumImpl(ionList, SpectraType.MS);
				splashSpectrum.toRelative(100); // from MassBank getSplash.R
				Splash splash = SplashFactory.create();
				fileWriter.write(splash.splashIt(splashSpectrum));
			}
		}
		fileWriter.flush();
		fileWriter.close();
	}
}
