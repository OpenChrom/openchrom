/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.support.history.EditInformation;
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
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

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
				ResultSet dataProcessingSet = statement.executeQuery("SELECT name FROM data_processing ORDER BY id;");
				List<String> dataProcessing = new ArrayList<>();
				while(dataProcessingSet.next()) {
					dataProcessing.add(dataProcessingSet.getString("name"));
				}
				dataProcessingSet.close();
				//
				ResultSet softwareResultSet = statement.executeQuery("SELECT name FROM software ORDER BY id;");
				List<String> software = new ArrayList<>();
				while(softwareResultSet.next()) {
					software.add(softwareResultSet.getString("name"));
				}
				softwareResultSet.close();
				//
				int size = Math.min(dataProcessing.size(), software.size());
				for(int i = 0; i < size; i++) {
					chromatogram.getEditHistory().add(new EditInformation(dataProcessing.get(i), software.get(i)));
				}
				//
				ResultSet spectrumResultSet = statement.executeQuery("SELECT * FROM spectrum;");
				while(spectrumResultSet.next()) {
					IVendorScan scan = new VendorScan();
					scan.setScanNumber(spectrumResultSet.getInt("cycle"));
					scan.setIdentifier(spectrumResultSet.getString("title"));
					scan.setRetentionTime(Math.round(spectrumResultSet.getFloat("time") * 1000)); // s to ms
					scan.setMassSpectrometer(spectrumResultSet.getShort("ms_level"));
					scan.addIon(new Ion(IIon.TIC_ION, spectrumResultSet.getFloat("tic")));
					double mainPrecursorMZ = spectrumResultSet.getDouble("main_precursor_mz");
					scan.setPrecursorIon(mainPrecursorMZ);
					chromatogram.addScan(scan);
				}
				spectrumResultSet.close();
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			}
		} catch(SQLException e) {
			logger.warn(e);
		}
		monitor.done();
		return chromatogram;
	}
}
