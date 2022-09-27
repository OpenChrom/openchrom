/*******************************************************************************
 * Copyright (c) 2020, 2022 Matthias Mailänder.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pkf.converter.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.PeakLibraryInformation;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.pkf.converter.model.PkfMassSpectrum;

import us.hebi.matlab.mat.format.Mat5;
import us.hebi.matlab.mat.types.Char;
import us.hebi.matlab.mat.types.MatFile;
import us.hebi.matlab.mat.types.Matrix;
import us.hebi.matlab.mat.types.Struct;

public class PkfReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(PkfReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.setName(StringUtils.substringBefore(file.getName(), ".pkf"));
		MatFile matFile = Mat5.readFromFile(file);
		Struct c = matFile.getStruct("C");
		for(int col = 0; col < c.getNumCols(); col++) {
			PkfMassSpectrum pkf = new PkfMassSpectrum();
			ILibraryInformation libraryInformation = new PeakLibraryInformation();
			Char spectraId = c.get("nam", 0, col);
			Char growthTime = c.get("gti", 0, col);
			Char cultivationTemperature = c.get("tem", 0, col);
			Char cultivationAthmosphere = c.get("air", 0, col);
			Char cultivationMedium = c.get("med", 0, col);
			Char sampleConcentration = c.get("con", 0, col);
			Char sampleTreatment = c.get("con", 0, col);
			Char extraInformation = c.get("ext", 0, col);
			libraryInformation.setComments(spectraId.getString() + " " + growthTime.getString() + " " + cultivationTemperature.getString() + " " + cultivationAthmosphere.getString() + " " + cultivationMedium.getString() + " " + sampleConcentration.getString() + " " + sampleTreatment.getString() + " " + extraInformation.getString());
			Char genus = c.get("gen", 0, col);
			Char species = c.get("spe", 0, col);
			Char strain = c.get("str", 0, col);
			libraryInformation.setName(genus.getString() + " " + species.getString() + " " + strain.getString());
			Char customer = c.get("cus", 0, col);
			libraryInformation.setContributor(customer.getString());
			Char ncbiTaxId = c.get("uid", 0, col);
			libraryInformation.setReferenceIdentifier(ncbiTaxId.getString());
			pkf.setLibraryInformation(libraryInformation);
			Matrix pik = c.get("pik", 0, col);
			for(int n = 0; n < pik.getNumCols(); n++) {
				double mz = pik.getDouble(0, n);
				float intensity = pik.getFloat(1, n);
				try {
					pkf.addIon(new Ion(mz, intensity));
				} catch(AbundanceLimitExceededException
						| IonLimitExceededException e) {
					logger.warn(e);
				}
			}
			massSpectra.addMassSpectrum(pkf);
		}
		matFile.close();
		return massSpectra;
	}
}
