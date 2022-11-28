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
package net.openchrom.msd.converter.supplier.mzdb.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzdb.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.mzdb.model.IVendorScan;
import net.openchrom.msd.converter.supplier.mzdb.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.mzdb.model.VendorScan;

public class ChromatogramReader extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	public ChromatogramReader() {

	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return null;
	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			logger.error(e);
			return chromatogram;
		}
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.toString())) {
			try (Statement statement = connection.createStatement()) {
				//
				ResultSet mzdbResultSet = statement.executeQuery("SELECT * FROM mzdb;");
				long epoch = mzdbResultSet.getLong("creation_timestamp") * 1000; // to milliseconds
				chromatogram = new VendorChromatogram();
				chromatogram.setFile(file);
				chromatogram.setDate(new Date(epoch));
				float version = mzdbResultSet.getFloat("version");
				if(version > 0.7) {
					logger.warn("The .mzDB file may be incompatible.");
				}
				mzdbResultSet.close();
				//
				ResultSet nameResultSet = statement.executeQuery("SELECT name FROM sample;");
				String sampleName = nameResultSet.getString(1);
				chromatogram.setDataName(sampleName);
				nameResultSet.close();
				//
				ResultSet timeUnitResultSet = statement.executeQuery("SELECT unit_accession FROM cv_term WHERE accession='MS:1000016';");
				int timeMultiplicator = getTimeMultiplicator(timeUnitResultSet.getString(1));
				timeUnitResultSet.close();
				//
				ResultSet spectrumResultSet = statement.executeQuery("SELECT * FROM spectrum;");
				while(spectrumResultSet.next()) {
					IVendorScan scan = new VendorScan();
					int cycle = spectrumResultSet.getInt("cycle");
					scan.setScanNumber(cycle);
					String title = spectrumResultSet.getString("title");
					scan.setIdentifier(title);
					int retentionTime = Math.round(spectrumResultSet.getFloat("time") * timeMultiplicator);
					scan.setRetentionTime(retentionTime);
					short msLevel = spectrumResultSet.getShort("ms_level");
					scan.setMassSpectrometer(msLevel);
					float abundance = spectrumResultSet.getFloat("tic");
					IIon ion = new Ion(IIon.TIC_ION, abundance);
					scan.addIon(ion);
					chromatogram.addScan(scan);
				}
				spectrumResultSet.close();
			}
		} catch(SQLException e) {
			logger.warn(e);
		} catch(AbundanceLimitExceededException e) {
			logger.warn(e);
		} catch(IonLimitExceededException e) {
			logger.warn(e);
		}
		monitor.done();
		return chromatogram;
	}

	public static int getTimeMultiplicator(String unit) {

		int multiplicator = 1;
		if(unit.equals("millisecond")) {
			multiplicator = 1;
		}
		if(unit.equals("second")) {
			multiplicator = 1000;
		}
		if(unit.equals("minute")) {
			multiplicator = 60 * 1000;
		}
		return multiplicator;
	}
}
