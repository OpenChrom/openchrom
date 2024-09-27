/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactor m/z and abundance limit
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzdb.io;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.support.history.EditInformation;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzdb.internal.DataEncoding;
import net.openchrom.msd.converter.supplier.mzdb.internal.DataMode;
import net.openchrom.msd.converter.supplier.mzdb.internal.Precision;
import net.openchrom.msd.converter.supplier.mzdb.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.mzdb.model.IVendorScan;
import net.openchrom.msd.converter.supplier.mzdb.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.mzdb.model.VendorIon;
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
				chromatogram = new VendorChromatogram();
				chromatogram.setFile(file);
				readMzDB(statement, file, chromatogram);
				readName(statement, chromatogram);
				readEditHistory(statement, chromatogram);
				readSpectrum(statement, chromatogram);
				readScans(statement, chromatogram);
			}
		} catch(SQLException e) {
			logger.warn(e);
		}
		monitor.done();
		return chromatogram;
	}

	private void readMzDB(Statement statement, File file, IVendorChromatogram chromatogram) throws SQLException {

		try (ResultSet mzdbResultSet = statement.executeQuery("SELECT * FROM mzdb;")) {
			long epoch = mzdbResultSet.getLong("creation_timestamp") * 1000; // to milliseconds
			chromatogram.setDate(new Date(epoch));
			float version = mzdbResultSet.getFloat("version");
			if(version > 0.7) {
				logger.warn("The .mzDB file may be incompatible.");
			}
		}
	}

	private void readName(Statement statement, IVendorChromatogram chromatogram) throws SQLException {

		try (ResultSet nameResultSet = statement.executeQuery("SELECT name FROM sample;")) {
			String sampleName = nameResultSet.getString(1);
			chromatogram.setSampleName(sampleName);
		}
	}

	private List<String> readDataProcessing(Statement statement) throws SQLException {

		List<String> dataProcessing = new ArrayList<>();
		try (ResultSet dataProcessingSet = statement.executeQuery("SELECT name FROM data_processing ORDER BY id;")) {
			while(dataProcessingSet.next()) {
				dataProcessing.add(dataProcessingSet.getString("name"));
			}
		}
		return dataProcessing;
	}

	private List<String> readSoftware(Statement statement) throws SQLException {

		List<String> software = new ArrayList<>();
		try (ResultSet softwareResultSet = statement.executeQuery("SELECT name FROM software ORDER BY id;")) {
			while(softwareResultSet.next()) {
				software.add(softwareResultSet.getString("name"));
			}
		}
		return software;
	}

	private void readEditHistory(Statement statement, IVendorChromatogram chromatogram) throws SQLException {

		List<String> dataProcessing = readDataProcessing(statement);
		List<String> software = readSoftware(statement);
		//
		int size = Math.min(dataProcessing.size(), software.size());
		for(int i = 0; i < size; i++) {
			chromatogram.getEditHistory().add(new EditInformation(dataProcessing.get(i), software.get(i)));
		}
	}

	private void readSpectrum(Statement statement, IVendorChromatogram chromatogram) throws SQLException {

		try (ResultSet spectrumResultSet = statement.executeQuery("SELECT * FROM spectrum;")) {
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
		}
	}

	private DataEncoding readDataEncoding(Statement statement) throws SQLException {

		DataEncoding dataEncoding = new DataEncoding();
		try (ResultSet dataEncodingResultSet = statement.executeQuery("SELECT * FROM data_encoding;")) {
			while(dataEncodingResultSet.next()) {
				dataEncoding.setDataMode(DataMode.valueOf(dataEncodingResultSet.getString("mode").toUpperCase()));
				dataEncoding.setCompression(dataEncodingResultSet.getString("compression"));
				String byteOrder = dataEncodingResultSet.getString("byte_order");
				if(byteOrder.equals("little_endian")) {
					dataEncoding.setByteOrder(ByteOrder.LITTLE_ENDIAN);
				} else if(byteOrder.equals("big_endian")) {
					dataEncoding.setByteOrder(ByteOrder.BIG_ENDIAN);
				}
				dataEncoding.setMzPrecision(Precision.fromBits(dataEncodingResultSet.getInt("mz_precision")));
				dataEncoding.setIntensityPrecision(Precision.fromBits(dataEncodingResultSet.getInt("intensity_precision")));
			}
		}
		return dataEncoding;
	}

	private void readScans(Statement statement, IVendorChromatogram chromatogram) throws SQLException {

		DataEncoding dataEncoding = readDataEncoding(statement);
		String compression = dataEncoding.getCompression();
		if(compression != null && !compression.isEmpty() && !compression.equals("none")) {
			throw new UnsupportedOperationException("Compression is not yet supported.");
		}
		if(dataEncoding.getDataMode() != DataMode.CENTROID) {
			throw new UnsupportedOperationException("Only centroided data is supported.");
		}
		try (ResultSet boundingBoxResultSet = statement.executeQuery("SELECT * FROM bounding_box;")) {
			while(boundingBoxResultSet.next()) {
				byte[] blobData = boundingBoxResultSet.getBytes("data");
				ByteBuffer buffer = ByteBuffer.wrap(blobData).order(dataEncoding.getByteOrder());
				while(buffer.hasRemaining()) {
					int spectrumId = buffer.getInt();
					IScan scan = chromatogram.getScan(spectrumId);
					if(scan instanceof IVendorScan vendorScan) {
						vendorScan.removeIon(0); // calculate TIC instead
						int numberOfIons = buffer.getInt();
						for(int i = 0; i < numberOfIons; i++) {
							double mz = 0;
							if(dataEncoding.getMzPrecision() == Precision.DOUBLE) {
								mz = buffer.getDouble();
							} else if(dataEncoding.getMzPrecision() == Precision.FLOAT) {
								mz = buffer.getFloat();
							}
							float intensity = 0;
							if(dataEncoding.getIntensityPrecision() == Precision.DOUBLE) {
								intensity = (float)buffer.getDouble();
							} else if(dataEncoding.getIntensityPrecision() == Precision.FLOAT) {
								intensity = buffer.getFloat();
							}
							vendorScan.addIon(new VendorIon(mz, intensity));
						}
					}
				}
			}
		}
	}
}