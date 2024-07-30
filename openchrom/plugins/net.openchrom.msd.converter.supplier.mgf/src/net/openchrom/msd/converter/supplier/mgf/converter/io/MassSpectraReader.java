/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactored MS/MS support
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.RegularLibraryMassSpectrum;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectraReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final String BEGIN_IONS = "BEGIN IONS";
	private static final String END_IONS = "END IONS";
	private static final String TITLE = "TITLE=";
	private static final String RT_IN_SECONDS = "RTINSECONDS=";
	private static final String PEPMASS = "PEPMASS=";
	// TODO private static final String CHARGE = "CHARGE=";
	private static final Pattern ionPattern = Pattern.compile("(\\d+\\.\\d+) (\\d+\\.\\d+)(?: (\\d+))?");
	private static final Pattern peptidePattern = Pattern.compile("(\\d+\\.\\d+)(?: (\\d+\\\\.\\d+))?");

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		IMassSpectra massSpectra = new MassSpectra();
		try (FileReader fileReader = new FileReader(file)) {
			try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
				String line = null;
				IRegularLibraryMassSpectrum massSpectrum = null;
				while((line = bufferedReader.readLine()) != null) {
					if(monitor.isCanceled()) {
						return massSpectra;
					}
					if(line.equals(BEGIN_IONS)) {
						massSpectrum = new RegularLibraryMassSpectrum();
					}
					if(line.startsWith(TITLE)) {
						massSpectrum.setIdentifier(line.replace(TITLE, "").trim());
					}
					if(line.startsWith(RT_IN_SECONDS)) {
						String retentionTimeSeconds = line.replace(RT_IN_SECONDS, "").trim();
						int rt = Math.round(Float.parseFloat(retentionTimeSeconds) * (float)IChromatogramOverview.SECOND_CORRELATION_FACTOR);
						massSpectrum.setRetentionTime(rt);
					}
					if(line.startsWith(PEPMASS)) {
						String peptideMass = line.replace(PEPMASS, "").trim();
						boolean startsWithNumber = Pattern.matches("^\\d.*", peptideMass);
						if(startsWithNumber) {
							Matcher matcher = peptidePattern.matcher(peptideMass);
							if(matcher.find()) {
								double precursorIon = Double.parseDouble(matcher.group(1));
								massSpectrum.setPrecursorIon(precursorIon);
							}
						}
					}
					boolean startsWithNumber = Pattern.matches("^\\d.*", line);
					if(startsWithNumber) {
						Matcher matcher = ionPattern.matcher(line);
						if(matcher.find()) {
							String mass = matcher.group(1);
							String intensity = matcher.group(2);
							// TODO: String charge = matcher.group(3);
							double mz = Double.parseDouble(mass);
							float abundance = Float.parseFloat(intensity);
							IIon ion = new Ion(mz, abundance);
							massSpectrum.addIon(ion);
						}
					}
					if(line.equals(END_IONS)) {
						massSpectra.addMassSpectrum(massSpectrum);
					}
				}
			}
		}
		return massSpectra;
	}
}